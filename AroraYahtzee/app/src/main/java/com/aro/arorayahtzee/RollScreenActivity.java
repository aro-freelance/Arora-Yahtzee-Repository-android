package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class RollScreenActivity extends AppCompatActivity {

    //todo BUG selection issue. after going to scorecard (not sure if that detail is important)
    // when I dicebutton1 it selected and unselected dicebutton5. other dicebuttons as well were,
    // at least based on the UI tint indicator not selecting/unselecting properly
    // while testing this it seemed related to having all selected possibly...
    // was working fine until i selected all 5
    // NEXT----- use Logs in the selection blocks to debug...

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


    MediaPlayer rollsSFXPlayer;
    MediaPlayer yahtzeeSFXPlayer;


    //todo UPDATE add music,
    // start() in onCreate
    // and add a music mute bool and button to options screen
    MediaPlayer musicPlayer;

    Boolean isMuted = false;

    Integer alphaValueOfSelection = 100;
    Integer redValueOfSelection = 50;
    Integer greenValueOfSelection = 50;
    Integer blueValueOfSelection = 50;



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

        diceButton1.setEnabled(false);
        diceButton2.setEnabled(false);
        diceButton3.setEnabled(false);
        diceButton4.setEnabled(false);
        diceButton5.setEnabled(false);

        //if score screen was cancelled this will be > 0
        rollNumber = pref.getInt("rollNumber", 0);

        //new set of dice rolls
        if(rollNumber == 0){
            diceButton1.setVisibility(View.INVISIBLE);
            diceButton2.setVisibility(View.INVISIBLE);
            diceButton3.setVisibility(View.INVISIBLE);
            diceButton4.setVisibility(View.INVISIBLE);
            diceButton5.setVisibility(View.INVISIBLE);
            scoreCardButton.setVisibility(View.INVISIBLE);
        }
        //user returned from looking at the scorecard. restore their last dice roll to UI
        else {
            Bundle bundle = getIntent().getExtras();
            // get intent bundle from score activity
            if (bundle != null) {
                diceNumberOne = bundle.getInt("diceValue1");
                diceNumberTwo = bundle.getInt("diceValue2");
                diceNumberThree = bundle.getInt("diceValue3");
                diceNumberFour = bundle.getInt("diceValue4");
                diceNumberFive = bundle.getInt("diceValue5");

                int imageInt1 = diceArrayImage.get(diceNumberOne - 1);
                diceButton1.setImageResource(imageInt1);
                int imageInt2 = diceArrayImage.get(diceNumberTwo - 1);
                diceButton2.setImageResource(imageInt2);
                int imageInt3 = diceArrayImage.get(diceNumberThree - 1);
                diceButton3.setImageResource(imageInt3);
                int imageInt4 = diceArrayImage.get(diceNumberFour - 1);
                diceButton4.setImageResource(imageInt4);
                int imageInt5 = diceArrayImage.get(diceNumberFive - 1);
                diceButton5.setImageResource(imageInt5);

                diceButton1.setEnabled(true);
                diceButton2.setEnabled(true);
                diceButton3.setEnabled(true);
                diceButton4.setEnabled(true);
                diceButton5.setEnabled(true);

                //todo UPDATE save and restore selection status of dice?

            }
            // intent bundle is null
            else {

                Log.d("test", "RollScreen: ERROR failed to load intent bundle from score activity");
            }
        }

        updateScoreUIText();



        //initialize roll sound
        rollsSFXPlayer = MediaPlayer.create(this, R.raw.dice4);

        //initialize yahtzee sound
        yahtzeeSFXPlayer = MediaPlayer.create(this, R.raw.yahtzee1crop);

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

    private void updateScoreUIText(){
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

                        diceButton1.setImageTintList(ColorStateList.valueOf(Color.argb(0, 0, 0, 0)));


                        selectedButtonArray.remove(diceButton1);
                        selectedValuesArray.remove(diceNumberOne);


                    } else {
                        isSelectedDice1 = true;

                        diceButton1.setImageTintList(ColorStateList.valueOf(Color.argb(
                                alphaValueOfSelection, redValueOfSelection, greenValueOfSelection, blueValueOfSelection)));


                        if(!selectedButtonArray.contains(diceButton1)){
                            selectedButtonArray.add(diceButton1);
                            selectedValuesArray.add(diceNumberOne);
                        }
                    }

                    break;

                case 2:
                    if(isSelectedDice2){
                        isSelectedDice2 = false;

                        diceButton2.setImageTintList(ColorStateList.valueOf(Color.argb(0, 0, 0, 0)));

                        selectedButtonArray.remove(diceButton2);
                        selectedValuesArray.remove(diceNumberTwo);

                    } else {
                        isSelectedDice2 = true;

                        diceButton2.setImageTintList(ColorStateList.valueOf(Color.argb(
                                alphaValueOfSelection, redValueOfSelection, greenValueOfSelection, blueValueOfSelection)));


                        if(!selectedButtonArray.contains(diceButton2)){
                            selectedButtonArray.add(diceButton2);
                            selectedValuesArray.add(diceNumberTwo);
                        }
                    }

                    break;

                case 3:
                    if(isSelectedDice3){
                        isSelectedDice3 = false;

                        diceButton3.setImageTintList(ColorStateList.valueOf(Color.argb(0, 0, 0, 0)));

                        selectedButtonArray.remove(diceButton3);
                        selectedValuesArray.remove(diceNumberThree);

                    } else {
                        isSelectedDice3 = true;

                        diceButton3.setImageTintList(ColorStateList.valueOf(Color.argb(
                                alphaValueOfSelection, redValueOfSelection, greenValueOfSelection, blueValueOfSelection)));


                        if(!selectedButtonArray.contains(diceButton3)){
                            selectedButtonArray.add(diceButton3);
                            selectedValuesArray.add(diceNumberThree);
                        }
                    }

                    break;

                case 4:
                    if(isSelectedDice4){
                        isSelectedDice4 = false;

                        diceButton4.setImageTintList(ColorStateList.valueOf(Color.argb(0, 0, 0, 0)));

                        selectedButtonArray.remove(diceButton4);
                        selectedValuesArray.remove(diceNumberFour);

                    } else {
                        isSelectedDice4 = true;

                        diceButton4.setImageTintList(ColorStateList.valueOf(Color.argb(
                                alphaValueOfSelection, redValueOfSelection, greenValueOfSelection, blueValueOfSelection)));


                        if(!selectedButtonArray.contains(diceButton4)){
                            selectedButtonArray.add(diceButton4);
                            selectedValuesArray.add(diceNumberFour);
                        }
                    }

                    break;

                case 5:
                    if(isSelectedDice5){
                        isSelectedDice5 = false;

                        diceButton5.setImageTintList(ColorStateList.valueOf(Color.argb(0, 0, 0, 0)));

                        selectedButtonArray.remove(diceButton5);
                        selectedValuesArray.remove(diceNumberFive);

                    } else {
                        isSelectedDice5 = true;

                        diceButton5.setImageTintList(ColorStateList.valueOf(Color.argb(
                                alphaValueOfSelection, redValueOfSelection, greenValueOfSelection, blueValueOfSelection)));


                        if(!selectedButtonArray.contains(diceButton5)){
                            selectedButtonArray.add(diceButton5);
                            selectedValuesArray.add(diceNumberFive);
                        }
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
            //play roll sfx
            rollsSFXPlayer.start();
        } else {
            Log.d("test", "roll sound while muted");
        }

        //random number
        //change imageview to image in array at the position
        if(!isSelectedDice1){
            diceNumberOne = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberOne - 1);
            diceButton1.setImageResource(imageInt);
            //Log.d("test", "dice 1 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice2){
            diceNumberTwo = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberTwo - 1);
            diceButton2.setImageResource(imageInt);
            //Log.d("test", "dice 2 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice3){
            diceNumberThree = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberThree - 1);
            diceButton3.setImageResource(imageInt);
            //Log.d("test", "dice 3 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice4){
            diceNumberFour = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberFour - 1);
            diceButton4.setImageResource(imageInt);
            //Log.d("test", "dice 4 selected. imageInt = " + imageInt);
        }
        if(!isSelectedDice5){
            diceNumberFive = new Random().nextInt(diceArrayImage.size()) + 1;
            int imageInt = diceArrayImage.get(diceNumberFive - 1);
            diceButton5.setImageResource(imageInt);
            //Log.d("test", "dice 5 selected. imageInt = " + imageInt);
        }

        if(!isMuted){
            //delay yahtzee sound after roll sound
            float secondToDelay = 0.6f;
            if(  diceNumberOne == diceNumberTwo &&
                 diceNumberOne == diceNumberThree &&
                 diceNumberOne == diceNumberFour &&
                 diceNumberOne == diceNumberFive){

                //delay .6 sec for roll sound to finish and then play yahtzee sfx
                new Handler(Looper.getMainLooper()).postDelayed(()
                        -> yahtzeeSFXPlayer.start(),
                        600); //Millisecond 1000 = 1 sec

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
        finish();
    }

    private void quitToTitle(){
        //go to title
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
        finish();
    }


}