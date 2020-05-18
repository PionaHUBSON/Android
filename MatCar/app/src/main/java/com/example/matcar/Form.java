package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    private Button form_button_send;
    private FirebaseFirestore fstore;
    private EditText form_title, form_description, form_phone_number;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Formularz");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //FIREBASE FIRESTORE
        fstore = FirebaseFirestore.getInstance();
        //FIREBASE FIRESTORE
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Form.this);
        //END OF MENU
        form_title = findViewById(R.id.form_title);
        form_description = findViewById(R.id.form_description);
        form_phone_number = findViewById(R.id.form_phone_number);
        form_button_send = findViewById(R.id.form_button_send);
        progressBar = findViewById(R.id.form_progressBar);
        progressBar.setVisibility(View.GONE);
        form_button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD TO DATABASE
                String title = form_title.getText().toString();
                String description = form_description.getText().toString();
                String phone_number = form_phone_number.getText().toString();
                if (title.isEmpty() || description.isEmpty() || phone_number.isEmpty()) {
                    if (title.isEmpty()) form_title.setError("Wpisz tytuł");
                    if (description.isEmpty()) form_description.setError("Wpisz opis");
                    if (phone_number.isEmpty()) form_phone_number.setError("Wpisz numer telefonu");

                } else if (!title.isEmpty() && !description.isEmpty() && !phone_number.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    DocumentReference documentReference = fstore.collection("form").document();
                    Map<String, Object> form_to_database = new HashMap<>();
                    form_to_database.put("title", title);
                    form_to_database.put("description", description);
                    form_to_database.put("phone_number", phone_number);
                    form_to_database.put("status", "Przyjety");
                    documentReference.set(form_to_database).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("USER: ", "onSuccess:user Form is sent");
                            Toast.makeText(Form.this, "Pomyślnie wysłano formularz do warsztatu", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //ADD TO DATABASE
                    Intent intent = new Intent(Form.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
