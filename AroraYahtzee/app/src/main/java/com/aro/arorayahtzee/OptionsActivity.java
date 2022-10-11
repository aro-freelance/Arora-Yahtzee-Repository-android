package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "myprefs";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button muteTitleButton;
    Button muteSFXButton;
    Button resetScoresButton;
    Button closeButton;


    Boolean isSoundFXMuted = false;
    Boolean isTitleSoundMuted = false;

    Integer defaultScore = 0;
    String defaultName = "Player";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        muteTitleButton = findViewById(R.id.mute_title_button_optionsscreen);
        muteSFXButton = findViewById(R.id.mute_sound_fx_button);
        resetScoresButton = findViewById(R.id.reset_high_score_button);
        closeButton = findViewById(R.id.close_button_optionscreen);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        isSoundFXMuted = pref.getBoolean("soundEffectMute", false);
        isTitleSoundMuted = pref.getBoolean("titleScreenMute", false);

        if(isSoundFXMuted){
            muteSFXButton.setBackgroundColor(Color.RED);
            muteSFXButton.setText("Unmute Sound Effects");
            muteSFXButton.setTextColor(Color.WHITE);
        } else {
            muteSFXButton.setBackgroundColor(Color.GREEN);
            muteSFXButton.setText("Mute Sound Effects");
            muteSFXButton.setTextColor(Color.BLACK);
        }

        if(isTitleSoundMuted){
            muteTitleButton.setBackgroundColor(Color.RED);
            muteTitleButton.setText("Unmute Title Screen");
            muteTitleButton.setTextColor(Color.WHITE);
        } else {
            muteTitleButton.setBackgroundColor(Color.GREEN);
            muteTitleButton.setText("Mute Title Screen");
            muteTitleButton.setTextColor(Color.BLACK);
        }




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

        //if currently muted... unmute and set UI
        if (isTitleSoundMuted){
            isTitleSoundMuted = false;
            muteTitleButton.setBackgroundColor(Color.GREEN);
            muteTitleButton.setText("Mute Title Screen");
            muteTitleButton.setTextColor(Color.BLACK);
        }
        //if currently unmuted... mute and set UI
        else {
            isTitleSoundMuted = true;
            muteTitleButton.setBackgroundColor(Color.RED);
            muteTitleButton.setText("Unmute Title Screen");
            muteTitleButton.setTextColor(Color.WHITE);
        }

        //save setting
        editor.putBoolean("titleScreenMute", isTitleSoundMuted);
        editor.apply();

    }

    private void sfxToggle(){

        //if currently muted... unmute and set UI
        if (isSoundFXMuted){
            isSoundFXMuted = false;
            muteSFXButton.setBackgroundColor(Color.GREEN);
            muteSFXButton.setText("Mute Sound Effects");
            muteSFXButton.setTextColor(Color.BLACK);
        }
        //if currently unmuted... mute and set UI
        else {
            isSoundFXMuted = true;
            muteSFXButton.setBackgroundColor(Color.RED);
            muteSFXButton.setText("Unmute Sound Effects");
            muteSFXButton.setTextColor(Color.WHITE);
        }

        //save setting
        editor.putBoolean("soundEffectMute", isSoundFXMuted);
        editor.apply();

    }

    private void scoreReset(){
        //ask user if they are sure
        //then reset scores
        new AlertDialog.Builder(this)
                .setTitle("Reset High Scores?")
                .setMessage("All high scores will be permanently erased.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetScores();
                        Log.d("test", "Options: reset high scores");
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("test", "Options: cancel reset");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void resetScores(){
        //set all ten high scores to defaults
        editor.putInt("highscore1", defaultScore);
        editor.putString("highscore1name", defaultName);
        editor.putInt("highscore2", defaultScore);
        editor.putString("highscore2name", defaultName);
        editor.putInt("highscore3", defaultScore);
        editor.putString("highscore3name", defaultName);
        editor.putInt("highscore4", defaultScore);
        editor.putString("highscore4name", defaultName);
        editor.putInt("highscore5", defaultScore);
        editor.putString("highscore5name", defaultName);
        editor.putInt("highscore6", defaultScore);
        editor.putString("highscore6name", defaultName);
        editor.putInt("highscore7", defaultScore);
        editor.putString("highscore7name", defaultName);
        editor.putInt("highscore8", defaultScore);
        editor.putString("highscore8name", defaultName);
        editor.putInt("highscore9", defaultScore);
        editor.putString("highscore9name", defaultName);
        editor.putInt("highscore10", defaultScore);
        editor.putString("highscore10name", defaultName);


        editor.apply();
    }

    private void closeOptionScreen(){
        //go to title screen
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
        finish();
    }



}