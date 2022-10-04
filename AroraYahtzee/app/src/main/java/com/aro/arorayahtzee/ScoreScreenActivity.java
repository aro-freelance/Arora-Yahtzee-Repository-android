package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreScreenActivity extends AppCompatActivity {

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
        //todo
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