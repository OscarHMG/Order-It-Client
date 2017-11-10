package com.oscarhmg.orderit.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.model.Food;
import com.oscarhmg.orderit.model.Order;
import com.squareup.picasso.Picasso;
import com.oscarhmg.orderit.database.DataBase;

public class DetailFoodActivity extends AppCompatActivity {
    private TextView foodName, foodPrice, foodDescription;
    private ImageView foodImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton purchaseFood;
    private ElegantNumberButton elegantNumberButton;
    private String foodId= "";
    private FirebaseDatabase database;
    private DatabaseReference foods;
    private Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.quantityFood);
        purchaseFood = (FloatingActionButton) findViewById(R.id.btnPurchaseFood);
        foodName = (TextView) findViewById(R.id.foodName);
        foodPrice = (TextView) findViewById(R.id.foodPrice);
        foodDescription = (TextView) findViewById(R.id.foodDescription);
        foodImage = (ImageView) findViewById(R.id.foodImageSelected);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);

        getIntentFoodInfo();

        //Add listener to purchase food and put in the cart
        purchaseFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataBase(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        elegantNumberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(DetailFoodActivity.this, "Agregado su pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentFoodInfo() {
        if(getIntent()!= null){
            foodId = getIntent().getStringExtra("FoodId");
        }

        if(!foodId.isEmpty() && foodId != null){
            getDetailOfFood();
        }
    }

    private void getDetailOfFood() {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                foodName.setText(currentFood.getName());

                foodPrice.setText(currentFood.getPrice());
                foodDescription.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
