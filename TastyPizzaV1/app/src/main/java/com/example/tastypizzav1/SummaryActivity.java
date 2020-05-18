package com.example.tastypizzav1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {
    private static String URL_REGIST5 = "https://tastypizzaapp.000webhostapp.com/tastypizza/transaction.php";
    private EditText name,email,password,confirmpassword;
    private EditText surname,city,street,apartment_number,postcode,phone_number;
    private ProgressBar loading;
    private Button register;
    private TextView finally_order_value;
    private String s1="";
    SessionManager sessionManager;
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration().
            environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    private String amount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        setContentView(R.layout.activity_summary);
        name = findViewById(R.id.name_total_order);
        email = findViewById(R.id.email_total_order);
        loading = findViewById(R.id.progressBar_total_order);
        surname = findViewById(R.id.surname_total_order);
        city = findViewById(R.id.city_total_order);
        street = findViewById(R.id.street_total_order);
        apartment_number = findViewById(R.id.apartment_number_total_order);
        postcode = findViewById(R.id.postcode_total_order);
        phone_number = findViewById(R.id.phone_number_total_order);
        loading.setVisibility(View.GONE);
        register = findViewById(R.id.register_total_order);
        String total_order_value = getIntent().getExtras().getString("total_order_value","defaultKey");
        final  String[] split = total_order_value.split(":");
        final String[] split2 = split[1].split("z");
        split2[0].trim();
        s1=split2[0];
        finally_order_value = findViewById(R.id.textView14);
        finally_order_value.append(total_order_value);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name2 = name.getText().toString().trim();
                final String email2 = email.getText().toString().trim();
                final String surname2= surname.getText().toString().trim();
                final String city2 = city.getText().toString().trim();
                final String street2 = street.getText().toString().trim();
                final String apartment_number2 = apartment_number.getText().toString().trim();
                final String postcode2 = postcode.getText().toString().trim();
                final String phone_number2 = phone_number.getText().toString().trim();
                if (TextUtils.isEmpty(name2) && TextUtils.isEmpty(email2) && TextUtils.isEmpty(surname2) && TextUtils.isEmpty(city2)  && TextUtils.isEmpty(street2) && TextUtils.isEmpty(apartment_number2) && TextUtils.isEmpty(postcode2) && TextUtils.isEmpty(phone_number2) )//If all fields are empty
                {
                    name.setError("Please Enter Name...");
                    email.setError("Please Enter Email....");
                    surname.setError("Please Enter Surname...");
                    city.setError("Please Enter City...");
                    street.setError("Please Enter Street...");
                    apartment_number.setError("Please Enter Apartment Number...");
                    postcode.setError("Please Enter Postcode...");
                    phone_number.setError("Please Enter Phone Number...");

                }
                else if (TextUtils.isEmpty(name2))//if only one field is empty
                {
                    name.setError("Please Enter Name...");
                }
                else if (TextUtils.isEmpty(email2))
                {
                    email.setError("Please Enter Email....");
                }
                else if(TextUtils.isEmpty(surname2))
                {
                    surname.setError("Please Enter Surname...");
                }
                else if(TextUtils.isEmpty(city2))
                {
                    city.setError("Please Enter City...");
                }
                else if(TextUtils.isEmpty(street2))
                {
                    street.setError("Please Enter Street...");
                }
                else if(TextUtils.isEmpty(apartment_number2))
                {
                    apartment_number.setError("Please Enter Apartment Number...");
                }
                else if(TextUtils.isEmpty(postcode2))
                {
                    postcode.setError("Please Enter Postcode...");
                }
                else if(TextUtils.isEmpty(phone_number2))
                {
                    phone_number.setError("Please Enter Phone Number...");
                }
                else if(!email2.contains("@"))
                {
                    email.setError("Email should contains @ character");
                }
                else {
                    processPayment();
                }
            }
        });

        if(sessionManager.isLogin())
        {
            HashMap<String,String> user = sessionManager.getUserDetail();
            String nName = user.get(sessionManager.NAME);
            String nEmail = user.get(sessionManager.EMAIL);
            String nSurname = user.get(sessionManager.SURNAME);
            String nCity = user.get(sessionManager.CITY);
            String nStreet = user.get(sessionManager.STREET);
            String nApartment_number = user.get(sessionManager.APARTMENT_NUMBER);
            String nPostcode = user.get(sessionManager.POSTCODE);
            String nPhonenumber = user.get(sessionManager.PHONE_NUMBER);
            name.setText(nName);
            email.setText(nEmail);
            surname.setText(nSurname);
            city.setText(nCity);
            street.setText(nStreet);
            apartment_number.setText(nApartment_number);
            postcode.setText(nPostcode);
            phone_number.setText(nPhonenumber);
        }
    }

    private void processPayment() {
        String[] temp=s1.split(",");
        s1="";
        s1=temp[0]+","+temp[1];
        String[] temp2=s1.split(",");
        amount = temp2[0]+"."+temp[1];
        double amount2 = Double.valueOf(amount);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(amount2),"PLN","Donate for Pizzeria",
               PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);

        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

}

    @Override
    protected void onDestroy()
    {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PAYPAL_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                PaymentConfirmation confirmation =  data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation!=null)
                {
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        Toast.makeText(this, "Payment was confirmed! Thank you", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class)
                                .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("PaymentAmount",amount));
                        PizzaProductsAdapter.order_value1 = 0.0;
                        Regist(URL_REGIST5);


                    }
                     catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode== Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
                Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Regist(String URL_REG)
    {
        final String total_order_value3 = getIntent().getExtras().getString("total_order_value","defaultKey");
        final String restaurant_id_to_send = getIntent().getExtras().getString("restaurant_id_to_sent","defaultKey");
        final String nName = name.getText().toString().trim();
        final String nEmail = email.getText().toString().trim();
        final String nSurname = surname.getText().toString().trim();
        final String nCity = city.getText().toString().trim();
        final String nStreet = street.getText().toString().trim();
        final String nApartment_number = apartment_number.getText().toString().trim();
        final String nPostcode = postcode.getText().toString().trim();
        final String nPhonenumber = phone_number.getText().toString().trim();
                //Rejestracja
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String pizza_data="";
                        String[] ord_val=total_order_value3.split(":");
                        String ord_val1=ord_val[1].trim();
                        for(int i=0;i<PizzaProductsAdapter.total_order.size();i++)
                        {
                            pizza_data+=PizzaProductsAdapter.total_order.get(i)+" ";
                        }
                        params.put("pizza_data", pizza_data);
                        params.put("order_value", ord_val1);
                        params.put("name", nName);
                        params.put("surname", nSurname);
                        params.put("email", nEmail);
                        params.put("city", nCity);
                        params.put("street", nStreet);
                        params.put("apartment_number", nApartment_number);
                        params.put("postcode", nPostcode);
                        params.put("phone_number", nPhonenumber);
                        params.put("restaurant_id", String.valueOf(restaurant_id_to_send));
                        PizzaProductsAdapter.total_order.clear();
                        return params;

                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }


}
