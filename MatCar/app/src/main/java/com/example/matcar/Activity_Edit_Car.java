package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Activity_Edit_Car extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public EditText edit_car_brand, edit_car_model, edit_car_year, edit_car_vin;
    public Button edit_car, btn_edit_car_back;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private String brand, model, year, vin;
    private String document_id;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit__car);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edytuj dane samochodu");
        }
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        document_id = getIntent().getStringExtra("document_id");
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Activity_Edit_Car.this);
        //END OF MENU

        //GET FIELDS
        edit_car_brand = findViewById(R.id.activity_edit_car_brand);
        edit_car_model = findViewById(R.id.activity_edit_car_model);
        edit_car_year = findViewById(R.id.activity_edit_car_year);
        edit_car_vin = findViewById(R.id.activity_edit_car_vin);
        edit_car = findViewById(R.id.activity_edit_car_edit);
        //GET FIELDS

        //BUTTON BACK
        btn_edit_car_back = findViewById(R.id.btn_edit_car_back);
        btn_edit_car_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left2, R.anim.left_to_right);
                finish();
            }
        });
        //BUTTON BACK

        //SET ACTUAL DATA TO FIELDS
        //GET DATA
        if (user != null) {
            final String uid = user.getUid();
            fstore.collection("cars").whereEqualTo("document_id", document_id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    edit_car_brand.setText(document.get("brand").toString());
                                    edit_car_model.setText(document.get("model").toString());
                                    edit_car_year.setText(document.get("year").toString());
                                    edit_car_vin.setText(document.get("VIN").toString());
                                }
                            } else {
                            }
                        }
                    });
        }
        //GET DATA
        //SET ACTUAL DATA TO FIELDS

        //ONCLICK
        edit_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_car();
            }
        });
        //ONCLICK
    }

    private void edit_car() {
        brand = edit_car_brand.getText().toString();
        model = edit_car_model.getText().toString();
        year = edit_car_year.getText().toString();
        vin = edit_car_vin.getText().toString();
        DocumentReference update_car_data = fstore.collection("cars").document(document_id);
        if (brand.isEmpty()) {
            edit_car_brand.setError("Wpisz markę");
        }
        if (model.isEmpty()) {
            edit_car_model.setError("Wpisz model");
        }
        if (year.isEmpty()) {
            edit_car_year.setError("Wpisz rok produkcji");
        }
        if (vin.isEmpty()) {
            edit_car_vin.setError("Wpisz numer VIN");
        }
        if (vin.length() != 17) {
            Toast.makeText(this, "Numer VIN musi składać się z 17 znaków", Toast.LENGTH_SHORT).show();
        } else if (year.length() != 4) {
            Toast.makeText(this, "Rok samochodu powinien składać się z 4 cyfer", Toast.LENGTH_SHORT).show();
        } else {
            update_car_data.update("brand", brand);
            update_car_data.update("model", model);
            update_car_data.update("year", year);
            update_car_data.update("VIN", vin);
            Toast.makeText(Activity_Edit_Car.this, "Pomyślnie zaktualizowano dane", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Activity_Edit_Car.this, YourCars.class);
            startActivity(i);
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
