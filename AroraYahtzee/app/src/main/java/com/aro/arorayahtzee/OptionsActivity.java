package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {


    Button muteTitleButton;
    Button muteSFXButton;
    Button resetScoresButton;
    Button closeButton;


    Boolean isSoundFXMuted = false;
    Boolean isTitleSoundMuted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        muteTitleButton = findViewById(R.id.mute_title_button_optionsscreen);
        muteSFXButton = findViewById(R.id.mute_sound_fx_button);
        resetScoresButton = findViewById(R.id.reset_high_score_button);
        closeButton = findViewById(R.id.close_button_optionscreen);

        //todo use user defaults to store bools for mute state of sfx and title

        //todo set local bools based on stored values

        //todo change button colors based on bools muted = red, not muted = false... and also change text to match

        muteTitleButton.setOnClickListener((View v) -> {
            titleSoundToggle();
        });

        muteSFXButton.setOnClickListener((View v) -> {
            sfxToggle();
        });

        resetScoresButton.setOnClickListener((View v) -> {
            scoreReset();
        });

        closeButton.setOnClickListener((View v) -> {
            closeOptionScreen();
        });

    }


    private void titleSoundToggle(){
        //todo
    }

    private void sfxToggle(){
        //todo
    }

    private void scoreReset(){
        //todo... remember to ask user if they are sure
    }

    private void closeOptionScreen(){
        //todo
    }



}