package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.matcar.Repair_Data_Adapter.services;

public class Repairs extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    private HorizontalScrollView horizontalScrollView;
    private Button repairs_suspension, repairs_engine, repairs_engine_equipment, repairs_braking_system, repairs_steering_system, repairs_filters, repairs_electricity,
            repairs_body;
    public static TextView total_price;
    private Button repair_confirm_services;
    private FirebaseFirestore repairs_choice;
    private RecyclerView recycler_view_basket;
    private Basket_Adapter basket_adapter;
    private FirebaseFirestore get_cars_for_users;
    private RecyclerView recycler_view_repairs;
    private ArrayList<ArrayList<Repair_Data>> repair_data;
    private Repair_Data_Adapter repair_adapter;
    private Button repair_order;
    private Dialog total_order;
    public static TextView no_orders_info;
    private int car_count = 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Naprawy");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairs);
        repairs_choice = FirebaseFirestore.getInstance();

        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, Repairs.this);
        //END OF MENU

        //GET CARS FOR USER REF
        get_cars_for_users = FirebaseFirestore.getInstance();
        //GET CARS FOR USER REF

        //RECYCLER VIEW
        repair_data = new ArrayList<ArrayList<Repair_Data>>();
        recycler_view_repairs = findViewById(R.id.recycler_view_repairs);
        recycler_view_repairs.setVisibility(View.GONE);
        setup_repair_choice_2("suspension");
        setup_repair_choice_2("engine");
        setup_repair_choice_2("engine_equipment");
        setup_repair_choice_2("braking_system");
        setup_repair_choice_2("steering_system");
        setup_repair_choice_2("filters");
        setup_repair_choice_2("electricity");
        setup_repair_choice_2("body");
        //RECYCLER VIEW
        //BUTTONS OF SCROLL VIEW
        repairs_suspension = findViewById(R.id.repairs_suspension);
        repairs_engine = findViewById(R.id.repairs_engine);
        repairs_engine_equipment = findViewById(R.id.repairs_engine_equipment);
        repairs_braking_system = findViewById(R.id.repairs_braking_system);
        repairs_steering_system = findViewById(R.id.repairs_steering_system);
        repairs_filters = findViewById(R.id.repairs_filters);
        repairs_electricity = findViewById(R.id.repairs_electricity);
        repairs_body = findViewById(R.id.repairs_body);
        //BUTTONS OF SCROLL VIEW
        horizontalScrollView = findViewById(R.id.repair_horizontal_scroll_view);


        //BUTTONS ON CLICK
        repairs_suspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(0), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_engine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(1), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_engine_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(2), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_braking_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(3), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_steering_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(4), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(5), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(6), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });
        repairs_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_adapter = new Repair_Data_Adapter(repair_data.get(7), getApplicationContext());
                recycler_view_repairs.setAdapter(repair_adapter);
            }
        });

        //REPAIR ORDER
        repair_order = findViewById(R.id.repair_order);
        repair_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_order = new Dialog(v.getContext());
                Window window = total_order.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP;
                wlp.y = 400;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                total_order.setContentView(R.layout.custompopup_total_order);
                total_order.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_background));
                TextView close = total_order.findViewById(R.id.close_repair_total_order);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        total_order.dismiss();
                    }
                });
                total_price = total_order.findViewById(R.id.total_price_of_order);
                total_order.show();
                recycler_view_basket = total_order.findViewById(R.id.repairs_total_order_recycler_view);
                recycler_view_basket.setHasFixedSize(true);
                recycler_view_basket.setLayoutManager(new LinearLayoutManager(Repairs.this));
                basket_adapter = new Basket_Adapter(services);
                recycler_view_basket.setAdapter(basket_adapter);
                //COFIRM BUTTON

                repair_confirm_services = total_order.findViewById(R.id.repair_confirm_services);
                //CHECK IF USER HAS CARS
                if (user != null) {
                    get_cars_for_users.collection("cars").whereEqualTo("user_id", user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override

                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            if (document.exists()) {
                                                car_count++;
                                            }
                                        }
                                    }
                                }
                            });
                }
                //CHECK IF USER HAS CARS
                repair_confirm_services.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (services.size() == 0) {
                            Toast.makeText(Repairs.this, "Nie wybrano żadnej naprawy!", Toast.LENGTH_SHORT).show();
                        } else if (user == null) {
                            Toast.makeText(Repairs.this, "Musisz być zalogowany, aby kontynuować", Toast.LENGTH_SHORT).show();
                        } else if (car_count == 0) {
                            Toast.makeText(Repairs.this, "Nie dodałeś żadnego samochodu w aplikacji. Dodaj samochód do konta, aby kontynuować", Toast.LENGTH_LONG).show();
                        } else {
                            String[] split_price = total_price.getText().toString().trim().split(":");
                            String final_order = "";
                            for (int i = 0; i < services.size(); i++) {
                                String[] split_services = services.get(i).trim().split(";");
                                if (split_services.length == 2) {
                                    if (i > 0) {
                                        final_order += ", " + split_services[0];
                                    } else {
                                        final_order += split_services[0];
                                    }
                                } else {
                                    if (i > 0) {
                                        final_order += ", " + split_services[0];
                                        final_order += " (" + split_services[2] + ")";
                                    } else {
                                        final_order += split_services[0];
                                        final_order += " (" + split_services[2] + ")";
                                    }

                                }
                            }
                            Intent intent = new Intent(getBaseContext(), Repairs_Final.class);
                            intent.putExtra("final_order", final_order);
                            intent.putExtra("total_price", split_price[1]);
                            startActivity(intent);
                        }
                    }
                });
                //CONFRIM BUTTON
                //NO ORDERS TEXTVIEW
                no_orders_info = total_order.findViewById(R.id.no_orders_info);
                //NO ORDERS TEXTVIEW
                if (services.size() == 0) {
                    no_orders_info.setVisibility(View.VISIBLE);
                } else {
                    no_orders_info.setVisibility(View.GONE);
                }
            }
        });
        //REPAIR ORDER
    }

    private void setup_repair_choice_2(final String doc) {
        final ArrayList<Repair_Data> rep_dat = new ArrayList<>();
        repairs_choice.collection("repairs").document(doc).collection(doc)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                recycler_view_repairs.setVisibility(View.VISIBLE);
                                rep_dat.add(document.toObject(Repair_Data.class));
                            }
                            if (doc.equals("suspension")) {
                                repair_adapter = new Repair_Data_Adapter(repair_data.get(0), getApplicationContext());
                                recycler_view_repairs.setHasFixedSize(true);
                                recycler_view_repairs.setLayoutManager(new LinearLayoutManager(Repairs.this));
                                recycler_view_repairs.setAdapter(repair_adapter);
                            }
                        } else {
                        }
                    }
                });
        repair_data.add(rep_dat);
        rep_dat.clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
