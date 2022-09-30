package com.aro.arorayahtzee;

public class ScoreCardModel {

    Integer ones = 0;
    Integer twos = 0;
    Integer threes = 0;
    Integer fours = 0;
    Integer fives = 0;
    Integer sixes = 0;

    //sum of ones, twos etc..
    Integer upperTotal = 0;

    Integer bonus = 35;
    Boolean isQualifiedForBonus = false;

    Integer threeOfKindValue = 0;
    Boolean isThreeOfKind = false;

    Integer fourOfKindValue = 0;
    Boolean isFourOfKind = false;

    Integer fullHouseValue = 25;
    Boolean isFullHouse = false;

    Integer smStraightValue = 30;
    Boolean isSmallStraight = false;

    Integer lrgStraightValue = 40;
    Boolean isLargeStraight = false;

    Integer yahtzeeValue = 50;
    Boolean isYahtzee = false;

    Integer chanceValue = 0;

    //for tracking multiple yahtzees
    Integer yahtzeeCounter = 0;

    //sum of lower half
    Integer lowerTotal = 0;

    Integer totalScore = 0;

}
