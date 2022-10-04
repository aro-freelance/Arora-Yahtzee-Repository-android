package com.aro.arorayahtzee;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterScoreCard extends RecyclerView.Adapter<AdapterScoreCard.ViewHolder>{


    ArrayList<ScoreLineModel> scoreCardArray = new ArrayList<ScoreLineModel>();


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

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
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

        //todo translate cell.closure

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return scoreCardArray.size();
    }


}
