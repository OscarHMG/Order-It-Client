package com.oscarhmg.orderit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;
import com.oscarhmg.orderit.model.Food;
import com.oscarhmg.orderit.viewholder.FoodViewHolder;
import com.squareup.picasso.Picasso;

public class FoodMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerFood;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference foodList;
    private String categoryId;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        //FireBase Instances
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerFood = (RecyclerView) findViewById(R.id.recyclerFood);
        recyclerFood.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerFood.setLayoutManager(layoutManager);


        //Capture intent with data (CategoryId)
        getIntentCategoryId();

        //loadFoodByCategory();
    }


    private void getIntentCategoryId(){
        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");

        //Load Food
        if(!categoryId.isEmpty() && categoryId !=null){
            loadFoodByCategory();
        }

    }

    private void loadFoodByCategory() {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId) ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.getTextViewFoodTitle().setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.getImageViewFood());

                final Food itemClicked = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLonglick) {
                        //Toast.makeText(FoodMenuActivity.this, ""+itemClicked.getName(), Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(FoodMenuActivity.this, DetailFoodActivity.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };
        recyclerFood.setAdapter(adapter);
    }
}
