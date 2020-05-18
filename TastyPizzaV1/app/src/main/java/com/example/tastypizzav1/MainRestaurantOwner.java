package com.example.tastypizzav1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRestaurantOwner extends AppCompatActivity {
    ImageView LoginButton;
    RecyclerView recyclerView3;
    List<TransactionList> productTransactionList;
    private static final String URL_PRODUCTS = "https://tastypizzaapp.000webhostapp.com/tastypizza/transactionlist.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_restaurant_owner);

        LoginButton =  findViewById(R.id.LogButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToUser = new Intent(v.getContext(), LogoutActivity.class);
                startActivity(GoToUser);
            }
        });
        recyclerView3 = findViewById(R.id.recycler_transactions);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        productTransactionList = new ArrayList<>();
        loadTransactionList();

    }

    private void loadTransactionList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productTransactionList.add(new TransactionList(
                                        product.getString("pizza_data"),
                                        product.getString("order_value"),
                                        product.getString("name"),
                                        product.getString("surname"),
                                        product.getString("email"),
                                        product.getString("city"),
                                        product.getString("street"),
                                        product.getString("apartment_number"),
                                        product.getString("postcode"),
                                        product.getString("phone_number"),
                                        product.getString("date")

                                ));

                            }


///////////////////////////////////////////////////

                            final TransactionAdapter adapter = new TransactionAdapter(MainRestaurantOwner.this, productTransactionList);

                            recyclerView3.setAdapter(adapter);



                            //////////////////////////////////////////////////
                            //creating adapter object and setting it to recyclerview




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("city2",usercity);
                final String restaurant_id = getIntent().getExtras().getString("restaurant_id","defaultKey");
                params.put("restaurant_id",restaurant_id);
                return params;
            }
        };


        ;
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        this.moveTaskToBack(true);


    }
}
