package com.aro.arorayahtzee;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterScoreCard extends RecyclerView.Adapter<AdapterScoreCard.ViewHolder>{

    public static final String MyPREFERENCES = "myprefs";


    List<ScoreLineModel> scoreCardArray;
    private LayoutInflater mInflater;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int upperTotalPrevious;
    int bonusPrevious;
    int lowerTotalPrevious;
    int grandTotalPrevious;
    int numberOfYahtzeesPrevious;

    int upperTotal;
    int bonus;
    int lowerTotal;
    int grandTotal;
    int numberOfYahtzees;

    boolean isFirstLoad = true;
    boolean isUnSelect = false;
    boolean isThereASelection = false;
    boolean isMultiYahtzee = false;


    //constructor
    AdapterScoreCard(Context context, List<ScoreLineModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.scoreCardArray = data;
        this.pref = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        this.editor = pref.edit();

        Log.d("test", "Adapter Scorecard constructor: Array size - "
                + scoreCardArray.size());

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView valueText;
        private final Button button;
        private final LinearLayout layout;

        public ViewHolder(View view) {
            super(view);

            titleText = view.findViewById(R.id.title_scorecarditem);
            valueText = view.findViewById(R.id.valuetext_scorecarditem);
            button = view.findViewById(R.id.selectbutton_scorecarditem);
            layout = view.findViewById(R.id.linearlayout_scorecarditem);
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.scorecard_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.titleText.setText(scoreCardArray.get(position).title);
        viewHolder.valueText.setText(scoreCardArray.get(position).value.toString());

        if(scoreCardArray.get(position).isSelected){
            viewHolder.button.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        } else{
            viewHolder.button.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }

        if(scoreCardArray.get(position).isCompleted){
            viewHolder.button.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.button.setVisibility(View.VISIBLE);
        }

        if(scoreCardArray.get(position).isDerivative){
            viewHolder.button.setVisibility(View.INVISIBLE);
        }
        if(isFirstLoad){
            //get stored scores
            upperTotalPrevious = scoreCardArray.get(6).value;
            bonusPrevious = scoreCardArray.get(7).value;
            lowerTotalPrevious = scoreCardArray.get(16).value;
            grandTotalPrevious = scoreCardArray.get(17).value;
            isMultiYahtzee = pref.getBoolean("isMultiYahtzee", false);
            numberOfYahtzeesPrevious = scoreCardArray.get(15).value;
            isFirstLoad = false;
        }


        //button clicked toggle the selected item, store the index of it,
        // and give the user a visual cue that it was selected
        viewHolder.button.setOnClickListener((View v) -> {


            //if it is NOT a multiyahtzee...
            if(!isMultiYahtzee){

                editor.putBoolean("isSelectionMade", true);
                editor.putInt("selectedIndexScoreCard", position);
                editor.apply();


                //HANDLE SELECT
                //loop through array and set the selected row
                int index = 0;
                for (int i = 0; i < scoreCardArray.size(); i++) {

                    ScoreLineModel score = scoreCardArray.get(i);

                    //unselect
                    if(score.isSelected){
                        score.isSelected = false;
                        //something was unselected
                        isUnSelect = true;

                    }
                    //select
                    else if(index == position){
                        score.isSelected = true;
                        //something was selected
                        isThereASelection = true;
                    }

                    index = index +1;
                }

                updateScoreBySelection(position);


            }
            //if is IS a multiyahtzee
            //todo TEST multiyahtzee
            else{

                //loop through array and set the selected row
                int index = 0;
                for (int i = 0; i < scoreCardArray.size(); i++) {

                    ScoreLineModel score = scoreCardArray.get(i);

                    //unselect
                    if(score.isSelected){
                        score.isSelected = false;
                        //something was unselected
                        isUnSelect = true;

                    }
                    //select
                    else if(index == position){
                        score.isSelected = true;
                        //something was selected
                        isThereASelection = true;
                    }

                    index = index +1;
                }

                updateScoreMultiYahtzee(position);

            }

            scoreCardArray.get(6).value = upperTotal;
            scoreCardArray.get(7).value = bonus;
            scoreCardArray.get(15).value = numberOfYahtzees * 100;
            scoreCardArray.get(16).value = lowerTotal;
            scoreCardArray.get(17).value = grandTotal;

            // todo TEST check and update. this is giving null object?
            //update recyclerview
            this.notifyDataSetChanged();


        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return scoreCardArray.size();
    }

    private void updateScoreBySelection(int position){

        //if there is a new selection, don't run the unselect block
        if(isThereASelection){
            isUnSelect = false;
            isThereASelection = false;
        }

        //update score based on selection
        if(isUnSelect){
            //reset to previous values
            upperTotal = upperTotalPrevious;
            lowerTotal = lowerTotalPrevious;
            //turn off the switch
            isUnSelect = false;
        }
        else if(position < 6){
            upperTotal = upperTotalPrevious + scoreCardArray.get(position).value;
            lowerTotal = lowerTotalPrevious;
        } else {
            upperTotal = upperTotalPrevious;
            lowerTotal = lowerTotalPrevious + scoreCardArray.get(position).value;
        }

        if(upperTotal >= 63){
            bonus = 35;
        } else {
            bonus = 0;
        }

        grandTotal = upperTotal + lowerTotal + bonus;

    }

    private void updateScoreMultiYahtzee(int position){

        //if there is a new selection, don't run the unselect block
        if(isThereASelection){
            isUnSelect = false;
            isThereASelection = false;
        }

        //update score based on selection
        if(isUnSelect){
            numberOfYahtzees = numberOfYahtzeesPrevious;
            lowerTotal = lowerTotalPrevious - 100;
        } else {
            //set value of selected row to 0
            scoreCardArray.get(position).value = 0;
            numberOfYahtzees = numberOfYahtzeesPrevious + 1;
            lowerTotal = lowerTotalPrevious + 100;
        }

        grandTotal = upperTotalPrevious + lowerTotal + bonusPrevious;

    }


}
