package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Log_Activity extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    public Button login_btn_register;
    public EditText log_email, log_password;
    public Button log_to_the_app;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private int RC_SIGN_IN = 1;
    public static GoogleApiClient mGoogleApiClient;

    //GOOGLE_BUTTON
    private Button signInButton;
    public static GoogleSignInClient googleSignInClient;

    //GOOGLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Zaloguj się");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i = new Intent(Log_Activity.this, User_Activity.class);
            startActivity(i);
        }
        mAuth = FirebaseAuth.getInstance();
        //GOOGLE
        signInButton = findViewById(R.id.log_google_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        if (googleSignInClient != null) googleSignInClient.signOut();
        //GOOGLE
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Log_Activity.this);
        //END OF MENU
        login_btn_register = findViewById(R.id.login_btn_register);
        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Log_Activity.this, Register.class);
                startActivity(i);
            }
        });
        log_email = findViewById(R.id.log_email);
        log_password = findViewById(R.id.log_password);
        log_to_the_app = findViewById(R.id.log_log_to_the_app);
        progressBar = findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.GONE);
        log_to_the_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = log_email.getText().toString();
                String password = log_password.getText().toString();
                Login(email, password);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                try {
                    GoogleSignInAccount acc = task.getResult(ApiException.class);
                    Toast.makeText(Log_Activity.this, "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show();
                    FirebaseGoogleAuth(acc);
                } catch (ApiException e) {

                    Toast.makeText(Log_Activity.this, "Błąd logowania", Toast.LENGTH_SHORT).show();
                    FirebaseGoogleAuth(null);
                }
            }

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            Toast.makeText(Log_Activity.this, "Błąd logowania", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Intent i = new Intent(Log_Activity.this, MainActivity.class);
                    startActivity(i);
                    //ADD TO DATABASE
                    final String UserId = user.getUid();
                    final FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                    String user_id = fstore.collection("users").document(UserId).toString();
                    DocumentReference docRef = fstore.collection("users").document(UserId);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                } else {
                                    DocumentReference documentReference = fstore.collection("users").document(UserId);
                                    Map<String, Object> user_to_database = new HashMap<>();
                                    String[] s1 = user.getDisplayName().split(" ");
                                    String phon;
                                    if (user.getPhoneNumber() == null) {
                                        phon = "Brak informacji";
                                    } else {
                                        phon = user.getPhoneNumber().toString();
                                    }
                                    user_to_database.put("id", UserId);
                                    user_to_database.put("name", s1[0]);
                                    user_to_database.put("surname", s1[s1.length - 1]);
                                    user_to_database.put("phone_number", phon);
                                    documentReference.set(user_to_database).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("USER: ", "onSuccess:user Profile is created" + UserId);
                                        }
                                    });

                                }
                            } else {
                            }
                        }
                    });
                    //ADD TO DATABASE
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(Log_Activity.this, "Porażka", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void Login(String email, String password) {
        if (!(email.isEmpty() || password.isEmpty() || (email.isEmpty() && password.isEmpty()))) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Good", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(Log_Activity.this, "Logowanie poprawne",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Log_Activity.this, MainActivity.class);
                                startActivity(i);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Log.w("Bad", "signInWithEmail:failure", task.getException());
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Log_Activity.this, "Nieprawidłowy email lub hasło",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            if (email.isEmpty()) {
                log_email.setError("Wpisz email");
            }
            if (password.isEmpty()) {
                log_password.setError("Wpisz hasło");
            }
        }
    }

    private void updateUI(FirebaseUser user) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
