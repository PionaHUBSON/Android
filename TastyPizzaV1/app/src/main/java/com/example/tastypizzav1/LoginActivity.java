package com.example.tastypizzav1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button RegisterButton;
    private Button btn_login;
    private ProgressBar progressbar1;
    private EditText email, password1;
    private static String URL_LOGIN="https://tastypizzaapp.000webhostapp.com/tastypizza/login.php";

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        email = findViewById(R.id.email11);
        password1 = findViewById(R.id.password12);
        progressbar1 = findViewById(R.id.progressBar);
        btn_login = findViewById(R.id.loginbutton);
        progressbar1.setVisibility(View.GONE);
        //Go to Register Activity
        RegisterButton = findViewById(R.id.login2);
        RegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent GoToUser = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(GoToUser);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password1.getText().toString().trim();

                if(!mEmail.isEmpty() || !mPassword.isEmpty() )
                {
                    Login(mEmail,mPassword);
                }
                else
                {
                    email.setError("Please insert email...");
                    password1.setError("Please insert password...");
                }

            }
        });

    }
        private void Login(final String email, final String password) {
            progressbar1.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.VISIBLE);

            //Rejestracja
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if(success.equals("1")) {
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");
                            String surname = jsonObject.getString("surname");
                            String city = jsonObject.getString("city");
                            String street = jsonObject.getString("street");
                            String apartment_number = jsonObject.getString("apartment_number");
                            String postcode = jsonObject.getString("postcode");
                            String phone_number = jsonObject.getString("phone_number");
                            sessionManager.createSession(name,email,surname,city,street,apartment_number,postcode,phone_number);
                            Toast.makeText(LoginActivity.this,"Succes login.\nYour name: "+name+"\nYour email: "+email,Toast.LENGTH_SHORT).show();
                            progressbar1.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("email",email);
                            intent.putExtra("name",name);
                            startActivity(intent);

                            finish();
                        }
                        else if(success.equals("2"))
                        {
                            Toast.makeText(LoginActivity.this, "Restaurant owner online", Toast.LENGTH_SHORT).show();
                            String email = jsonObject.getString("email");
                            String restaurant_id = jsonObject.getString("restaurant_id");
                            sessionManager.createSession(email,restaurant_id);
                            Intent intent = new Intent(LoginActivity.this,MainRestaurantOwner.class);
                            intent.putExtra("restaurant_id",restaurant_id);
                            startActivity(intent);

                        }
                        else if(success.equals("0"))
                        {
                            Toast.makeText(LoginActivity.this, "The email or password are invalid ", Toast.LENGTH_SHORT).show();
                            progressbar1.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressbar1.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this,"Error "+e.toString(),Toast.LENGTH_SHORT).show();


                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressbar1.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this,"Error "+error.toString(),Toast.LENGTH_SHORT).show();


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email",email);
                    params.put("password",password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
}