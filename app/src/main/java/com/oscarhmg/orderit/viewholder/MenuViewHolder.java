package com.oscarhmg.orderit.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;

/**
 * Created by OscarHMG on 08/11/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView textViewMenuTitle;
    ImageView imageViewCategory;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        textViewMenuTitle = (TextView)itemView.findViewById(R.id.menu_titleName);
        imageViewCategory = (ImageView)itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);

    }

    public TextView getTextViewMenuTitle() {
        return textViewMenuTitle;
    }

    public void setTextViewMenuTitle(TextView textViewMenuTitle) {
        this.textViewMenuTitle = textViewMenuTitle;
    }

    public ImageView getImageViewCategory() {
        return imageViewCategory;
    }

    public void setImageViewCategory(ImageView imageViewCategory) {
        this.imageViewCategory = imageViewCategory;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }
}
