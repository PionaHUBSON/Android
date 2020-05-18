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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Edit_User_Activity extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    public EditText activity_user_edit_name, activity_user_edit_surname, activity_user_edit_phone_number, activity_user_edit_password, activity_user_edit_confirm_password;
    public Button activity_user_edit_change_personal_data, activity_user_edit_change_password;
    public FirebaseFirestore fstore;
    public Button btn_edit_user_back;
    public String name, surname, phone_number, password, confirm_password;
    public TextView pass_change;
    public LinearLayout change_password, change_password_edit1, change_password_edit2;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user_);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edytuj swoje dane");
        }
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Edit_User_Activity.this);
        //END OF MENU

        //BACK BUTTON
        btn_edit_user_back = findViewById(R.id.btn_edit_user_back);
        btn_edit_user_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.right_to_left2, R.anim.left_to_right);
                finish();
            }
        });
        //BACK BUTTON
        //FINDVIEW
        activity_user_edit_name = findViewById(R.id.activity_edit_user_name);
        activity_user_edit_surname = findViewById(R.id.activity_edit_user_surname);
        activity_user_edit_phone_number = findViewById(R.id.activity_edit_user_phone_number);
        activity_user_edit_change_personal_data = findViewById(R.id.activity_edit_user_change_personal_data);
        activity_user_edit_password = findViewById(R.id.activity_edit_user_new_password);
        activity_user_edit_confirm_password = findViewById(R.id.activity_edit_user_confirm_password);
        activity_user_edit_change_password = findViewById(R.id.activity_edit_user_change_password);
        pass_change = findViewById(R.id.activity_edit_user_zmiana_hasla);

        change_password = findViewById(R.id.linear_change_password);
        change_password_edit1 = findViewById(R.id.linear_change_password_edit1);
        change_password_edit2 = findViewById(R.id.linear_change_password_edit2);
        for (UserInfo profile : user.getProviderData()) {
            if (profile.getProviderId().toString().equals("google.com")) {
                activity_user_edit_password.setVisibility(View.GONE);
                activity_user_edit_confirm_password.setVisibility(View.GONE);
                activity_user_edit_change_password.setVisibility(View.GONE);
                pass_change.setVisibility(View.GONE);
                change_password.setVisibility(View.GONE);
                change_password_edit1.setVisibility(View.GONE);
                change_password_edit2.setVisibility(View.GONE);
            }
        }

        //FINDVIEW
        //GET DATA
        if (user != null) {
            String email = user.getEmail();
            final String uid = user.getUid();
            fstore = FirebaseFirestore.getInstance();
            fstore.collection("users").whereEqualTo("id", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    activity_user_edit_name.setText(document.get("name").toString());
                                    activity_user_edit_surname.setText(document.get("surname").toString());
                                    if (document.get("phone_number").toString().isEmpty()) {
                                        activity_user_edit_phone_number.setText("");
                                    } else {
                                        activity_user_edit_phone_number.setText(document.get("phone_number").toString());
                                    }
                                }
                            } else {
                            }
                        }
                    });
            //GET DATA
            activity_user_edit_change_personal_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = activity_user_edit_name.getText().toString();
                    surname = activity_user_edit_surname.getText().toString();
                    phone_number = activity_user_edit_phone_number.getText().toString();
                    DocumentReference update_user_data = fstore.collection("users").document(uid);
                    if (name.isEmpty()) {
                        activity_user_edit_name.setError("Wpisz imię");
                    }
                    if (surname.isEmpty()) {
                        activity_user_edit_surname.setError("Wpisz nazwisko");
                    }
                    if (phone_number.isEmpty()) {
                        activity_user_edit_phone_number.setError("Wprowadź numer telefonu");
                    } else if (phone_number.length() < 9) {
                        activity_user_edit_phone_number.setError("Numer jest zbyt któtki");
                    }
                    if (!name.isEmpty() && !surname.isEmpty() && !phone_number.isEmpty() && phone_number.length() >= 9) {
                        update_user_data.update("name", activity_user_edit_name.getText().toString());
                        update_user_data.update("surname", activity_user_edit_surname.getText().toString());
                        update_user_data.update("phone_number", activity_user_edit_phone_number.getText().toString());
                        Toast.makeText(Edit_User_Activity.this, "Pomyślnie zaktualizowano dane", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Edit_User_Activity.this, User_Activity.class);
                        startActivity(i);
                    }
                }
            });

            //BUTTON PASSWORD
            activity_user_edit_change_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    password = activity_user_edit_password.getText().toString();
                    confirm_password = activity_user_edit_confirm_password.getText().toString();
                    if (password.isEmpty()) {
                        activity_user_edit_password.setError("Wprowadź nowe hasło");
                    }
                    if (confirm_password.isEmpty()) {
                        activity_user_edit_confirm_password.setError("Potwierdź hasło");
                    }
                    if (!password.equals(confirm_password)) {
                        Toast.makeText(Edit_User_Activity.this, "Hasła nie zgadzają się", Toast.LENGTH_SHORT).show();
                    }
                    if (!password.isEmpty() && !confirm_password.isEmpty() && password.equals(confirm_password)) {
                        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                        String newPassword = password;
                        user2.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Edit_User_Activity.this, "Pomyślnie zaktualizowano hasło", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(Edit_User_Activity.this, User_Activity.class);
                                            startActivity(i);
                                        }
                                    }
                                });
                    }
                }
            });
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
