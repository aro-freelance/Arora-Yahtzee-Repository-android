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


    ScoreScreenActivity scoreScreenActivity = new ScoreScreenActivity();


    List<ScoreLineModel> scoreCardArray;
    private LayoutInflater mInflater;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    //constructor
    AdapterScoreCard(Context context, List<ScoreLineModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.scoreCardArray = data;
        this.pref = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        this.editor = pref.edit();


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


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.scorecard_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
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

        //button clicked toggle the selected item, store the index of it,
        // and give the user a visual cue that it was selected
        viewHolder.button.setOnClickListener((View v) -> {



            //if it is NOT a multiyahtzee...
            if(!scoreScreenActivity.isMultiYahtzee){

                //todo BUG upper and lower total is updating incorrectly on scorecard screen.
                // 1. endlessly adds when selecting and  reselecting
                // 2. does not keep previous score and add when displaying
                // ... it is now no longer counting up endlessly
                // however after unselect it is not updating totals (on new selection it is)
                // also would be good to update score at top right as selections are made... or remove it
                // also it is not displaying updated score totals on second+ record
                // (but score on back end is correct) just not displaying on scorecard right

                //score update for unselect
                Boolean isSelectionMade = pref.getBoolean("isSelectionMade", false);
                Integer selectedIndex = pref.getInt("selectedIndexScoreCard", -1);
                if(selectedIndex >= 0 && isSelectionMade){
                    Log.d("test", "Adapter Scorecard: unselect. previous index = "
                            + selectedIndex);

                    if(selectedIndex < 6){
                        Log.d("test", "Adapter Scorecard: PRE upperscore = "
                                + scoreScreenActivity.upperTotal);
                         scoreScreenActivity.upperTotal =
                                scoreScreenActivity.upperTotal -
                                        scoreCardArray.get(selectedIndex).value;

                        Log.d("test", "Adapter Scorecard: UPDATED upperscore = "
                                + scoreScreenActivity.upperTotal);

                    } else {
                        Log.d("test", "Adapter Scorecard: PRE lowerscore = "
                                + scoreScreenActivity.lowerTotal);
                         scoreScreenActivity.lowerTotal =
                                scoreScreenActivity.lowerTotal -
                                        scoreCardArray.get(selectedIndex).value;
                        Log.d("test", "Adapter Scorecard: UPDATED lowerscore = "
                                + scoreScreenActivity.lowerTotal);
                       }
                } else {
                    Log.d("test", "Adapter Scorecard: is not unselect");

                }


                editor.putBoolean("isSelectionMade", true);
                editor.putInt("selectedIndexScoreCard", viewHolder.getAdapterPosition());
                editor.apply();



                //loop through array and set the selected row
                int index = 0;
                for (int i = 0; i < scoreCardArray.size(); i++) {

                    ScoreLineModel score = scoreCardArray.get(i);

                    if(score.isSelected){
                        score.isSelected = false;
                    }
                    //only run this if it wasn't just unselected...
                    // otherwise it will immediately be reselected
                    else if(index == position){
                        score.isSelected = true;
                    }


                    index = index +1;
                }

                //update score based on selection
                if(position < 6){
                    scoreScreenActivity.upperTotal =
                            scoreScreenActivity.upperTotal + scoreCardArray.get(position).value;
                } else {
                    scoreScreenActivity.lowerTotal =
                            scoreScreenActivity.lowerTotal + scoreCardArray.get(position).value;
                }

                if(scoreScreenActivity.upperTotal >= 63){
                    scoreScreenActivity.bonus = 35;
                } else {
                    scoreScreenActivity.bonus = 0;
                }

                scoreScreenActivity.grandTotal = scoreScreenActivity.upperTotal
                        + scoreScreenActivity.lowerTotal + scoreScreenActivity.bonus;


                scoreCardArray.get(6).value = scoreScreenActivity.upperTotal;
                scoreCardArray.get(7).value = scoreScreenActivity.bonus;
                scoreCardArray.get(16).value = scoreScreenActivity.lowerTotal;
                scoreCardArray.get(17).value = scoreScreenActivity.grandTotal;

            }
            //if is IS a multiyahtzee
            //todo TEST multiyahtzee
            else{

                //todo unselect BUG
                //score update for unselect
                Boolean isSelectionMade = pref.getBoolean("isSelectionMade", false);
                Integer selectedIndex = pref.getInt("selectedIndexScoreCard", -1);
                if(selectedIndex >= 0 && isSelectionMade){
                    //if there is a previous selection... remove it from the totals before adding new selection to totals
                    scoreScreenActivity.lowerTotal = scoreScreenActivity.lowerTotal - 100;
                }

                scoreScreenActivity.isSelectionMade = true;
                scoreScreenActivity.selectedIndex = viewHolder.getAdapterPosition();
                //loop through array and set the selected row
                int index = 0;
                for (int i = 0; i < scoreCardArray.size(); i++) {

                    ScoreLineModel score = scoreCardArray.get(i);

                    if(score.isSelected){
                        score.isSelected = false;
                    }
                    //only run this if it wasn't just unselected...
                    // otherwise it will immediately be reselected
                    else if(index == position){
                        score.isSelected = true;
                    }

                    index = index +1;
                }

                //set value of selected row to 0
                scoreCardArray.get(position).value = 0;

                //the multi yahtzee score should go up by 50
                scoreScreenActivity.sNumberOfYahtzees = scoreScreenActivity.sNumberOfYahtzees + 1;
                scoreCardArray.get(15).value = scoreScreenActivity.sNumberOfYahtzees * 100;

                //score totals should be updated
                scoreScreenActivity.lowerTotal = scoreScreenActivity.lowerTotal + 100;
                scoreScreenActivity.grandTotal = scoreScreenActivity.upperTotal
                        + scoreScreenActivity.lowerTotal + scoreScreenActivity.bonus;

                scoreCardArray.get(16).value = scoreScreenActivity.lowerTotal;
                scoreCardArray.get(17).value = scoreScreenActivity.grandTotal;

                scoreScreenActivity.scoreText.setText("Score: " + scoreScreenActivity.grandTotal);
            }


            //update recyclerview todo TEST check and update. this is giving null object?
            this.notifyDataSetChanged();



        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return scoreCardArray.size();
    }


}
