package com.aro.arorayahtzee;

//the items we will display on the score card. not all will be used on every line
public class ScoreLineModel {

    String title;
    Integer value;
    Boolean isSelected;
    Boolean isCompleted;
    String description;
    Boolean isDerivative;
    Boolean isMultiYahtzeeSelection;

    ScoreLineModel(String title, int value, Boolean isSelected, Boolean isCompleted,
                   String description, Boolean isDerivative, Boolean isMultiYahtzeeSelection){

        this.title = title;
        this.value = value;
        this.isSelected = isSelected;
        this.isCompleted = isCompleted;
        this.description = description;
        this.isDerivative = isDerivative;
        this.isMultiYahtzeeSelection = isMultiYahtzeeSelection;

    }

}
