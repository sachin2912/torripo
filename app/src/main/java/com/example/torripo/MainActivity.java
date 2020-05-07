package com.example.torripo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm=getSupportFragmentManager();
    SharedPreferences sharedPreferences;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:

                                fm.beginTransaction().
                                replace(R.id.frame_layout,user_account_view.newInstance(),"user_account_view").
                                addToBackStack("multiple_package").commit();
                                dl.closeDrawers();

                                break;
                    case R.id.vbook:
                                fm.beginTransaction().
                                replace(R.id.frame_layout,bookings_view.newInstance(),"bookings_view").
                                addToBackStack(null).commit();
                                dl.closeDrawers();
                                break;
                    case R.id.vpack:
                                fm.beginTransaction().
                                replace(R.id.frame_layout,multiple_package.newInstance(),"multiple_package").addToBackStack(null).
                                commit();
                                dl.closeDrawers();
                                break;
                    case R.id.aboutus:

                                fm.beginTransaction().replace(R.id.frame_layout,about_us.newInstance(),"about_us").
                                    addToBackStack("multiple_package").commit();
                                dl.closeDrawers();
                                break;
                    case R.id.developers:
                                fm.beginTransaction().replace(R.id.frame_layout,developer.newInstance(),"developer").
                                addToBackStack("multiple_package").commit();
                                dl.closeDrawers();

                                break;
                    case R.id.vlogout:

                        dl.closeDrawers();

                        Logout();

                        break;
                    default:
                        return true;
                }


                return true;

            }
        });
        sharedPreferences = getSharedPreferences("torripo_login",getApplicationContext().MODE_PRIVATE);

        if (sharedPreferences.contains("uname"))
        {
            fm.beginTransaction().add(R.id.frame_layout, multiple_package.newInstance(), "multiple_package").commit();
        }
        else
        {
            fm.beginTransaction().add(R.id.frame_layout,login.newInstance(),"login_user").commit();
        }
    }

    public void hide_menu()
    {
        Menu menu = this.nv.getMenu();
        menu.findItem(R.id.account).setVisible(false);
        menu.findItem(R.id.vbook).setVisible(false);
        menu.findItem(R.id.vpack).setVisible(false);
        menu.findItem(R.id.vlogout).setVisible(false);
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void show_menu()
    {
        Menu menu = this.nv.getMenu();
        menu.findItem(R.id.account).setVisible(true);
        menu.findItem(R.id.vbook).setVisible(true);
        menu.findItem(R.id.vpack).setVisible(true);
        menu.findItem(R.id.vlogout).setVisible(true);
    }

    public void Logout()
    {
        this.sharedPreferences.edit().clear().commit();
        this.fm.beginTransaction().
                replace(R.id.frame_layout,login.newInstance(),"login").
                addToBackStack(null).commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (f instanceof multiple_package || f instanceof login)
        {
            finish();
        }
        else if(f instanceof booking_form || f instanceof bookings_view )
        {
            getSupportFragmentManager().beginTransaction().
             replace(R.id.frame_layout,multiple_package.newInstance(),"multiple_package").
                    addToBackStack(null).commit();
        }
        else if(f instanceof booking_detail_view)
        {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.frame_layout,bookings_view.newInstance(),"bookings_view").
                    addToBackStack(null).commit();
        }
        else if(f instanceof passenger_form)
        {
            Toast.makeText(getApplicationContext(),"Cannot go back , please fill the form as required , you may cancel the booking in view Booking !!!",Toast.LENGTH_LONG).show();
        }
        else
        {
            super.onBackPressed();
        }
    }

}
