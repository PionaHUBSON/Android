package com.example.tastypizzav1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MenuActivity extends AppCompatActivity {
    ImageView LogButton;
    SessionManager sessionManager;
    private EditText editTextSearch;
    private TextView tv3;
    private ProgressBar progressbar3;
    private static final String URL_PRODUCTS = "https://tastypizzaapp.000webhostapp.com/tastypizza/api.php";
    private LocationManager locationManager;
    private static String usercity;
    Location gps_loc,network_loc,final_loc;
    double lattitude=0.0;
    double longitude=0.0;
    private static String list;



    //a list to store all the products
    List<Restaurant> productList;
    //the recyclerview
    RecyclerView recyclerView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1000:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    String locationProvider = LocationManager.NETWORK_PROVIDER;
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(locationProvider);
                    try
                    {
                        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
                        String city = hereLocation(location.getLatitude(),location.getLongitude());
                        usercity=city.trim();
                        //Toast.makeText(this,usercity,Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(this,"Not found",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sessionManager = new SessionManager(this);
        tv3 = findViewById(R.id.textView3);
        gps_loc=null;
        network_loc=null;
        final_loc=null;


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!=
             PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1000);
            }
            else
            {
                String locationProvider = LocationManager.NETWORK_PROVIDER;
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(locationProvider);
                try
                {
                    Geocoder geocoder = new Geocoder(this,Locale.getDefault());
                    String city = hereLocation(location.getLatitude(),location.getLongitude());
                    usercity=city.trim();
                    //Toast.makeText(this,city,Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,"Not found",Toast.LENGTH_SHORT).show();
                }

            }








        //Go to user activity

        progressbar3 = findViewById(R.id.progressBar2);
        progressbar3.setVisibility(View.VISIBLE);
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
                   // finish();
                }
            }
        });


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
         recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToUser = new Intent(v.getContext(), RestaurantMeals.class);
                startActivity(GoToUser);

            }
        });

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();


    }

    private String hereLocation(double latitude, double longitude) {
        String cityname="";
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        List<Address> addresses;
        try
        {
           addresses=geocoder.getFromLocation(latitude,longitude,10);
           if(addresses.size()>0)
           {
               for(Address adr: addresses)
               {
                   if(adr.getLocality() != null && adr.getLocality().length() >0 ){
                       cityname = adr.getLocality();
                       break;
                   }
               }
           }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return cityname;
    }

    private void onLocationChanged(Location location) {
       // longitude =location.getLongitude();
       // lattitude = location.getLatitude();
       // Toast.makeText(this, (int) longitude,Toast.LENGTH_SHORT).show();
    }



    public ArrayList<Restaurant> filteredList2 = new ArrayList<>();
    private void filter(String text){


        for(Restaurant item: productList)
        {
            if(item.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList2.add(item);
            }
        }
        //
    }

    @Override
    public void onBackPressed() {
        Intent GoToUser = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(GoToUser);
    }
    private void loadProducts() {
        /*Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        Toast.makeText(MenuActivity.this, city, Toast.LENGTH_SHORT).show();
*/
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
                                productList.add(new Restaurant(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getString("city"),
                                        product.getString("street"),
                                        product.getString("apartment_number"),
                                        product.getString("postcode"),
                                        product.getString("min_price"),
                                        product.getString("delivery_cost"),
                                        product.getString("logo_img"),
                                        product.getString("phone_number")

                                ));

                            }


///////////////////////////////////////////////////
                            editTextSearch = findViewById(R.id.editTextSearch);
                            final RestaurantsAdapter adapter = new RestaurantsAdapter(MenuActivity.this, productList);

                            recyclerView.setAdapter(adapter);
                            progressbar3.setVisibility(View.GONE);
                            editTextSearch.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    final  ArrayList<Restaurant> filteredList = new ArrayList<>();
                                    for(Restaurant item: productList)
                                    {
                                        if(item.getName().toLowerCase().contains(s.toString().toLowerCase()))
                                        {
                                            filteredList.add(item);
                                        }
                                    }
                                    //filter(s.toString());
                                     adapter.filterList(filteredList);
                                    list=filteredList.toString();


                                }

                            });

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
                params.put("city2",usercity);

                return params;
            }
        };


                ;
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}
