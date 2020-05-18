package com.example.tastypizzav1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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


public class RestaurantMeals extends AppCompatActivity {
    ImageView log;
    public static TextView order_value;
    ProgressBar progressBar4;
    SessionManager sessionManager;
    List<PizzaProducts> productList2;
    List<PizzaOrderProducts> productList3;
    public int exitcounter=0;
    RadioGroup radioGroup;
    RadioButton rb1,rb2,rb3;
    TextView min_pizza_price_product_list;
    TextView medium_pizza_price_product_list;
    TextView max_pizza_price_product_list;
    static Button button3;
    Button order,button_clear;
    public static FragmentManager fragmentManager;
    private static final String URL_PRODUCTS4 = "https://tastypizzaapp.000webhostapp.com/tastypizza/product_list.php";
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    public static TextView pop_up_total_order_value;
    static Dialog myDialog;
   static public TextView total_order_price;
    static String restaurant_id_to_sent;
    private PizzaOrderAdapter pizzaOrderAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_meals);

        myDialog = new Dialog(this);
        sessionManager = new SessionManager(this);
        pop_up_total_order_value = findViewById(R.id.total_order_price);
        order = findViewById(R.id.button2);
        button_clear = findViewById(R.id.button3);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PizzaProductsAdapter.total_order.clear();
                PizzaProductsAdapter.order_value1=0;
                order_value.setText("0.00 zł");
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);

            }
        });
        min_pizza_price_product_list = findViewById(R.id.min_pizza_price_product_list);
        medium_pizza_price_product_list = findViewById(R.id.medium_pizza_price_product_list);
        max_pizza_price_product_list = findViewById(R.id.max_pizza_price_product_list);
        log = findViewById(R.id.logButton2);
        order_value = findViewById(R.id.order_value);
        radioGroup = findViewById(R.id.radiogroup1);
        rb1 = findViewById(R.id.radioButton8);
        rb2 = findViewById(R.id.radioButton10);
        rb3 = findViewById(R.id.radioButton9);
        button3 = findViewById(R.id.button3);
        progressBar4 = findViewById(R.id.progressBar4);

        log.setOnClickListener(new View.OnClickListener() {
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
        recyclerView2 = findViewById(R.id.recycler_view3);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToUser = new Intent(v.getContext(), RestaurantMeals.class);
                startActivity(GoToUser);
                Toast.makeText(RestaurantMeals.this, "sadadasd", Toast.LENGTH_SHORT).show();
            }
        });

        //initializing the productlist
        productList2 = new ArrayList<>();
        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts2();



    }
    public void ShowPopup(View v) {
        final TextView txtclose;
        Button button_finally_order;
        final String min_price = getIntent().getExtras().getString("min_price","defaultKey");
        final String delivery_cost = getIntent().getExtras().getString("delivery_cost","defaultKey");
        double price = PizzaProductsAdapter.order_value1+Double.valueOf(delivery_cost);
        myDialog.setContentView(R.layout.order_pop_up);
        button_finally_order = myDialog.findViewById(R.id.button_finally_order);
        recyclerView3 = myDialog.findViewById(R.id.rv5);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        productList3 = new ArrayList<>();
        for(int i=0;i<PizzaProductsAdapter.total_order.size();i++)
        {
            String temp=PizzaProductsAdapter.total_order.get(i);
            String[] temp2=temp.split(";");
            productList3.add(new PizzaOrderProducts(temp2[1],temp2[0],temp2[2]));
        }

        pizzaOrderAdapter  = new PizzaOrderAdapter(RestaurantMeals.this,productList3);
        recyclerView3.setAdapter(pizzaOrderAdapter);
        total_order_price = myDialog.findViewById(R.id.total_order_price);
        txtclose =(TextView) myDialog.findViewById(R.id.exit);
        txtclose.setText("X");
        total_order_price.setText("Total order price: "+String.format("%.2f", price)+"zł");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        button_finally_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PizzaProductsAdapter.order_value1<Double.valueOf(min_price))
                {
                    Toast.makeText(RestaurantMeals.this, "The price of order is too low", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent GoToUser = new Intent(v.getContext(), SummaryActivity.class);
                    GoToUser.putExtra("total_order_value", total_order_price.getText());
                    GoToUser.putExtra("restaurant_id_to_sent", restaurant_id_to_sent);
                    //total_order_price.setText("");
                    //PizzaProductsAdapter.order_value1=0;
                    startActivity(GoToUser);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        exitcounter++;
        if(exitcounter==2)
        {
            exitcounter=0;
            PizzaProductsAdapter.order_value1 = 0.0;
            PizzaProductsAdapter.total_order.clear();
            Intent GoToUser = new Intent(RestaurantMeals.this, MenuActivity.class);
            startActivity(GoToUser);
        }
        else
            {
                Toast.makeText(RestaurantMeals.this, "Are you sure? Your order will be cancelled! \nPress again to exit", Toast.LENGTH_SHORT).show();
            }
    }
    private void loadProducts2() {
       final String data = getIntent().getExtras().getString("id","defaultKey");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        //adding the product to product list
                        productList2.add(new PizzaProducts(
                                product.getInt("id"),
                                product.getString("name"),
                                product.getString("ingredients"),
                                product.getString("min_pizza_price"),
                                product.getString("medium_pizza_price"),
                                product.getString("max_pizza_price"),
                                product.getString("restaurant_id")

                        ));
                        restaurant_id_to_sent = product.getString("restaurant_id");
                    }

                    if(array.length()==0)
                    {
                        Toast.makeText(RestaurantMeals.this, "There's no products in that restaurant yet :(", Toast.LENGTH_SHORT).show();

                    }
                    progressBar4.setVisibility(View.GONE);
                    PizzaProductsAdapter adapter2 = new PizzaProductsAdapter(RestaurantMeals.this, productList2);
                    recyclerView2.setAdapter(adapter2);
                } catch (JSONException e) {
                         e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RestaurantMeals.this, "Register error!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("data1", data);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
