package com.oscarhmg.orderit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;
import com.oscarhmg.orderit.model.Food;
import com.oscarhmg.orderit.viewholder.FoodViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerFood;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference foodList;
    private String categoryId;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    //Search Bar
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    private List<String> suggestedFood = new ArrayList<>();
    private MaterialSearchBar materialSearchBar;

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

        //Search Bar
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchFoodBar);
        materialSearchBar.setHint(getString(R.string.searchFoodBarHint));
        loadSSuggestedFood();

    }


    /**
     * Load suggested food to search bar and show suggestions in search app bar)
     */
    private void loadSSuggestedFood() {

        foodList.orderByChild("MenuId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot iterator: dataSnapshot.getChildren()){
                    Food itemFood = iterator.getValue(Food.class);
                    suggestedFood.add(itemFood.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Add list of suggestions
        materialSearchBar.setLastSuggestions(suggestedFood);
        materialSearchBar.setCardViewElevation(10);
        setListenersSearchBar();
    }

    /**
     * Function to set listener to the search bar when the user write in the search bar
     */
    private void setListenersSearchBar() {
        //Add listener when user is typing in the search bar
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();

                for(String search : suggestedFood){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);

                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Action when user search or not in the search bar

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerFood.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())) {
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
                        foodDetail.putExtra("FoodId", searchAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });

            }
        };

        recyclerFood.setAdapter(searchAdapter);
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
