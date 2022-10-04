package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreScreenActivity extends AppCompatActivity {

    TextView scoreText;

    AdapterScoreCard adapter;

    ArrayList<ScoreLineModel> scoreCardArray = new ArrayList<ScoreLineModel>();

    Boolean isMultiYahtzee = false;
    Integer upperTotal = 0;
    Integer lowerTotal = 0;
    Boolean isSelectionMade = false;
    Integer selectedIndex = -1;
    Integer bonus = 0;
    Integer grandTotal = 0;

    //stored
    Integer sNumberOfYahtzees = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        adapter = new AdapterScoreCard(this, scoreCardArray);

        scoreText = findViewById(R.id.score_textView_scorescreen);


    }


}