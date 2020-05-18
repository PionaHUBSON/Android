package com.example.matcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_add_car extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    public EditText add_car_brand, add_car_model, add_car_year, add_car_vin;
    public Button add_car;
    private Button btn_add_car_back;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dodaj samochód");
        }
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, activity_add_car.this);
        //END OF MENU

        //GET FIELDS
        add_car_brand = findViewById(R.id.activity_add_car_brand);
        add_car_model = findViewById(R.id.activity_add_car_model);
        add_car_year = findViewById(R.id.activity_add_car_year);
        add_car_vin = findViewById(R.id.activity_add_car_vin);
        add_car = findViewById(R.id.activity_add_car_add);
        //GET FIELDS

        //BUTTON BACK
        btn_add_car_back = findViewById(R.id.btn_add_car_back);
        btn_add_car_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left2, R.anim.left_to_right);
                finish();
            }
        });
        //BUTTON BACK
        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void add() {
        final String brand = add_car_brand.getText().toString();
        final String model = add_car_model.getText().toString();
        final String year = add_car_year.getText().toString();
        final String vin = add_car_vin.getText().toString();

        if (!(brand.isEmpty() || model.isEmpty() || year.isEmpty() || vin.isEmpty())) {

            if (year.length() != 4) {
                Toast.makeText(activity_add_car.this, "Rok samochodu powinien składać się z 4 cyfer",
                        Toast.LENGTH_SHORT).show();
            } else if (vin.length() != 17) {
                Toast.makeText(activity_add_car.this, "Numer VIN musi składać się z 17 znaków",
                        Toast.LENGTH_SHORT).show();
            } else {
                //ADD TO DATABASE
                final String UserId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("cars").document();
                Map<String, Object> user_to_database = new HashMap<>();
                user_to_database.put("brand", brand);
                user_to_database.put("model", model);
                user_to_database.put("year", year);
                user_to_database.put("VIN", vin);
                user_to_database.put("user_id", mAuth.getUid().toString());
                user_to_database.put("document_id", documentReference.getId().toString());
                documentReference.set(user_to_database).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("USER: ", "onSuccess:user Profile is created" + UserId);
                    }
                });
                //ADD TO DATABASE
                Intent intent = new Intent(activity_add_car.this, YourCars.class);
                startActivity(intent);
            }
        } else {
            if (brand.isEmpty()) add_car_brand.setError("Wpisz markę");
            if (model.isEmpty()) add_car_model.setError("Wpisz model");
            if (year.isEmpty()) add_car_year.setError("Wpisz rok");
            if (vin.isEmpty()) add_car_vin.setError("Wpisz VIN");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
