package com.oscarhmg.orderit.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.model.Food;
import com.squareup.picasso.Picasso;

public class DetailFoodActivity extends AppCompatActivity {
    private TextView foodName, foodPrice, foodDescription;
    private ImageView foodImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton purchaseFood;
    private ElegantNumberButton elegantNumberButton;
    private String foodId= "";
    private FirebaseDatabase database;
    private DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.quantityFood);
        //purchaseFood = (FloatingActionButton) findViewById(R.id.btnPurchaseFood);
        foodName = (TextView) findViewById(R.id.foodName);
        foodPrice = (TextView) findViewById(R.id.foodPrice);
        foodDescription = (TextView) findViewById(R.id.foodDescription);
        foodImage = (ImageView) findViewById(R.id.foodImageSelected);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);

        getIntentFoodInfo();
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
                Food food = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(food.getName());
                foodName.setText(food.getName());

                foodPrice.setText(food.getPrice());
                foodDescription.setText(food.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
