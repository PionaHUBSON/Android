package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class YourCars extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    private FirebaseFirestore cars_for_users;
    private CollectionReference carref;
    private CarAdapter carAdapter;
    private ArrayList<Car_Info> car_infos;
    public String Uid;
    public Button activity_your_cars_add_new;
    private RecyclerView recycler_view_your_cars;
    public LinearLayout ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Twoje samochody");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cars);

        FirebaseUser userfirebase = FirebaseAuth.getInstance().getCurrentUser();
        Uid = userfirebase.getUid().toString();
        cars_for_users = FirebaseFirestore.getInstance();
        carref = cars_for_users.collection("cars");
        setUpRecyclerView2();
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, YourCars.this);
        //END OF MENU

        //ADD NEW CAR BUTTON
        activity_your_cars_add_new = findViewById(R.id.activity_your_cars_add_new);
        activity_your_cars_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(YourCars.this, activity_add_car.class);
                startActivity(i);
            }
        });
        //ADD NEW CAR BUTTON

        //LINEAR NO CARS
        ly = findViewById(R.id.no_cars);
        ly.setVisibility(View.GONE);
        //LINEAR NO CARS

        recycler_view_your_cars = findViewById(R.id.recycler_view_your_cars);
        recycler_view_your_cars.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView2() {
        Query query = carref.orderBy("model", Query.Direction.DESCENDING);
        car_infos = new ArrayList<>();
        cars_for_users.collection("cars").whereEqualTo("user_id", Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                                if (document.exists()) {
                                    recycler_view_your_cars.setVisibility(View.VISIBLE);
                                    car_infos.add(document.toObject(Car_Info.class));
                                }
                            }
                            if (count == 0) {
                                ly.setVisibility(View.VISIBLE);
                                recycler_view_your_cars.setVisibility(View.GONE);
                            }
                            carAdapter = new CarAdapter(car_infos);
                            RecyclerView recyclerView = findViewById(R.id.recycler_view_your_cars);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(YourCars.this));
                            recyclerView.setAdapter(carAdapter);
                        } else {
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
