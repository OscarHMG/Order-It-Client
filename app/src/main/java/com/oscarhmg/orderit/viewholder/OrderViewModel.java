package com.oscarhmg.orderit.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;

/**
 * Created by OscarHMG on 09/11/2017.
 */

public class OrderViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtOrderId, txtOrderStatus, txtOrderAddress, txtOrderPhone;
    private ItemClickListener itemClickListener;

    public OrderViewModel(View itemView) {
        super(itemView);
        txtOrderId = (TextView) itemView.findViewById(R.id.orderId);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.orderAddress);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.orderPhone);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TextView getTxtOrderId() {
        return txtOrderId;
    }

    public void setTxtOrderId(TextView txtOrderId) {
        this.txtOrderId = txtOrderId;
    }

    public TextView getTxtOrderStatus() {
        return txtOrderStatus;
    }

    public void setTxtOrderStatus(TextView txtOrderStatus) {
        this.txtOrderStatus = txtOrderStatus;
    }

    public TextView getTxtOrderAddress() {
        return txtOrderAddress;
    }

    public void setTxtOrderAddress(TextView txtOrderAddress) {
        this.txtOrderAddress = txtOrderAddress;
    }

    public TextView getTxtOrderPhone() {
        return txtOrderPhone;
    }

    public void setTxtOrderPhone(TextView txtOrderPhone) {
        this.txtOrderPhone = txtOrderPhone;
    }


}
