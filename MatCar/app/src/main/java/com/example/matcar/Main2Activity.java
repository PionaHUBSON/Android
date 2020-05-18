package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Main2Activity extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    public LinearLayout linearRepairs;
    public LinearLayout linearDiagnostic;
    public LinearLayout linearForm;
    public LinearLayout linearOverview;
    public TextView tv_rep, tv_diag, tv_form, tv_over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Main2Activity.this);
        //END OF MENU

        linearRepairs = findViewById(R.id.repairs_main);
        linearRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Repairs.class);
                startActivity(intent);
            }
        });

        linearDiagnostic = findViewById(R.id.diagnostic_main);
        linearDiagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Diagnostics.class);
                startActivity(intent);
            }
        });

        linearForm = findViewById(R.id.form_main);
        linearForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Form.class);
                startActivity(intent);
            }
        });

        linearOverview = findViewById(R.id.overview_main);
        linearOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Overview.class);
                startActivity(intent);
            }
        });

        tv_rep = findViewById(R.id.main2_rep);
        tv_diag = findViewById(R.id.main2_diag);
        tv_form = findViewById(R.id.main2_form);
        tv_over = findViewById(R.id.main2_over);
        String s1 = "Naprawy \nWybierz kategorię naprawy, a następnie kliknij w intersującą Cię część, którą chesz aby została ona wymieniona przez mechanika";
        String s2 = "Diagnostyka \n Umów się ze swoim mechanikiem na diagnostykę komputerową twojego samochodu";
        String s3 = "Formularz Zgłoszeniowy \n Nie wiesz co się dzieje z samochodem? Opisz problem w formularzu";
        String s4 = "Ogólny Przegląd \n Umów się na wizytę, a mechanik sprawdzi ogólny stan techniczny twojego samochodu";

        SpannableString ss1 = new SpannableString(s1);
        SpannableString ss2 = new SpannableString(s2);
        SpannableString ss3 = new SpannableString(s3);
        SpannableString ss4 = new SpannableString(s4);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new RelativeSizeSpan(1.2f), 0, 7, 0); // set size

        ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new RelativeSizeSpan(1.2f), 0, 11, 0); // set size

        ss3.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new RelativeSizeSpan(1.2f), 0, 9, 0); // set size

        ss4.setSpan(new StyleSpan(Typeface.BOLD), 0, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss4.setSpan(new RelativeSizeSpan(1.2f), 0, 15, 0); // set size

        tv_rep.setText(ss1);
        tv_diag.setText(ss2);
        tv_form.setText(ss3);
        tv_over.setText(ss4);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
