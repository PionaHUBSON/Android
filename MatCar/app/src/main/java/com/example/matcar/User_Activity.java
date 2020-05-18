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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class User_Activity extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    public TextView user_name_title, user_name, user_surname, user_email, user_phone_number;
    public Button user_activity_button_edit;
    public FirebaseFirestore fstore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Twoje konto");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_);

        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, User_Activity.this);
        //END OF MENU

        //TEXTVIEWS AND BUTTON
        user_name_title = findViewById(R.id.user_activity_title_name);
        user_name = findViewById(R.id.user_activity_name);
        user_surname = findViewById(R.id.user_activity_surname);
        user_email = findViewById(R.id.user_activity_email);
        user_phone_number = findViewById(R.id.user_activity_phone_number);
        user_activity_button_edit = findViewById(R.id.user_activity_button_edit);
        //TEXTVIEWS AND BUTTON
        String uid;
        //GET DATA
        if (user != null) {
            String email = user.getEmail();
            uid = user.getUid();
            user_email.setText("Email: " + email);
            fstore = FirebaseFirestore.getInstance();
            fstore.collection("users").whereEqualTo("id", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String phon = document.get("phone_number").toString();
                                    user_name.setText("Imię: " + document.get("name").toString());
                                    user_name_title.setText(document.get("name").toString() + " !");
                                    user_surname.setText("Nazwisko: " + document.get("surname").toString());
                                    if (phon.isEmpty()) {
                                        user_phone_number.setText("Telefon: Brak informacji");
                                    } else {
                                        user_phone_number.setText("Telefon: " + document.get("phone_number").toString());
                                    }
                                }
                            } else {
                            }
                        }
                    });
        }
        //GET DATA
        user_activity_button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Activity.this, Edit_User_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (user != null) {
            Intent intent = new Intent(User_Activity.this, MainActivity.class);
            startActivity(intent);
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
