package com.example.tastypizzav1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button CheckOutPizzas;
    ImageView LogButton;
    private TextView name,email;
    SessionManager sessionManager;
    private static final int REQUEST_PERMISSION =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name =(TextView) findViewById(R.id.name);
        email =(TextView) findViewById(R.id.email);
        sessionManager = new SessionManager(this);

        //Go to menu activity
        //Go to menu activity
        //Go to menu activity
        CheckOutPizzas=(Button) findViewById(R.id.CheckOutPizzas);
        CheckOutPizzas.setOnClickListener( new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
                    return;
                }else{
                    Intent GoToMenu= new Intent(v.getContext(),MenuActivity.class);
                    startActivity(GoToMenu);
                    // Write you code here if permission already given.
                }



            }
        });

        //Go to user activity
        LogButton=(ImageView) findViewById(R.id.LogButton);
        LogButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if(sessionManager.isLogin())
                {
                    Intent GoToUser = new Intent(v.getContext(), LogoutActivity.class);
                    startActivity(GoToUser);
                    //finish();
                }
                else {
                    Intent GoToUser = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(GoToUser);
                    //finish();
                }
            }
        });

    }







    @Override
    public void onBackPressed() {

        this.moveTaskToBack(true);


    }
}
