package com.example.tastypizzav1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {
    private static String URL_REGIST = "https://tastypizzaapp.000webhostapp.com/tastypizza/register.php";
    private EditText name,email,password,confirmpassword;
    private EditText surname,city,street,apartment_number,postcode,phone_number;
    private ProgressBar loading;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        loading = findViewById(R.id.progressBar);
        register = findViewById(R.id.register);
        surname = findViewById(R.id.surname);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        apartment_number = findViewById(R.id.apartment_number);
        postcode = findViewById(R.id.postcode);
        phone_number = findViewById(R.id.phone_number);
        loading.setVisibility(View.GONE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist(URL_REGIST);
            }
        });
    }

    private void Regist(String URL_REG)
    {
        final String name2 = this.name.getText().toString().trim();
        final String email2 = this.email.getText().toString().trim();
        final String password2 = this.password.getText().toString().trim();
        final String confirmpassword2 = this.confirmpassword.getText().toString().trim();
        final String surname= this.surname.getText().toString().trim();
        final String city = this.city.getText().toString().trim();
        final String street = this.street.getText().toString().trim();
        final String apartment_number = this.apartment_number.getText().toString().trim();
        final String postcode = this.postcode.getText().toString().trim();
        final String phone_number = this.phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(name2) && TextUtils.isEmpty(email2) && TextUtils.isEmpty(password2) && TextUtils.isEmpty(confirmpassword2) && TextUtils.isEmpty(surname) && TextUtils.isEmpty(city)  && TextUtils.isEmpty(street) && TextUtils.isEmpty(apartment_number) && TextUtils.isEmpty(postcode) && TextUtils.isEmpty(phone_number) )//If all fields are empty
        {
            name.setError("Please Enter Name...");
            email.setError("Please Enter Email....");
            password.setError("Please Enter Password...");
            confirmpassword.setError("Please Enter Confirm Password...");
            this.surname.setError("Please Enter Surname...");
            this.city.setError("Please Enter City...");
            this.street.setError("Please Enter Street...");
            this.apartment_number.setError("Please Enter Apartment Number...");
            this.postcode.setError("Please Enter Postcode...");
            this.phone_number.setError("Please Enter Phone Number...");

        }
        else if (TextUtils.isEmpty(name2))//if only one field is empty
        {
            name.setError("Please Enter Name...");
        }
        else if (TextUtils.isEmpty(email2))
        {
            email.setError("Please Enter Email....");
        }
        else if (TextUtils.isEmpty(password2))
        {
            password.setError("Please Enter Password...");
        }
        else if(TextUtils.isEmpty(confirmpassword2))
        {
            confirmpassword.setError("Please Enter Confirm password...");
        }
        else if(TextUtils.isEmpty(surname))
        {
            this.surname.setError("Please Enter Surname...");
        }
        else if(TextUtils.isEmpty(city))
        {
            this.city.setError("Please Enter City...");
        }
        else if(TextUtils.isEmpty(street))
        {
            this.street.setError("Please Enter Street...");
        }
        else if(TextUtils.isEmpty(apartment_number))
        {
            this.apartment_number.setError("Please Enter Apartment Number...");
        }
        else if(TextUtils.isEmpty(postcode))
        {
            this.postcode.setError("Please Enter Postcode...");
        }
        else if(TextUtils.isEmpty(phone_number))
        {
            this.phone_number.setError("Please Enter Phone Number...");
        }
        else if(!email2.contains("@"))
        {
            email.setError("Email should contains @ character");
        }
        else {
            if (password2.matches(confirmpassword2))
            {
                loading.setVisibility(View.VISIBLE);
                register.setVisibility(View.VISIBLE);
                //Rejestracja
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1"))
                            {
                                Toast.makeText(RegisterActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                                Intent GoToUser = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(GoToUser);
                                finish();
                            }
                            else if(success.equals("2"))
                            {
                                Toast.makeText(RegisterActivity.this, "Email already exist! ", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterActivity.this, "Register error!" + error.toString(), Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                register.setVisibility(View.VISIBLE);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name2);
                        params.put("email", email2);
                        params.put("password", password2);
                        params.put("surname", surname);
                        params.put("city", city);
                        params.put("street", street);
                        params.put("apartment_number", apartment_number);
                        params.put("postcode", postcode);
                        params.put("phone_number", phone_number);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(RegisterActivity.this, "Passwords are different! Correct it", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
