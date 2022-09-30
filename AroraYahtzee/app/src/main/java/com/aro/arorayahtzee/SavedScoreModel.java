package com.aro.arorayahtzee;

//this class is for saving a player's score
public class SavedScoreModel {

    Integer score = 0;

    String playerName = "";

    SavedScoreModel(Integer score, String playerName){

        this.score = score;

        this.playerName = playerName;

    }

}
