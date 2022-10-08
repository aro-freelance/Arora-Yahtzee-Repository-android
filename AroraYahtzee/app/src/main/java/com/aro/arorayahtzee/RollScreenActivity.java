package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class RollScreenActivity extends AppCompatActivity {

    //todo add assets to project ... dice face images, sounds

    public static final String MyPREFERENCES = "myprefs";

    ImageButton diceButton1;
    ImageButton diceButton2;
    ImageButton diceButton3;
    ImageButton diceButton4;
    ImageButton diceButton5;

    Button scoreCardButton;
    Button rollButton;
    Button quitButton;

    TextView scoreTextView;

    Boolean isSelectedDice1 = false;
    Boolean isSelectedDice2 = false;
    Boolean isSelectedDice3 = false;
    Boolean isSelectedDice4 = false;
    Boolean isSelectedDice5 = false;

    //array of dice side images
    ArrayList<Integer> diceArrayImage = new ArrayList<>();

    //array of image buttons
    ArrayList<ImageButton> selectedButtonArray = new ArrayList<>();

    //array of selected values
    ArrayList<Integer> selectedValuesArray = new ArrayList<>();

    Integer diceNumberOne = 0;
    Integer diceNumberTwo = 0;
    Integer diceNumberThree = 0;
    Integer diceNumberFour = 0;
    Integer diceNumberFive = 0;

    //number of times user has rolled (max is 3 by convention of game)
    Integer rollNumber = 0;
    Integer maxRolls = 3;

    Integer score = 0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;



    //todo audioplayer roll sfx

    //todo audioplayer yahtzee sfx
    //todo audioplayer music

    Boolean isMuted = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_main);

        diceButton1 = findViewById(R.id.dice1_imagebutton);
        diceButton2 = findViewById(R.id.dice2_imagebutton);
        diceButton3 = findViewById(R.id.dice3_imagebutton);
        diceButton4 = findViewById(R.id.dice4_imagebutton);
        diceButton5 = findViewById(R.id.dice5_imagebutton);

        scoreCardButton = findViewById(R.id.scorecard_button);
        rollButton = findViewById(R.id.roll_button);
        quitButton = findViewById(R.id.quit_button);

        scoreTextView = findViewById(R.id.score_textview);


        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        //set dice images to array

        diceArrayImage.add(R.drawable.dice1);
        diceArrayImage.add(R.drawable.dice2);
        diceArrayImage.add(R.drawable.dice3);
        diceArrayImage.add(R.drawable.dice4);
        diceArrayImage.add(R.drawable.dice5);
        diceArrayImage.add(R.drawable.dice6);

        //////

        Log.d("test", "Roll Number: " + rollNumber);


        if(rollNumber == 0){
            diceButton1.setVisibility(View.INVISIBLE);
            diceButton2.setVisibility(View.INVISIBLE);
            diceButton3.setVisibility(View.INVISIBLE);
            diceButton4.setVisibility(View.INVISIBLE);
            diceButton5.setVisibility(View.INVISIBLE);
            scoreCardButton.setVisibility(View.INVISIBLE);
        }

        scoreTextView.setText("");

        diceButton1.setEnabled(false);
        diceButton2.setEnabled(false);
        diceButton3.setEnabled(false);
        diceButton4.setEnabled(false);
        diceButton5.setEnabled(false);

        updateScore();

        //todo initialize roll sound
        //todo initialize yahtzee sound

        isMuted = pref.getBoolean("soundEffectMute", false);

        diceButton1.setOnClickListener((View v) -> {
            diceButtonSelected(1);
        });

        diceButton2.setOnClickListener((View v) -> {
            diceButtonSelected(2);
        });

        diceButton3.setOnClickListener((View v) -> {
            diceButtonSelected(3);
        });

        diceButton4.setOnClickListener((View v) -> {
            diceButtonSelected(4);
        });

        diceButton5.setOnClickListener((View v) -> {
            diceButtonSelected(5);
        });

        rollButton.setOnClickListener((View v) -> {
            rollButtonPressed();
        });

        quitButton.setOnClickListener((View v) -> {
            quitToTitle();
        });

        scoreCardButton.setOnClickListener((View v) -> {
            goToScoreScreen();
        });

    }

    private void updateScore(){
        //get stored score
        score = pref.getInt("score", 0);

        Log.d("test", "roll screen update score: score = " + score);

        //set score to UI
        scoreTextView.setText("Score : " + score);
    }

    private void diceButtonSelected(Integer diceId){

        if(diceId != null){

            switch (diceId){

                case 1:
                    if(isSelectedDice1){
                        isSelectedDice1 = false;

                        diceButton1.setBackgroundColor(Color.BLACK);

                        if(!selectedButtonArray.contains(diceButton1)){
                            selectedButtonArray.add(diceButton1);
                            selectedValuesArray.add(diceNumberOne);
                        }

                    } else {
                        isSelectedDice1 = true;

                        diceButton1.setBackgroundColor(Color.TRANSPARENT);

                        selectedButtonArray.remove(diceButton1);
                        selectedValuesArray.remove(diceNumberOne);
                    }

                    break;

                case 2:
                    if(isSelectedDice2){
                        isSelectedDice2 = false;

                        diceButton2.setBackgroundColor(Color.BLACK);

                        if(!selectedButtonArray.contains(diceButton2)){
                            selectedButtonArray.add(diceButton2);
                            selectedValuesArray.add(diceNumberTwo);
                        }

                    } else {
                        isSelectedDice2 = true;

                        diceButton2.setBackgroundColor(Color.TRANSPARENT);

                        selectedButtonArray.remove(diceButton2);
                        selectedValuesArray.remove(diceNumberTwo);
                    }

                    break;

                case 3:
                    if(isSelectedDice3){
                        isSelectedDice3 = false;

                        diceButton3.setBackgroundColor(Color.BLACK);

                        if(!selectedButtonArray.contains(diceButton3)){
                            selectedButtonArray.add(diceButton3);
                            selectedValuesArray.add(diceNumberThree);
                        }

                    } else {
                        isSelectedDice3 = true;

                        diceButton3.setBackgroundColor(Color.TRANSPARENT);

                        selectedButtonArray.remove(diceButton3);
                        selectedValuesArray.remove(diceNumberThree);
                    }

                    break;

                case 4:
                    if(isSelectedDice4){
                        isSelectedDice4 = false;

                        diceButton4.setBackgroundColor(Color.BLACK);

                        if(!selectedButtonArray.contains(diceButton4)){
                            selectedButtonArray.add(diceButton4);
                            selectedValuesArray.add(diceNumberFour);
                        }

                    } else {
                        isSelectedDice4 = true;

                        diceButton4.setBackgroundColor(Color.TRANSPARENT);

                        selectedButtonArray.remove(diceButton4);
                        selectedValuesArray.remove(diceNumberFour);
                    }

                    break;

                case 5:
                    if(isSelectedDice5){
                        isSelectedDice5 = false;

                        diceButton5.setBackgroundColor(Color.BLACK);

                        if(!selectedButtonArray.contains(diceButton5)){
                            selectedButtonArray.add(diceButton5);
                            selectedValuesArray.add(diceNumberFive);
                        }

                    } else {
                        isSelectedDice5 = true;

                        diceButton5.setBackgroundColor(Color.TRANSPARENT);

                        selectedButtonArray.remove(diceButton5);
                        selectedValuesArray.remove(diceNumberFive);
                    }

                    break;

                default:
                    Log.d("test", "dice button selection error");


            }
        }

    }

    private void rollButtonPressed(){

        diceButton1.setVisibility(View.VISIBLE);
        diceButton2.setVisibility(View.VISIBLE);
        diceButton3.setVisibility(View.VISIBLE);
        diceButton4.setVisibility(View.VISIBLE);
        diceButton5.setVisibility(View.VISIBLE);

        scoreCardButton.setVisibility(View.VISIBLE);

        diceButton1.setEnabled(true);
        diceButton2.setEnabled(true);
        diceButton3.setEnabled(true);
        diceButton4.setEnabled(true);
        diceButton5.setEnabled(true);

        rollButton.setEnabled(false);

        rollNumber = rollNumber + 1;

        if(rollNumber <= maxRolls){
            roll();
        } else {
            Log.d("test", "max rolls, show alert and go to scorecard after user closes alert");

            //alert user that they have rolled the max times,
            //then when they close alert go to scorecard screen
            new AlertDialog.Builder(this)
                    .setTitle("No Rolls Remaining")
                    .setMessage("Record your roll on the scorecard.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Scorecard", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            goToScoreScreen();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Back", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        rollButton.setEnabled(true);

    }

    private void roll(){

        if(!isMuted){
            //todo play roll sfx
        } else {
            Log.d("test", "roll sound while muted");
        }

        //random number
        //change imageview to image in array at the position
        if(!isSelectedDice1){
            diceNumberOne = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberOne - 1);
            diceButton1.setImageResource(imageInt);
            Log.d("test", "dice 1 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice2){
            diceNumberTwo = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberTwo - 1);
            diceButton2.setImageResource(imageInt);
            Log.d("test", "dice 2 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice3){
            diceNumberThree = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberThree - 1);
            diceButton3.setImageResource(imageInt);
            Log.d("test", "dice 3 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice4){
            diceNumberFour = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberFour - 1);
            diceButton4.setImageResource(imageInt);
            Log.d("test", "dice 4 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice5){
            diceNumberFive = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberFive - 1);
            diceButton5.setImageResource(imageInt);
            Log.d("test", "dice 5 selected. imageInt = " + imageInt);
        }

        if(!isMuted){
            //delay yahtzee sound after roll sound
            float secondToDelay = 0.6f;
            if(  diceNumberOne == diceNumberTwo &&
                 diceNumberOne == diceNumberThree &&
                 diceNumberOne == diceNumberFour &&
                 diceNumberOne == diceNumberFive){

                //todo add delay { // play sfx yahtzee }

                Log.d("test", "yahtzee sound after delay");
            }
        } else {
            Log.d("test", "muted yahtzee sound");
        }



    }


    private void goToScoreScreen(){

        //send dice values and roll number to score screen by intent
        //go to score screen
        Intent intent = new Intent(this, ScoreScreenActivity.class);
        intent.putExtra("diceValue1", diceNumberOne);
        intent.putExtra("diceValue2", diceNumberTwo);
        intent.putExtra("diceValue3", diceNumberThree);
        intent.putExtra("diceValue4", diceNumberFour);
        intent.putExtra("diceValue5", diceNumberFive);
        intent.putExtra("rollNumber", rollNumber);
        startActivity(intent);
    }

    private void quitToTitle(){
        //go to title
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
    }


}