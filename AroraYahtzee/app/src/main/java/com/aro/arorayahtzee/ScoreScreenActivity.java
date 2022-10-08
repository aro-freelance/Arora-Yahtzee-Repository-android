package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ScoreScreenActivity extends AppCompatActivity {


    public static final String MyPREFERENCES = "myprefs";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

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


    //current score values
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


        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            diceValue1 = bundle.getInt("diceValue1");
            diceValue2 = bundle.getInt("diceValue2");
            diceValue3 = bundle.getInt("diceValue3");
            diceValue4 = bundle.getInt("diceValue4");
            diceValue5 = bundle.getInt("diceValue5");
            rollNumber = bundle.getInt("rollNumber");
        }

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


    //load saved score card values
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

        //todo upper and lower total is updating incorrectly on scorecard screen.
        // 1. endlessly adds when selecting and  reselecting
        // 2. does not keep previous score and add when displaying

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


    //use the dice values to calculate possible values for each score card category
    private void checkPossibleScores(){

        Log.d("test", "One: " + diceValue1 + " Two: " + diceValue2 + " Three: " + diceValue3
                + " Four: " + diceValue4 + " Five: " + diceValue5);

        //put the roll values in array and sort to ascending numerical order
        valuesArray.clear();
        valuesArray.add(diceValue1);
        valuesArray.add(diceValue2);
        valuesArray.add(diceValue3);
        valuesArray.add(diceValue4);
        valuesArray.add(diceValue5);
        Collections.sort(valuesArray);

        //Count each number
        int oneCounter = 0;
        int twoCounter = 0;
        int threeCounter = 0;
        int fourCounter = 0;
        int fiveCounter = 0;
        int sixCounter = 0;

        for (int i = 0; i < valuesArray.size(); i++) {

            int value = valuesArray.get(i);
            if(value == 1){
                oneCounter = oneCounter + 1;
            }
            if(value == 2){
                twoCounter = twoCounter + 1;
            }
            if(value == 3){
                threeCounter = threeCounter + 1;
            }
            if(value == 4){
                fourCounter = fourCounter + 1;
            }
            if(value == 5){
                fiveCounter = fiveCounter + 1;
            }
            if(value == 6){
                sixCounter = sixCounter + 1;
            }
        }

        Log.d("test", "One Counter: " + oneCounter);
        Log.d("test", "Two Counter: " + twoCounter);
        Log.d("test", "Three Counter: " + threeCounter);
        Log.d("test", "Four Counter: " + fourCounter);
        Log.d("test", "Five Counter: " + fiveCounter);

        //go through the dice values and determine possible scores for each score card slot

        //ONES
        int onesScore = 0;
        if(!scoreCardArray.get(0).isCompleted){
            for (int i = 0; i < valuesArray.size(); i++) {
                int value = valuesArray.get(i);

                if(value == 1){
                    onesScore = onesScore + value;
                }

            }
            Log.d("test", "Ones: " + onesScore);
        }
        else{
            onesScore = sOnes;
            Log.d("test", "sOnes: " + sOnes);
        }

        //TWOS
        int twosScore = 0;
        if(!scoreCardArray.get(1).isCompleted){
            for (int i = 0; i < valuesArray.size(); i++) {
                int value = valuesArray.get(i);

                if(value == 2){
                    twosScore = twosScore + value;
                }

            }
            Log.d("test", "Twos: " + twosScore);
        }
        else{
            twosScore = sTwos;
            Log.d("test", "sTwos: " + sTwos);
        }

        //THREES
        int threesScore = 0;
        if(!scoreCardArray.get(2).isCompleted){
            for (int i = 0; i < valuesArray.size(); i++) {
                int value = valuesArray.get(i);

                if(value == 3){
                    threesScore = threesScore + value;
                }

            }
            Log.d("test", "Threes: " + threesScore);
        }
        else{
            threesScore = sThrees;
            Log.d("test", "sThrees: " + sThrees);
        }

        //FOURS
        int foursScore = 0;
        if(!scoreCardArray.get(3).isCompleted){
            for (int i = 0; i < valuesArray.size(); i++) {
                int value = valuesArray.get(i);

                if(value == 4){
                    foursScore = foursScore + value;
                }

            }
            Log.d("test", "Fours: " + foursScore);
        }
        else{
            foursScore = sFours;
            Log.d("test", "sFours: " + sFours);
        }

        //FIVES
        int fivesScore = 0;
        if(!scoreCardArray.get(4).isCompleted){
            for (int i = 0; i < valuesArray.size(); i++) {
                int value = valuesArray.get(i);

                if(value == 5){
                    fivesScore = fivesScore + value;
                }

            }
            Log.d("test", "Fives: " + fivesScore);
        }
        else{
            fivesScore = sFives;
            Log.d("test", "sFives: " + sFives);
        }

        //SIXES
        int sixesScore = 0;
        if(!scoreCardArray.get(5).isCompleted){
            for (int i = 0; i < valuesArray.size(); i++) {
                int value = valuesArray.get(i);

                if(value == 6){
                    sixesScore = sixesScore + value;
                }

            }
            Log.d("test", "Sixes: " + sixesScore);
        }
        else{
            sixesScore = sSixes;
            Log.d("test", "sSixes: " + sSixes);
        }

        //UPPER TOTAL
        upperTotal = sOnes + sTwos + sThrees + sFours + sFives + sSixes;
        Log.d("test", "UpperTotal: " + upperTotal);

        //BONUS
        bonus = 0;
        if(upperTotal >= 63){
            bonus = 35;
        }
        Log.d("test", "bonus: " + bonus);

        //3 OF A KIND
        int threeOfKindScore = 0;
        if(!scoreCardArray.get(8).isCompleted){

            boolean isThreeOfKind = false;
            if(oneCounter >= 3){
                isThreeOfKind = true;
            }
            if(twoCounter >= 3){
                isThreeOfKind = true;
            }
            if(threeCounter >= 3){
                isThreeOfKind = true;
            }
            if(fourCounter >= 3){
                isThreeOfKind = true;
            }
            if(fiveCounter >= 3){
                isThreeOfKind = true;
            }
            if(sixCounter >= 3){
                isThreeOfKind = true;
            }

            if(isThreeOfKind){
                threeOfKindScore = diceValue1 + diceValue2 + diceValue3 + diceValue4 + diceValue5;
            } else {
                threeOfKindScore = 0;
            }

            Log.d("test", "Three of a Kind: " + threeOfKindScore);

        }
        else{

            threeOfKindScore = sThreeOfAKind;
            Log.d("test", "sThree of a Kind: " + sThreeOfAKind);
        }

        //4 OF A KIND
        int fourOfKindScore = 0;
        if(!scoreCardArray.get(9).isCompleted){

            boolean isFourOfKind = false;
            if(oneCounter >= 4){
                isFourOfKind = true;
            }
            if(twoCounter >= 4){
                isFourOfKind = true;
            }
            if(threeCounter >= 4){
                isFourOfKind = true;
            }
            if(fourCounter >= 4){
                isFourOfKind = true;
            }
            if(fiveCounter >= 4){
                isFourOfKind = true;
            }
            if(sixCounter >= 4){
                isFourOfKind = true;
            }

            if(isFourOfKind){
                fourOfKindScore = diceValue1 + diceValue2 + diceValue3 + diceValue4 + diceValue5;
            } else {
                fourOfKindScore = 0;
            }

            Log.d("test", "Four of a Kind: " + fourOfKindScore);

        }
        else{

            fourOfKindScore = sFourOfAKind;
            Log.d("test", "sFour of a Kind: " + sFourOfAKind);
        }

        //FULL HOUSE
        int fullHouseScore = 0;
        if(!scoreCardArray.get(10).isCompleted){

            boolean isFullHouse = false;
            boolean isPair = false;
            boolean isTrio = false;
            int theNumberThatHasAPair = 0;
            int theNumberThatHasATrio = 0;

            if(oneCounter == 2){
                isPair = true;
                theNumberThatHasAPair = 1;
            }
            if(twoCounter == 2){
                isPair = true;
                theNumberThatHasAPair = 2;
            }
            if(threeCounter == 2){
                isPair = true;
                theNumberThatHasAPair = 3;
            }
            if(fourCounter == 2){
                isPair = true;
                theNumberThatHasAPair = 4;
            }
            if(fiveCounter == 2){
                isPair = true;
                theNumberThatHasAPair = 5;
            }
            if(sixCounter == 2){
                isPair = true;
                theNumberThatHasAPair = 6;
            }

            if(oneCounter == 3){
                isTrio = true;
                theNumberThatHasATrio = 1;
            }
            if(twoCounter == 3){
                isTrio = true;
                theNumberThatHasATrio = 2;
            }
            if(threeCounter == 3){
                isTrio = true;
                theNumberThatHasATrio = 3;
            }
            if(fourCounter == 3){
                isTrio = true;
                theNumberThatHasATrio = 4;
            }
            if(fiveCounter == 3){
                isTrio = true;
                theNumberThatHasATrio = 5;
            }
            if(sixCounter == 3){
                isTrio = true;
                theNumberThatHasATrio = 6;
            }

            if(isPair && isTrio){
                if(theNumberThatHasAPair != theNumberThatHasATrio){
                    isFullHouse = true;
                }
            }

            if(isFullHouse){
                fullHouseScore = 25;
            }

            Log.d("test", "Full House: " + fullHouseScore);

        }
        else{
            fullHouseScore = sFullHouse;
            Log.d("test", "Full House: " + sFullHouse);
        }

        //SM STRAIGHT
        int smallStraightScore = 0;
        if(!scoreCardArray.get(11).isCompleted){

            boolean isSmallStraight = false;

            if(valuesArray.contains(1) && valuesArray.contains(2) && valuesArray.contains(3)
                    && valuesArray.contains(4)){
                isSmallStraight = true;
            } else if(valuesArray.contains(2) && valuesArray.contains(3) && valuesArray.contains(4)
                    && valuesArray.contains(5)){
                isSmallStraight = true;
            } else if(valuesArray.contains(3) && valuesArray.contains(4) && valuesArray.contains(5)
                    && valuesArray.contains(6)){
                isSmallStraight = true;
            } else {
                isSmallStraight = false;
            }

            if(isSmallStraight){
                smallStraightScore = 30;
            }

            Log.d("test", "Small Straight: " + smallStraightScore);

        } else{
            smallStraightScore = sSmallStraight;
            Log.d("test", "sSmall Straight: " + sSmallStraight);
        }

        //LG STRAIGHT
        int largeStraightScore = 0;
        if(!scoreCardArray.get(12).isCompleted){

            boolean isLargeStraight = false;

            if(valuesArray.contains(1) && valuesArray.contains(2) && valuesArray.contains(3)
                    && valuesArray.contains(4) && valuesArray.contains(5)){
                isLargeStraight = true;
            } else if(valuesArray.contains(2) && valuesArray.contains(3) && valuesArray.contains(4)
                    && valuesArray.contains(5) && valuesArray.contains(6)){
                isLargeStraight = true;
            } else {
                isLargeStraight = false;
            }

            if(isLargeStraight){
                largeStraightScore = 40;
            }

            Log.d("test", "Large Straight: " + largeStraightScore);

        } else{
            largeStraightScore = sLargeStraight;
            Log.d("test", "sLarge Straight: " + sLargeStraight);
        }

        //YAHTZEE
        int yahtzeeScore = 0;
        boolean isYahtzee = false;
        if(oneCounter >= 5){
            isYahtzee = true;
        }
        if(twoCounter >= 5){
            isYahtzee = true;
        }
        if(threeCounter >= 5){
            isYahtzee = true;
        }
        if(fourCounter >= 5){
            isYahtzee = true;
        }
        if(fiveCounter >= 5){
            isYahtzee = true;
        }
        if(sixCounter >= 5){
            isYahtzee = true;
        }

        if(isYahtzee){
            yahtzeeScore = 50;
        }

        if(!scoreCardArray.get(13).isCompleted){
            Log.d("test", "Yahtzee: " + yahtzeeScore);
        }
        //yahtzee is complete
        else{
            //but player has another yahtzee
            if(sYahtzee > 0){
                if(isYahtzee){
                    isMultiYahtzee = true;
                    Log.d("test", "is multi yahtzee = " + isMultiYahtzee);
                    //todo show message to user and let them select where to put it (681 on xcode file)


                }
            }
            yahtzeeScore = sYahtzee;
            Log.d("test", "sYahtzee: " + sYahtzee);
        }

        //CHANCE
        int chanceScore = 0;
        if(!scoreCardArray.get(14).isCompleted){
            chanceScore = diceValue1 + diceValue2 + diceValue3 + diceValue4 + diceValue5;
            Log.d("test", "Chance: " + chanceScore);
        } else {
            chanceScore = sChance;
            Log.d("test", "sChance: " + sChance);
        }

        //LOWER TOTAL
        lowerTotal = sThreeOfAKind + sFourOfAKind + sFullHouse + sSmallStraight
                + sLargeStraight + sYahtzee + sChance + (sNumberOfYahtzees * 100);
        Log.d("test", "number of additional yahtzees = " + sNumberOfYahtzees);
        Log.d("test", "Lower Total: " + lowerTotal);

        //GRAND TOTAL
        grandTotal = upperTotal + bonus + lowerTotal;

        Log.d("test", "Grand Total: " + grandTotal);

        scoreCardArray.clear();
        scoreCardArray.add(0, new ScoreLineModel("Ones", onesScore, false,
                sIsOnesCompleted, "description", false, false));
        scoreCardArray.add(1, new ScoreLineModel("Twos", twosScore, false,
                sIsTwosCompleted, "description", false, false));
        scoreCardArray.add(2, new ScoreLineModel("Threes", threesScore, false,
                sIsThreesCompleted, "description", false, false));
        scoreCardArray.add(3, new ScoreLineModel("Fours", foursScore, false,
                sIsFoursCompleted, "description", false, false));
        scoreCardArray.add(4, new ScoreLineModel("Fives", fivesScore, false,
                sIsFivesCompleted, "description", false, false));
        scoreCardArray.add(5, new ScoreLineModel("Sixes", sixesScore, false,
                sIsSixesCompleted, "description", false, false));
        scoreCardArray.add(6, new ScoreLineModel("Upper Total", upperTotal, false,
                false, "description", true, false));
        scoreCardArray.add(7, new ScoreLineModel("Bonus", bonus, false,
                false, "description", true, false));
        scoreCardArray.add(8, new ScoreLineModel("3 of a Kind", threeOfKindScore, false,
                sIsThreeOfAKindCompleted, "description", false, false));
        scoreCardArray.add(9, new ScoreLineModel("4 of a Kind", fourOfKindScore, false,
                sIsFourOfAKindCompleted, "description", false, false));
        scoreCardArray.add(10, new ScoreLineModel("Full House", fullHouseScore, false,
                sIsFullHouseCompleted, "description", false, false));
        scoreCardArray.add(11, new ScoreLineModel("Small Straight", smallStraightScore, false,
                sIsSmallStraightCompleted, "description", false, false));
        scoreCardArray.add(12, new ScoreLineModel("Large Straight", largeStraightScore, false,
                sIsLargeStraightCompleted, "description", false, false));
        scoreCardArray.add(13, new ScoreLineModel("Yahtzee", yahtzeeScore, false,
                sIsYahtzeeCompleted, "description", false, false));
        scoreCardArray.add(14, new ScoreLineModel("Chance", chanceScore, false,
                sIsChanceCompleted, "description", false, false));
        scoreCardArray.add(15, new ScoreLineModel("Yahtzee Bonus", (sNumberOfYahtzees * 100), false,
                false, "description", true, false));
        scoreCardArray.add(16, new ScoreLineModel("Lower Total", lowerTotal, false,
                false, "description", true, false));
        scoreCardArray.add(17, new ScoreLineModel("Grand Total", grandTotal, false,
                false, "description", true, false));


    }

    //record the value for the selected score card category
    // and close the score card (return to roll screen or end game)
    private void record(){

        isSelectionMade = pref.getBoolean("isSelectionMade", false);
        selectedIndex = pref.getInt("selectedIndexScoreCard", -1);

        Log.d("test", "isSelectionMade = " +isSelectionMade
                + " selectedIndex = " + selectedIndex);

        //check what is selected and then save to persistent storage
        if(isSelectionMade){

            if(scoreCardArray.get(0).isSelected){
                //store value
                editor.putInt("onesValue", scoreCardArray.get(0).value);
                //store isComplete
                editor.putBoolean("isOnesComplete", true);
                editor.apply();
                cOnes = scoreCardArray.get(0).value;
            } else {
                cOnes = sOnes;
            }
            if(scoreCardArray.get(1).isSelected){
                //store value
                editor.putInt("twosValue", scoreCardArray.get(1).value);
                //store isComplete
                editor.putBoolean("isTwosComplete", true);
                editor.apply();
                cTwos = scoreCardArray.get(1).value;
            } else {
                cTwos = sOnes;
            }
            if(scoreCardArray.get(2).isSelected){
                //store value
                editor.putInt("threesValue", scoreCardArray.get(2).value);
                //store isComplete
                editor.putBoolean("isThreesComplete", true);
                editor.apply();
                cThrees = scoreCardArray.get(2).value;
            } else {
                cThrees = sThrees;
            }
            if(scoreCardArray.get(3).isSelected){
                //store value
                editor.putInt("foursValue", scoreCardArray.get(3).value);
                //store isComplete
                editor.putBoolean("isFoursComplete", true);
                editor.apply();
                cFours = scoreCardArray.get(3).value;
            } else {
                cFours = sFours;
            }
            if(scoreCardArray.get(4).isSelected){
                //store value
                editor.putInt("fivesValue", scoreCardArray.get(4).value);
                //store isComplete
                editor.putBoolean("isFivesComplete", true);
                editor.apply();
                cFives = scoreCardArray.get(4).value;
            } else {
                cFives = sFives;
            }
            if(scoreCardArray.get(5).isSelected){
                //store value
                editor.putInt("sixesValue", scoreCardArray.get(5).value);
                //store isComplete
                editor.putBoolean("isSixesComplete", true);
                editor.apply();
                cSixes = scoreCardArray.get(5).value;
            } else {
                cSixes = sSixes;
            }
            if(scoreCardArray.get(8).isSelected){
                //store value
                editor.putInt("threeOfAKindValue", scoreCardArray.get(8).value);
                //store isComplete
                editor.putBoolean("isThreeOfAKindComplete", true);
                editor.apply();
                cThreeOfAKind = scoreCardArray.get(8).value;
            } else {
                cThreeOfAKind = sThreeOfAKind;
            }
            if(scoreCardArray.get(9).isSelected){
                //store value
                editor.putInt("fourOfAKindValue", scoreCardArray.get(9).value);
                //store isComplete
                editor.putBoolean("isFourOfAKindComplete", true);
                editor.apply();
                cFourOfAKind = scoreCardArray.get(9).value;
            } else {
                cFourOfAKind = sFourOfAKind;
            }
            if(scoreCardArray.get(10).isSelected){
                //store value
                editor.putInt("fullHouseValue", scoreCardArray.get(10).value);
                //store isComplete
                editor.putBoolean("isFullHouseComplete", true);
                editor.apply();
                cFullHouse = scoreCardArray.get(10).value;
            } else {
                cFullHouse = sFullHouse;
            }
            if(scoreCardArray.get(11).isSelected){
                //store value
                editor.putInt("smallStraightValue", scoreCardArray.get(11).value);
                //store isComplete
                editor.putBoolean("isSmallStraightComplete", true);
                editor.apply();
                cSmallStraight = scoreCardArray.get(11).value;
            } else {
                cSmallStraight = sSmallStraight;
            }
            if(scoreCardArray.get(12).isSelected){
                //store value
                editor.putInt("largeStraightValue", scoreCardArray.get(12).value);
                //store isComplete
                editor.putBoolean("isLargeStraightComplete", true);
                editor.apply();
                cLargeStraight = scoreCardArray.get(12).value;
            } else {
                cLargeStraight = sLargeStraight;
            }
            if(scoreCardArray.get(13).isSelected){
                //store value
                editor.putInt("isYahtzeeValue", scoreCardArray.get(13).value);
                //store isComplete
                editor.putBoolean("isYahtzeeComplete", true);
                editor.apply();
                cYahtzee = scoreCardArray.get(13).value;
            } else {
                cYahtzee = sYahtzee;
            }
            if(scoreCardArray.get(14).isSelected){
                //store value
                editor.putInt("isChanceValue", scoreCardArray.get(14).value);
                //store isComplete
                editor.putBoolean("isChanceComplete", true);
                editor.apply();
                cChance = scoreCardArray.get(14).value;
            } else {
                cChance = sChance;
            }

            if(isMultiYahtzee){
                editor.putInt("numberOfYahtzeesValue", sNumberOfYahtzees);
                editor.apply();
                cNumberOfYahtzees = scoreCardArray.get(15).value;
            } else {
                cNumberOfYahtzees = sNumberOfYahtzees;
            }

            //UPPER TOTAL
            upperTotal = cOnes + cTwos + cThrees + cFours + cFives + cSixes;

            //LOWER TOTAL
            lowerTotal = cThreeOfAKind + cFourOfAKind + cFullHouse + cSmallStraight
                    + cLargeStraight + cYahtzee + cChance + (cNumberOfYahtzees * 100);

            grandTotal = upperTotal + lowerTotal;

            //update grand total
            editor.putInt("score", grandTotal);
            editor.apply();
            Log.d("test", "score card: grand total = " + grandTotal);



            checkEndGame();


        }
        //selection is not made
        else{

            //tell user to select something using an alert
            new AlertDialog.Builder(this)
                    .setTitle("Select a Category")
                    .setMessage("Cannot record. Please select a category to record a score.")

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("OK", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }


    }

    private void goToRollScreen(){

        //reset the roll number in roll screen
        editor.putInt("rollNumber", 0);
        editor.apply();

        //go to roll screen
        Intent intent = new Intent(this, RollScreenActivity.class);
        startActivity(intent);
    }


    private void cancelPressed(){

        Log.d("test", "rollNumber = " + rollNumber);

        if(rollNumber < 3){

            //send the roll number
            editor.putInt("rollNumber", rollNumber);
            editor.apply();

            //close this screen and go to roll screen
            Intent intent = new Intent(this, RollScreenActivity.class);
            startActivity(intent);

        }
        else{

            //tell user they have no more rolls and they must make a selection with alert
            new AlertDialog.Builder(this)
                    .setTitle("Please make a selection")
                    .setMessage("You have no remaining rolls. Please select a category and record your score.")


                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("OK", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

    }


    //call this when this screen is about to close in some way other than clicking the record button
    //(unused)
    private void prepare(){

        //record if there is a selection
        if(isSelectionMade){
            //reset the roll number in roll screen
            rollScreenActivity.rollNumber = 0;
            record();

            //todo close this screen and open roll screen
        }

        else {
            //todo store values
        }


    }

    //check if the game is over... if it is end the game
    private void checkEndGame(){

        boolean isEnd = false;
        int categoryCompleteCounter = 0;

        for (int i = 0; i < scoreCardArray.size(); i++) {

            ScoreLineModel score = scoreCardArray.get(i);

            if(score.isCompleted || score.isSelected){
                categoryCompleteCounter = categoryCompleteCounter + 1;
            }

        }

        if(categoryCompleteCounter >= 13){
            isEnd = true;
        }

        if(isEnd){

            Log.d("test", "isEnd. score = " + grandTotal);

            //store the score
            editor.putInt("score", grandTotal);
            editor.apply();

            //go to high score screen
            Intent intent = new Intent(this, HighScoreActivity.class);
            startActivity(intent);

        } else {
            goToRollScreen();
        }

    }





}