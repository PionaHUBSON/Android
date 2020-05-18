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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private EditText reg_login, reg_password, reg_name, reg_surname, reg_phone_number;
    private Button reg_button;
    private Button btn_register_back;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Rejestracja");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Register.this);
        //END OF MENU
        reg_login = findViewById(R.id.reg_login);
        reg_password = findViewById(R.id.reg_pass);
        reg_name = findViewById(R.id.reg_name);
        reg_surname = findViewById(R.id.reg_surnanme);
        reg_phone_number = findViewById(R.id.reg_phone);
        reg_button = findViewById(R.id.reg_button);
        progressBar = findViewById(R.id.register_progressBar);
        progressBar.setVisibility(View.GONE);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                regist();
            }
        });

        //BUTTON BACK
        btn_register_back = findViewById(R.id.btn_register_back);
        btn_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left2, R.anim.left_to_right);
                finish();
            }
        });
        //BUTTON BACK
    }

    private void regist() {
        final String email = reg_login.getText().toString();
        final String password = reg_password.getText().toString();
        final String name = reg_name.getText().toString();
        final String surname = reg_surname.getText().toString();
        final String phone = reg_phone_number.getText().toString();
        if (!(email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || phone.isEmpty())) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Good", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(Register.this, "Rejestracja udała się ",
                                        Toast.LENGTH_SHORT).show();
                                //ADD TO DATABASE
                                final String UserId = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("users").document(UserId);
                                Map<String, Object> user_to_database = new HashMap<>();
                                user_to_database.put("id", UserId);
                                user_to_database.put("name", name);
                                user_to_database.put("surname", surname);
                                user_to_database.put("phone_number", phone);
                                documentReference.set(user_to_database).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("USER: ", "onSuccess:user Profile is created" + UserId);
                                    }
                                });
                                //ADD TO DATABASE
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                updateUI(null);
                                Log.w("Bad", "createUserWithEmail:failure", task.getException());
                                if (!email.contains("@") || !email.contains(".")) {
                                    Toast.makeText(Register.this, "Niedozwolona nazwa email",
                                            Toast.LENGTH_SHORT).show();
                                } else if (password.length() < 6) {
                                    Toast.makeText(Register.this, "Hasło powinno zawierać co najmniej 6 znaków",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "Taki email już istnieje",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                    });
        } else {
            progressBar.setVisibility(View.GONE);
            if (email.isEmpty()) reg_login.setError("Wpisz email");
            if (password.isEmpty()) reg_password.setError("Wpisz hasło");
            if (name.isEmpty()) reg_name.setError("Wpisz imię");
            if (surname.isEmpty()) reg_surname.setError("Wpisz nazwisko");
            if (phone.isEmpty()) reg_phone_number.setError("Wpisz numer telefonu");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
