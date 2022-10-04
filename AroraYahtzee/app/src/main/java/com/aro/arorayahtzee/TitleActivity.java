package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    Button newGameButton;
    Button optionsButton;
    Button highScoreButton;


    //todo user defaults

    Boolean isMuted = false;

    //todo audioplayer
    //AudioPlayer? titleSoundPlayer = new AudioPlayer()?

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        newGameButton = findViewById(R.id.newgame_button);
        optionsButton = findViewById(R.id.options_button);
        highScoreButton = findViewById(R.id.highscore_button);

        resetGameData();

        //todo set isMuted from defaults
        //isMuted =

        //todo if sound exists set it to audioplayer


        if(!isMuted){
            //todo playsound from titleSoundPlayer
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

        //todo reset all defaults
        //ex. in xcode... defaults.removeObject(forKey: "onesValue");
        //etc... for all values on score sheet

    }



}