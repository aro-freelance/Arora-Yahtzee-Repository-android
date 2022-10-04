package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    //todo lots of updates needed regarding score saving and comparison... fillout the psudeocoded sections

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
        highScoreList.add(0, new SavedScoreModel(0, "name1"));

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


    private void getHighScore(){
        //set high score array to the values saved in defaults

        //if there are no saved scores set high score array to a default list
        if(highScoreList.get(0).score == 0){
            highScoreList.add(0, new SavedScoreModel(0, "Player"));
            highScoreList.add(1, new SavedScoreModel(0, "Player"));
            highScoreList.add(2, new SavedScoreModel(0, "Player"));
            highScoreList.add(3, new SavedScoreModel(0, "Player"));
            highScoreList.add(4, new SavedScoreModel(0, "Player"));
            highScoreList.add(5, new SavedScoreModel(0, "Player"));
            highScoreList.add(6, new SavedScoreModel(0, "Player"));
            highScoreList.add(7, new SavedScoreModel(0, "Player"));
            highScoreList.add(8, new SavedScoreModel(0, "Player"));
            highScoreList.add(9, new SavedScoreModel(0, "Player"));

            saveNewHighScores();
        }

        //todo sort?

        //reload data in recyclerview
        adapter.notifyDataSetChanged();
    }


    private void checkIfNewScore(){
        //todo

        //todo translate from xcode
        //newScore = defaults.integer(forKey: "score");

        if(newScore > 0){

            //if there is a new high score, then show the UI for it
            compareToHighScores();
        }
    }


    private void showNewHighScoreUI(){
        //todo
    }


    private void showNotHighScoreUI(){
        //todo

    }

    private void compareToHighScores(){
        //todo


        //**** this counts the number of scores that are beaten by the new one... to place it in order
        //for high score in highscorelist
          //if new score > highScore.score
             //isNewHighScore = true
             //numberNewScoreIsHighThan += 1

        //if isNewHighScore shownewhighscoreui
        //else show notnewhighscoreui

    }

    private void saveNewHighScores(){
        //todo
        //set highscorelist to defaults
    }




}