package com.oscarhmg.orderit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscarhmg.orderit.viewholder.MenuViewHolder;
import com.oscarhmg.orderit.R;
import com.oscarhmg.orderit.interfaces.ItemClickListener;
import com.oscarhmg.orderit.model.Category;
import com.oscarhmg.orderit.utils.SessionManager;
import com.squareup.picasso.Picasso;

public class HomeMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    private FirebaseDatabase database;
    private DatabaseReference tableCategory;
    private TextView userNameLogged;

    private RecyclerView recyclerMenu;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.nav_menu));
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        //Init FireBase
        database = FirebaseDatabase.getInstance();
        tableCategory = database.getReference("Category");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderIntent = new Intent(HomeMenuActivity.this, CartActivity.class);
                startActivity(orderIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Info of the user logged
        View header=navigationView.getHeaderView(0);
        userNameLogged = (TextView) header.findViewById(R.id.userLoggedName);

        recyclerMenu = (RecyclerView) findViewById(R.id.recyclerMenu);


        userNameLogged.setText(session.getUserDetails().get(SessionManager.KEY_NAME));
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerMenu.setLayoutManager(layoutManager);

        loadCategoryMenu();
    }

    /**
     * Load category menu from FireBase with recycler view
     */
    private void loadCategoryMenu() {
         adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>
                (Category.class, R.layout.menu_item, MenuViewHolder.class, tableCategory) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.getTextViewMenuTitle().setText(model.getName());
                Log.i("DEBUG",""+model.getName() +" "+model.getImage());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.getImageViewCategory());

                final Category itemClicked = model;

                //Set my custom listener to the CardView
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLonglick) {
                        //Toast.makeText(HomeMenuActivity.this, ""+itemClicked.getName(), Toast.LENGTH_SHORT).show();
                        Intent loadFoodList = new Intent(HomeMenuActivity.this, FoodMenuActivity.class);
                        loadFoodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(loadFoodList);
                    }
                });
            }
        };



        recyclerMenu.setAdapter(adapter);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {
            intent = new Intent(HomeMenuActivity.this, CartActivity.class );
            startActivity(intent);
        } else if (id == R.id.nav_orders) {
            intent = new Intent(HomeMenuActivity.this, OrderStatusActivity.class );
            startActivity(intent);
        } else if (id == R.id.nav_log_out) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void startActivityFromMenu(){

    }
}
