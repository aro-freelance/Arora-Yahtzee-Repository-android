package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreScreenActivity extends AppCompatActivity {


    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
    SharedPreferences.Editor editor = pref.edit();

    TextView scoreText;
    Button cancelButton;
    Button recordButton;
    RecyclerView recyclerView;
    AdapterScoreCard adapter;
    RecyclerView.LayoutManager mLayoutManager;

    RollScreenActivity rollScreenActivity = new RollScreenActivity();

    //dice values
    Integer diceValue1 = 0;
    Integer diceValue2 = 0;
    Integer diceValue3 = 0;
    Integer diceValue4 = 0;
    Integer diceValue5 = 0;

    Boolean isSelectionMade = false;
    Integer selectedIndex = -1;

    Integer rollNumber = 0;

    Boolean isMultiYahtzee = false;
    Integer upperTotal = 0;
    Integer bonus = 0;
    Integer lowerTotal = 0;
    Integer grandTotal = 0;

    ArrayList<ScoreLineModel> scoreCardArray = new ArrayList<ScoreLineModel>();

    ArrayList<Integer> valuesArray = new ArrayList<>();

    //todo defaults (persistance)

    //stored score values
    Integer sOnes = 0;
    Integer sTwos = 0;
    Integer sThrees = 0;
    Integer sFours = 0;
    Integer sFives = 0;
    Integer sSixes = 0;
    Integer sThreeOfAKind = 0;
    Integer sFourOfAKind = 0;
    Integer sFullHouse = 0;
    Integer sSmallStraight = 0;
    Integer sLargeStraight = 0;
    Integer sYahtzee = 0;
    Integer sChance = 0;
    Integer sNumberOfYahtzees = 0;

    //stored isCompleted bool values

    Boolean sIsOnesCompleted = false;
    Boolean sIsTwosCompleted = false;
    Boolean sIsThreesCompleted = false;
    Boolean sIsFoursCompleted = false;
    Boolean sIsFivesCompleted = false;
    Boolean sIsSixesCompleted = false;
    Boolean sIsThreeOfAKindCompleted = false;
    Boolean sIsFourOfAKindCompleted = false;
    Boolean sIsFullHouseCompleted = false;
    Boolean sIsSmallStraightCompleted = false;
    Boolean sIsLargeStraightCompleted = false;
    Boolean sIsYahtzeeCompleted = false;
    Boolean sIsChanceCompleted = false;

    //current values

    Integer cOnes = 0;
    Integer cTwos = 0;
    Integer cThrees = 0;
    Integer cFours = 0;
    Integer cFives = 0;
    Integer cSixes = 0;
    Integer cThreeOfAKind = 0;
    Integer cFourOfAKind = 0;
    Integer cFullHouse = 0;
    Integer cSmallStraight = 0;
    Integer cLargeStraight = 0;
    Integer cYahtzee = 0;
    Integer cChance = 0;
    Integer cNumberOfYahtzees = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        scoreText = findViewById(R.id.score_textView_scorescreen);
        cancelButton = findViewById(R.id.cancel_button_scorescreen);
        recordButton = findViewById(R.id.record_button_scorescreen);

        //setup recyclerview
        recyclerView = findViewById(R.id.recyclerView_scorescreen);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new AdapterScoreCard(this, scoreCardArray);
        recyclerView.setAdapter(adapter);



        recordButton.setOnClickListener((View v) -> {
            record();
        });

        cancelButton.setOnClickListener((View v) -> {
            cancelPressed();
        });


        //load previous recorded score data
        loadStoredScores();

        //check possible values of current roll
        checkPossibleScores();

    }

    private void loadStoredScores(){

        //check if ones complete
        if(pref.getBoolean("isOnesComplete", false)){

            Log.d("testing", "Load: ones complete");
            sIsOnesCompleted = true;

            //load value to temp
            int tOnes = pref.getInt("onesValue", 0);
            if(tOnes != 0){
                //if value is loaded set it to stored value
                sOnes = tOnes;
            }
        }

        //check if twos complete
        if(pref.getBoolean("isTwosComplete", false)){

            Log.d("testing", "Load: twos complete");
            sIsTwosCompleted = true;

            //load value to temp
            int tTwos = pref.getInt("twosValue", 0);
            if(tTwos != 0){
                //if value is loaded set it to stored value
                sTwos = tTwos;
            }
        }

        //check if threes complete
        if(pref.getBoolean("isThreesComplete", false)){

            Log.d("testing", "Load: threes complete");
            sIsThreesCompleted = true;

            //load value to temp
            int tThrees = pref.getInt("threesValue", 0);
            if(tThrees != 0){
                //if value is loaded set it to stored value
                sThrees = tThrees;
            }
        }

        //check if fours complete
        if(pref.getBoolean("isFoursComplete", false)){

            Log.d("testing", "Load: fours complete");
            sIsFoursCompleted = true;

            //load value to temp
            int tFours = pref.getInt("foursValue", 0);
            if(tFours != 0){
                //if value is loaded set it to stored value
                sFours = tFours;
            }
        }

        //check if fives complete
        if(pref.getBoolean("isFivesComplete", false)){

            Log.d("testing", "Load: fives complete");
            sIsFivesCompleted = true;

            //load value to temp
            int tFives = pref.getInt("fivesValue", 0);
            if(tFives != 0){
                //if value is loaded set it to stored value
                sFives = tFives;
            }
        }

        //check if sixes complete
        if(pref.getBoolean("isSixesComplete", false)){

            Log.d("testing", "Load: sixes complete");
            sIsSixesCompleted = true;

            //load value to temp
            int tSixes = pref.getInt("sixesValue", 0);
            if(tSixes != 0){
                //if value is loaded set it to stored value
                sSixes = tSixes;
            }
        }

        //check if 3 of kind complete
        if(pref.getBoolean("isThreeOfAKindComplete", false)){

            Log.d("testing", "Load: 3 of kind complete");
            sIsThreeOfAKindCompleted = true;

            //load value to temp
            int tThreeOfAKind = pref.getInt("threeOfAKindValue", 0);
            if(tThreeOfAKind != 0){
                //if value is loaded set it to stored value
                sThreeOfAKind = tThreeOfAKind;
            }
        }

        //check if 4 of kind complete
        if(pref.getBoolean("isFourOfAKindComplete", false)){

            Log.d("testing", "Load: 4 of kind complete");
            sIsFourOfAKindCompleted = true;

            //load value to temp
            int tFourOfAKind = pref.getInt("fourOfAKindValue", 0);
            if(tFourOfAKind != 0){
                //if value is loaded set it to stored value
                sFourOfAKind = tFourOfAKind;
            }
        }


        //check if full house complete
        if(pref.getBoolean("isFullHouseComplete", false)){

            Log.d("testing", "Load: full house complete");
            sIsFullHouseCompleted = true;

            //load value to temp
            int tFullHouse = pref.getInt("fullHouseValue", 0);
            if(tFullHouse != 0){
                //if value is loaded set it to stored value
                sFullHouse = tFullHouse;
            }
        }

        //check if small straight complete
        if(pref.getBoolean("isSmallStraightComplete", false)){

            Log.d("testing", "Load: small straight complete");
            sIsSmallStraightCompleted = true;

            //load value to temp
            int tSmallStraight = pref.getInt("smallStraightValue", 0);
            if(tSmallStraight != 0){
                //if value is loaded set it to stored value
                sSmallStraight = tSmallStraight;
            }
        }

        //check if large straight complete
        if(pref.getBoolean("isLargeStraightComplete", false)){

            Log.d("testing", "Load: large straight complete");
            sIsLargeStraightCompleted = true;

            //load value to temp
            int tLargeStraight = pref.getInt("largeStraightValue", 0);
            if(tLargeStraight != 0){
                //if value is loaded set it to stored value
                sLargeStraight = tLargeStraight;
            }
        }

        //check if large straight complete
        if(pref.getBoolean("isLargeStraightComplete", false)){

            Log.d("testing", "Load: large straight complete");
            sIsLargeStraightCompleted = true;

            //load value to temp
            int tLargeStraight = pref.getInt("largeStraightValue", 0);
            if(tLargeStraight != 0){
                //if value is loaded set it to stored value
                sLargeStraight = tLargeStraight;
            }
        }

        //check if yahtzee complete
        if(pref.getBoolean("isYahtzeeComplete", false)){

            Log.d("testing", "Load: yahtzee complete");
            sIsYahtzeeCompleted = true;

            //load value to temp
            int tYahtzee = pref.getInt("yahtzeeValue", 0);
            if(tYahtzee != 0){
                //if value is loaded set it to stored value
                sYahtzee = tYahtzee;
            }

            //number of additional yahtzees
            int tNumberOfYahtzees = pref.getInt("numberOfYahtzeesValue", 0);
            if(tNumberOfYahtzees != 0){
                //if value is loaded set it to stored value
                sNumberOfYahtzees = tNumberOfYahtzees;
            }
        }

        //check if chance complete
        if(pref.getBoolean("isChanceComplete", false)){

            Log.d("testing", "Load: chance complete");
            sIsChanceCompleted = true;

            //load value to temp
            int tChance = pref.getInt("chanceValue", 0);
            if(tChance != 0){
                //if value is loaded set it to stored value
                sChance = tChance;
            }
        }

        upperTotal = sOnes + sTwos + sThrees + sFours + sFives + sSixes;

        lowerTotal = sThreeOfAKind + sFourOfAKind + sFullHouse + sSmallStraight + sLargeStraight
                + sYahtzee + sChance + (sNumberOfYahtzees * 100);

        if(upperTotal >= 63){
            bonus = 35;
        }

        grandTotal = upperTotal + lowerTotal;

        scoreText.setText("Score : " + grandTotal);

        scoreCardArray.clear();
        scoreCardArray.add(0, new ScoreLineModel("Ones", sOnes, false,
                sIsOnesCompleted, "description", false, false));
        scoreCardArray.add(1, new ScoreLineModel("Twos", sTwos, false,
                sIsTwosCompleted, "description", false, false));
        scoreCardArray.add(2, new ScoreLineModel("Threes", sThrees, false,
                sIsThreesCompleted, "description", false, false));
        scoreCardArray.add(3, new ScoreLineModel("Fours", sFours, false,
                sIsFoursCompleted, "description", false, false));
        scoreCardArray.add(4, new ScoreLineModel("Fives", sFives, false,
                sIsFivesCompleted, "description", false, false));
        scoreCardArray.add(5, new ScoreLineModel("Sixes", sSixes, false,
                sIsSixesCompleted, "description", false, false));
        scoreCardArray.add(6, new ScoreLineModel("Upper Total", 0, false,
                false, "description", true, false));
        scoreCardArray.add(7, new ScoreLineModel("Bonus", 0, false,
                false, "description", true, false));
        scoreCardArray.add(8, new ScoreLineModel("3 of a Kind", sThreeOfAKind, false,
                sIsThreeOfAKindCompleted, "description", false, false));
        scoreCardArray.add(9, new ScoreLineModel("4 of a Kind", sFourOfAKind, false,
                sIsFourOfAKindCompleted, "description", false, false));
        scoreCardArray.add(10, new ScoreLineModel("Full House", sFullHouse, false,
                sIsFullHouseCompleted, "description", false, false));
        scoreCardArray.add(11, new ScoreLineModel("Small Straight", sSmallStraight, false,
                sIsSmallStraightCompleted, "description", false, false));
        scoreCardArray.add(12, new ScoreLineModel("Large Straight", sLargeStraight, false,
                sIsLargeStraightCompleted, "description", false, false));
        scoreCardArray.add(13, new ScoreLineModel("Yahtzee", sYahtzee, false,
                sIsYahtzeeCompleted, "description", false, false));
        scoreCardArray.add(14, new ScoreLineModel("Chance", sChance, false,
                sIsChanceCompleted, "description", false, false));
        scoreCardArray.add(15, new ScoreLineModel("Yahtzee Bonus", (sNumberOfYahtzees * 100), false,
                false, "description", true, false));
        scoreCardArray.add(16, new ScoreLineModel("Lower Total", 0, false,
                false, "description", true, false));
        scoreCardArray.add(17, new ScoreLineModel("Grand Total", 0, false,
                false, "description", true, false));


    }


    private void checkPossibleScores(){
        //todo
    }

    private void record(){
        //todo
    }

    private void cancelPressed(){

    }

    //todo translate prepare... record and reset roll number to 0 before leaving this screen
//    private void prepare(){
//
//    }

    private void checkEndGame(){
        //todo
    }

}