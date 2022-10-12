package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    Button newGameButton;
    Button optionsButton;
    Button highScoreButton;


    public static final String MyPREFERENCES = "myprefs";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Boolean isMuted = false;

    MediaPlayer titleSoundPlayer;

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        newGameButton = findViewById(R.id.newgame_button);
        optionsButton = findViewById(R.id.options_button);
        highScoreButton = findViewById(R.id.highscore_button);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        //todo UPDATE record more title sounds / edit dice clip to clean up start and end
        titleSoundPlayer = MediaPlayer.create(this, R.raw.dice3);

        resetGameData();

        //set isMuted from defaults
        isMuted = pref.getBoolean("titleScreenMute", false);

        if(!isMuted){
            //play sound from titleSoundPlayer
            titleSoundPlayer.start();
        }

        newGameButton.setOnClickListener((View v) -> {
            newGamePressed();
        });
        optionsButton.setOnClickListener((View v) -> {
            optionsPressed();
        });
        highScoreButton.setOnClickListener((View v) -> {
            highScorePressed();
        });


    }



    private void newGamePressed(){

        //go to roll screen
        Intent intent = new Intent(this, RollScreenActivity.class);
        startActivity(intent);
    }

    private void optionsPressed(){

        //go to options screen
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    private void highScorePressed(){

        //go to high score screen
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }


    private void resetGameData(){

        //reset all defaults
        editor.remove("onesValue");
        editor.remove("isOnesComplete");
        editor.remove("twosValue");
        editor.remove("isTwosComplete");
        editor.remove("threesValue");
        editor.remove("isThreesComplete");
        editor.remove("foursValue");
        editor.remove("isFoursComplete");
        editor.remove("fivesValue");
        editor.remove("isFivesComplete");
        editor.remove("sixesValue");
        editor.remove("isSixesComplete");
        editor.remove("threeOfAKindValue");
        editor.remove("isThreeOfAKindComplete");
        editor.remove("fourOfAKindValue");
        editor.remove("isFourOfAKindComplete");
        editor.remove("fullHouseValue");
        editor.remove("isFullHouseComplete");
        editor.remove("smallStraightValue");
        editor.remove("isSmallStraightComplete");
        editor.remove("largeStraightValue");
        editor.remove("isLargeStraightComplete");
        editor.remove("isYahtzeeValue");
        editor.remove("isYahtzeeComplete");
        editor.remove("isChanceValue");
        editor.remove("isChanceComplete");
        editor.remove("numberOfYahtzeesValue");
        editor.remove("isSelectionMade");
        editor.remove("selectedIndexScoreCard");
        editor.remove("rollNumber");
        editor.remove("score");
        editor.apply();


    }



}