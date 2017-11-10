package com.oscarhmg.orderit.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.adapter.CartAdapter;
import com.oscarhmg.orderit.database.DataBase;
import com.oscarhmg.orderit.model.Order;
import com.oscarhmg.orderit.model.Request;
import com.oscarhmg.orderit.utils.SessionManager;
import com.oscarhmg.orderit.utils.UtilsEditText;
import com.oscarhmg.orderit.utils.UtilsFormat;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerCart;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalPrice;
    private FButton placeOrder;
    private FirebaseDatabase database;
    private DatabaseReference requests;
    private List<Order> cart = new ArrayList<>();
    private CartAdapter adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sessionManager = new SessionManager(getApplicationContext());
        recyclerCart = (RecyclerView) findViewById(R.id.recyclerCart);
        totalPrice = (TextView) findViewById(R.id.total);
        placeOrder = (FButton) findViewById(R.id.btnPlaceOrder);

        layoutManager = new LinearLayoutManager(this);
        recyclerCart.setHasFixedSize(true);
        recyclerCart.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        loadOrders();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPlaceOrder();
            }
        });
    }

    private void loadOrders() {
        cart = new DataBase(this).getCarts(); //Obtain cart orders
        adapter = new CartAdapter(cart, this);
        recyclerCart.setAdapter(adapter);

        getTotalPrice();

    }

    public void getTotalPrice(){
        int total = 0;
        for(Order order: cart)
            total = UtilsFormat.parseStringToInt(order.getPrice()) * UtilsFormat.parseStringToInt(order.getQuantity());

        totalPrice.setText(UtilsFormat.stringToDollarFormat(total));
    }



    public  void showDialogPlaceOrder(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.title_placeOrder));
        alertDialog.setMessage(getString(R.string.message_placeOrder));

        final EditText editTextAddres = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        editTextAddres.setLayoutParams(layoutParams);
        alertDialog.setView(editTextAddres);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        //Add Button
        alertDialog.setPositiveButton(getString(R.string.positve_orderPlace), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!UtilsEditText.isEmpty(editTextAddres)){
                    Request request = new Request(
                            sessionManager.getUserDetails().get(SessionManager.KEY_NUMBER),
                            sessionManager.getUserDetails().get(SessionManager.KEY_NAME),
                            editTextAddres.getText().toString(),
                            totalPrice.getText().toString(),
                            cart);

                    //Save in FireBase the new request of the orders
                    requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                    //Saved in Firebasem, now we nedd to delete order in local DB.
                    new DataBase(getBaseContext()).cleanCart();
                    Toast.makeText(CartActivity.this, getString(R.string.orderCompletedSend), Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(CartActivity.this, getString(R.string.error_empty_addres), Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.setNegativeButton(getString(R.string.placeOrderCancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }
}
