package com.example.matcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mToggle;
    private FirebaseFirestore history_for_users;
    private CollectionReference hisref;
    private User_History_Adapter history_adapter;
    private ArrayList<User_History> history_infos;
    public String Uid;
    private RecyclerView recycler_view_user_history;
    private LinearLayout no_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Historia usług");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FirebaseUser userfirebase = FirebaseAuth.getInstance().getCurrentUser();
        Uid = userfirebase.getUid().toString();
        history_for_users = FirebaseFirestore.getInstance();
        hisref = history_for_users.collection("history");
        //MENU
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = new Menu(mToggle, navigationView, History.this);
        //END OF MENU

        recycler_view_user_history = findViewById(R.id.recycler_view_user_history);
        recycler_view_user_history.setVisibility(View.GONE);

        no_history = findViewById(R.id.no_history);
        no_history.setVisibility(View.GONE);
        setUpRecyclerView3();
    }

    private void setUpRecyclerView3() {
        history_infos = new ArrayList<>();
        history_for_users.collection("history").whereEqualTo("client_id", Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count++;
                                if (document.exists()) {
                                    recycler_view_user_history.setVisibility(View.VISIBLE);
                                    history_infos.add(document.toObject(User_History.class));
                                }
                            }
                            if (count == 0) {
                                no_history.setVisibility(View.VISIBLE);
                            }
                            history_adapter = new User_History_Adapter(history_infos);
                            RecyclerView recyclerView = findViewById(R.id.recycler_view_user_history);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
                            recyclerView.setAdapter(history_adapter);
                        } else {
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
