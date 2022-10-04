package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterHighScoreScreen adapter;

    Integer newScore = 0;
    Boolean isNewHighScore = false;
    Integer numberNewScoreIsHigherThan = 0;
    ArrayList<SavedScoreModel> highScoreList = new ArrayList<SavedScoreModel>();
    String nameEntered = "x";

    //todo user defaults




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        //test list
        highScoreList.add(0, new SavedScoreModel(99, "name1"));
        highScoreList.add(1, new SavedScoreModel(2000, "name2"));
        highScoreList.add(2, new SavedScoreModel(205, "name3"));

        recyclerView = findViewById(R.id.recyclerview_highscorescreen);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new AdapterHighScoreScreen(this, highScoreList);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        getHighScore();

        //if player is coming from a game, check if they have a new high score
        checkIfNewScore();

    }

    //todo setup recyclerview cellforrowat , numberofrowsinsection


    private void getHighScore(){
        //todo
    }


    private void checkIfNewScore(){
        //todo
    }




}