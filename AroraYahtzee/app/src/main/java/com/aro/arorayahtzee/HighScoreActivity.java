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
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    Button closeButton;

    public static final String MyPREFERENCES = "myprefs";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterHighScoreScreen adapter;

    Integer newScore = 0;
    Boolean isNewHighScore = false;
    Integer numberNewScoreIsHigherThan = 0;
    ArrayList<SavedScoreModel> highScoreList = new ArrayList<SavedScoreModel>();
    String nameEntered = "x";

    Integer defaultScore = 0;
    String defaultName = "Player";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        closeButton = findViewById(R.id.close_button_highscorescreen);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        recyclerView = findViewById(R.id.recyclerview_highscorescreen);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        //test list
        highScoreList.add(0, new SavedScoreModel(0, "name1"));

        adapter = new AdapterHighScoreScreen(this, highScoreList);
        recyclerView.setAdapter(adapter);


        closeButton.setOnClickListener((View v) -> {
            closeButtonPressed();
        });

        getHighScore();

        //if player is coming from a game, check if they have a new high score
        checkIfNewScore();

    }


    private void getHighScore(){

        //get high scores from saved values, or set to defaults
        SavedScoreModel score1 = new SavedScoreModel(pref.getInt("highscore1", defaultScore)
                , pref.getString("highscore1name", defaultName ));
        SavedScoreModel score2 = new SavedScoreModel(pref.getInt("highscore2", defaultScore)
                , pref.getString("highscore2name", defaultName ));
        SavedScoreModel score3 = new SavedScoreModel(pref.getInt("highscore3", defaultScore)
                , pref.getString("highscore3name", defaultName ));
        SavedScoreModel score4 = new SavedScoreModel(pref.getInt("highscore4", defaultScore)
                , pref.getString("highscore4name", defaultName ));
        SavedScoreModel score5 = new SavedScoreModel(pref.getInt("highscore5", defaultScore)
                , pref.getString("highscore5name", defaultName ));
        SavedScoreModel score6 = new SavedScoreModel(pref.getInt("highscore6", defaultScore)
                , pref.getString("highscore6name", defaultName ));
        SavedScoreModel score7 = new SavedScoreModel(pref.getInt("highscore7", defaultScore)
                , pref.getString("highscore7name", defaultName ));
        SavedScoreModel score8 = new SavedScoreModel(pref.getInt("highscore8", defaultScore)
                , pref.getString("highscore8name", defaultName ));
        SavedScoreModel score9 = new SavedScoreModel(pref.getInt("highscore9", defaultScore)
                , pref.getString("highscore9name", defaultName ));
        SavedScoreModel score10 = new SavedScoreModel(pref.getInt("highscore10", defaultScore)
                , pref.getString("highscore10name", defaultName ));

        //set scores to array
        highScoreList.add(0, score1);
        highScoreList.add(1, score2);
        highScoreList.add(2, score3);
        highScoreList.add(3, score4);
        highScoreList.add(4, score5);
        highScoreList.add(5, score6);
        highScoreList.add(6, score7);
        highScoreList.add(7, score8);
        highScoreList.add(8, score9);
        highScoreList.add(9, score10);

//        //if there are no scores saved, save that the scores are defaults
//        if(highScoreList.get(0).score == defaultScore){
//            saveNewHighScores();
//        }

        //todo TEST sort?  ... should not be needed...

        //reload data in recyclerview
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    private void checkIfNewScore(){

        newScore = pref.getInt("score", 0);

        Log.d("test", "HighScore: newScore " + newScore);

        if(newScore > 0){

            //if there is a new high score, then show the UI for it
            compareToHighScores();
        }
    }

    private void compareToHighScores(){

        //this counts the number of scores that are beaten by the new one
        for (int i = 0; i < highScoreList.size() - 1; i++) {

            SavedScoreModel highScore = highScoreList.get(i);

            if(newScore > highScore.score){
                isNewHighScore = true;
                numberNewScoreIsHigherThan = numberNewScoreIsHigherThan + 1;
                Log.d("test", "High Score: numberhigherthan = " + numberNewScoreIsHigherThan);
            }
        }

        if(isNewHighScore){
            showNewHighScoreUI();
        } else {
            showNotHighScoreUI();
        }


    }



    private void showNewHighScoreUI(){
        //edittext for entering name
        EditText nameEditText = new EditText(this);
        nameEditText.setPadding(8,8,8,8);

        new AlertDialog.Builder(this)
                .setTitle("Score: " + newScore)
                .setMessage("New High Score! Enter you name.")

                //display an EditText box
                .setView(nameEditText)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //get the user name input as string
                        nameEntered = String.valueOf(nameEditText.getText());

                        //remove the lowest score
                        highScoreList.remove(9);

                        //insert the new score model above scores it is higher than
                        highScoreList.add((10 - numberNewScoreIsHigherThan), new SavedScoreModel(newScore, nameEntered));

                        //save to persistent data
                        saveNewHighScores();

                        //reload data in recyclerview
                        recyclerView.getAdapter().notifyDataSetChanged();

                    }
                })

                .show();

    }


    private void showNotHighScoreUI(){

        new AlertDialog.Builder(this)
                .setTitle("Score: " + newScore)
                .setMessage("Game Over. You did not get a new high score.")

                .setPositiveButton("OK", null)

                .show();

    }



    private void saveNewHighScores(){

        Log.d("test", "HighScore: save method: list size " + highScoreList.size());

        editor.putInt("highscore1", highScoreList.get(0).score);
        editor.putString("highscore1name", highScoreList.get(0).playerName);
        editor.putInt("highscore2", highScoreList.get(1).score);
        editor.putString("highscore2name", highScoreList.get(1).playerName);
        editor.putInt("highscore3", highScoreList.get(2).score);
        editor.putString("highscore3name", highScoreList.get(2).playerName);
        editor.putInt("highscore4", highScoreList.get(3).score);
        editor.putString("highscore4name", highScoreList.get(3).playerName);
        editor.putInt("highscore5", highScoreList.get(4).score);
        editor.putString("highscore5name", highScoreList.get(4).playerName);
        editor.putInt("highscore6", highScoreList.get(5).score);
        editor.putString("highscore6name", highScoreList.get(5).playerName);
        editor.putInt("highscore7", highScoreList.get(6).score);
        editor.putString("highscore7name", highScoreList.get(6).playerName);
        editor.putInt("highscore8", highScoreList.get(7).score);
        editor.putString("highscore8name", highScoreList.get(7).playerName);
        editor.putInt("highscore9", highScoreList.get(8).score);
        editor.putString("highscore9name", highScoreList.get(8).playerName);
        editor.putInt("highscore10", highScoreList.get(9).score);
        editor.putString("highscore10name", highScoreList.get(9).playerName);

        editor.apply();

    }

    private void closeButtonPressed(){
        //go to title screen
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
        finish();
    }




}