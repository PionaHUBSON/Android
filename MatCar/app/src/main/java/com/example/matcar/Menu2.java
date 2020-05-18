package com.example.matcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;

public class Menu2 extends Activity {
    public ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    Context context;

    public Menu2() {
        initNavigationDrawer();
    }

    public Menu2(ActionBarDrawerToggle mToggle, NavigationView navigationView, Context context) {
        this.navigationView = navigationView;
        this.mToggle = mToggle;
        this.context = context;
        initNavigationDrawer();
    }

    public void initNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.start:
                        if (!(context instanceof MainActivity)) {
                            Intent i = new Intent(context, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                            break;
                        } else {
                            break;
                        }
                    case R.id.repairs:
                        if (!(context instanceof Repairs)) {
                            Intent i2 = new Intent(context, Repairs.class);
                            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i2);
                            break;
                        } else {
                            break;
                        }

                    case R.id.diagnostic:
                        if (!(context instanceof Diagnostics)) {
                            Intent i2 = new Intent(context, Diagnostics.class);
                            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i2);
                            break;
                        } else {
                            break;
                        }

                    case R.id.form:
                        if (!(context instanceof Form)) {
                            Intent i2 = new Intent(context, Form.class);
                            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i2);
                            break;
                        } else {
                            break;
                        }

                    case R.id.overview:
                        if (!(context instanceof Overview)) {
                            Intent i2 = new Intent(context, Overview.class);
                            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i2);
                            break;
                        } else {
                            break;
                        }


                    case R.id.log_in:
                        if (!(context instanceof Log_Activity)) {
                            Intent i2 = new Intent(context, Log_Activity.class);
                            i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i2);
                            break;
                        } else {
                            break;
                        }
                    default:
                        break;
                }
                return true;
            }
        });
    }


}
