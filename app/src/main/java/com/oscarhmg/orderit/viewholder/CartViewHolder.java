package com.oscarhmg.orderit.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;
import com.oscarhmg.orderit.model.Order;
import com.oscarhmg.orderit.utils.UtilsFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView txtItemName, txtItemPrice;
    private ImageView imageCountItem;
    private ItemClickListener itemClickListener;




    public CartViewHolder(View itemView) {
        super(itemView);
        txtItemName = (TextView) itemView.findViewById(R.id.cartItemName);
        txtItemPrice = (TextView) itemView.findViewById(R.id.cartItemPrice);
        imageCountItem = (ImageView) itemView.findViewById(R.id.cartItemCount);

    }

    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TextView getTxtItemName() {
        return txtItemName;
    }

    public void setTxtItemName(TextView txtItemName) {
        this.txtItemName = txtItemName;
    }

    public TextView getTxtItemPrice() {
        return txtItemPrice;
    }

    public void setTxtItemPrice(TextView txtItemPrice) {
        this.txtItemPrice = txtItemPrice;
    }

    public ImageView getImageCountItem() {
        return imageCountItem;
    }

    public void setImageCountItem(ImageView imageCountItem) {
        this.imageCountItem = imageCountItem;
    }
}




