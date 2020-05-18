package com.example.tastypizzav1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class LogoutActivity extends AppCompatActivity {
    private TextView name,email;
    private Button btn_logout;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        name = findViewById(R.id.name33);
        email = findViewById(R.id.textView10);
        btn_logout = findViewById(R.id.btn_log);

        HashMap<String,String> user = sessionManager.getUserDetail();
        String nName = user.get(sessionManager.NAME);
        String nEmail = user.get(sessionManager.EMAIL);

        name.setText(nName);
        email.setText(nEmail);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Intent GoToUser = new Intent(v.getContext(), MainActivity.class);
                startActivity(GoToUser);
                finish();
            }
        });
    }
}
