package com.oscarhmg.orderit.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;

/**
 * Created by OscarHMG on 08/11/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView textViewFoodTitle;
    private ImageView imageViewFood;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);

        textViewFoodTitle = (TextView) itemView.findViewById(R.id.food_titleName);
        imageViewFood = (ImageView) itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TextView getTextViewFoodTitle() {
        return textViewFoodTitle;
    }

    public void setTextViewFoodTitle(TextView textViewFoodTitle) {
        this.textViewFoodTitle = textViewFoodTitle;
    }

    public ImageView getImageViewFood() {
        return imageViewFood;
    }

    public void setImageViewFood(ImageView imageViewFood) {
        this.imageViewFood = imageViewFood;
    }
}
