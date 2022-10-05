package com.aro.arorayahtzee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class RollScreenActivity extends AppCompatActivity {

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
    ArrayList<Image> diceArrayImage;

    //array of image buttons
    ArrayList<ImageButton> selectedButtonArray;

    //array of selected values
    ArrayList<Integer> selectedValuesArray;

    Integer diceNumberOne = 0;
    Integer diceNumberTwo = 0;
    Integer diceNumberThree = 0;
    Integer diceNumberFour = 0;
    Integer diceNumberFive = 0;

    //number of times user has rolled (max is 3)
    Integer rollNumber = 0;

    Integer score = 0;

    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
    SharedPreferences.Editor editor = pref.edit();


    //todo audioplayer roll sfx
    //todo audioplayer yahtzee sfx
    //todo audioplayer music

    Boolean isMuted = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_main);

        //todo initialize all the UI stuff buttons textview etc



        Log.d("test", "Roll Number: " + rollNumber);


        if(rollNumber == 0){
            diceButton1.setVisibility(View.INVISIBLE);
            diceButton2.setVisibility(View.INVISIBLE);
            diceButton3.setVisibility(View.INVISIBLE);
            diceButton4.setVisibility(View.INVISIBLE);
            diceButton5.setVisibility(View.INVISIBLE);
            scoreCardButton.setVisibility(View.INVISIBLE);
        }

        //todo dice title to ""?


        //todo dice button enabled false

        updateScore();

        //todo initialize roll sound

        //todo initialize yahtzee sound

        isMuted = pref.getBoolean("soundEffectMute", false);

        //todo button on click listeners...
        // remember to pass the button id to the dicebuttonselected method just pass an int to id
        // which the dice buttons call

    }

    private void updateScore(){
        //todo
    }

    private void diceButtonSelected(Integer diceId){
        //todo
    }

    private void rollButtonPressed(){
        //todo
    }

    private void roll(){
        //todo
    }


    //todo this needs to be translated... the purpose of it is to pass info to score screen
    private void prepare(){
        //todo
    }


}