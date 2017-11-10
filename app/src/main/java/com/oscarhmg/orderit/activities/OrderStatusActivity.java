package com.oscarhmg.orderit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.model.Request;
import com.oscarhmg.orderit.utils.SessionManager;
import com.oscarhmg.orderit.viewholder.OrderViewModel;

public class OrderStatusActivity extends AppCompatActivity {
    private RecyclerView recyclerOrders;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference requests;
    private FirebaseRecyclerAdapter<Request, OrderViewModel> adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        recyclerOrders = (RecyclerView) findViewById(R.id.recyclerOrderStatus);
        recyclerOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerOrders.setLayoutManager(layoutManager);

        sessionManager = new SessionManager(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Request orders by phone (ID of the user)
        loadOrdersStatus(sessionManager.getUserDetails().get(SessionManager.KEY_NUMBER));

    }

    private void loadOrdersStatus(final String phoneNumber) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewModel>(
                Request.class,
                R.layout.order_status_item,
                OrderViewModel.class,
                requests.orderByChild("phone").equalTo(phoneNumber)) {
            @Override
            protected void populateViewHolder(OrderViewModel viewHolder, Request model, int position) {
                viewHolder.getTxtOrderId().setText(adapter.getRef(position).getKey());
                viewHolder.getTxtOrderAddress().setText(model.getAddress());
                viewHolder.getTxtOrderPhone().setText(model.getPhone());
                viewHolder.getTxtOrderStatus().setText(codeToStringStatus(model.getStatus()));
            }
        };
        recyclerOrders.setAdapter(adapter);
    }

    private String codeToStringStatus(String status) {
        Log.i("DEBUG", status);
        String statusString = null;
        switch (status){
            case "0":
                statusString = getString(R.string.placedStatus);
                break;
            case "1":
                statusString = getString(R.string.shippingStatus);
                break;
            case "2":
                statusString = getString(R.string.shippedStatus);
                break;
            default:
                statusString = "";
                break;
        }
        return statusString;
    }
}
