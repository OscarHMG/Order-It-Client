package com.oscarhmg.orderit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.model.Order;
import com.oscarhmg.orderit.utils.UtilsFormat;
import com.oscarhmg.orderit.viewholder.CartViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;


    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable textDrawable = TextDrawable.builder().buildRound("" + listData.get(position).getQuantity(), Color.RED);

        int totalPrice = UtilsFormat.parseStringToInt(listData.get(position).getPrice()) * UtilsFormat.parseStringToInt(listData.get(position).getQuantity());

        holder.getImageCountItem().setImageDrawable(textDrawable);
        holder.getTxtItemPrice().setText(UtilsFormat.stringToDollarFormat(totalPrice));
        holder.getTxtItemName().setText(listData.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
