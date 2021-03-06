package com.exam.entranceinew.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.ui.activity.usersection.LoginScreen;
import com.exam.entranceinew.ui.fragment.HomeFragment;
import com.exam.entranceinew.ui.fragment.ProfileFragment;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    String TAG = "drawer";
    SmoothBottomBar bottomBar;
    LinearLayout llLeftDrawer;
    boolean doubleBackToExitPressedOnce = false;
    Fragment fragment;
    ImageView ivCloseDrawer;
    TextView tv_logout;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);

        initialize_view();
        function();
    }

    private void initialize_view() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();
        mView = new ViewDialog(this);

        drawer = findViewById(R.id.drawer_layout);
        bottomBar = findViewById(R.id.bottomBar);
        ivCloseDrawer = findViewById(R.id.ivCloseDrawer);
        tv_logout = findViewById(R.id.tvLogout);
    }

    @SuppressLint("RestrictedApi")
    private void function() {



        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Log.d(TAG, "onDrawerClosed: ");
                //getActionBar().setTitle(R.string.app_name);
                InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                invalidateOptionsMenu();
                //show_chat();
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // getActionBar().setTitle("Home");
                Log.d(TAG, "onDrawerOpened: ");
                InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                invalidateOptionsMenu();

            }
        };
        drawer.addDrawerListener(toggle);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.shophop_ic_drawer);
        }

        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        fragment = new HomeFragment();
        loadFragment(fragment);

        ivCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                if (i==0){
                    Log.d(TAG, "onItemSelect: 0");
                    fragment = new HomeFragment();
                    loadFragment(fragment);

                }else if(i==1){

                    Log.d(TAG, "onItemSelect: 1");
                }else if(i==2){
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    Log.d(TAG, "onItemSelect: 2");

                }
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (globalClass.isNetworkAvailable()){

                    shared_preference.clearPrefrence();
                    globalClass.setLogin_status(false);
                    drawer.closeDrawers();
                    Toast.makeText(MainActivity.this,"You are now logged out.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(MainActivity.this,"Check your internet connection.",Toast.LENGTH_LONG).show();

                }
            }
        });

        user_profile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        }

        else {
            //  super.onBackPressed();


            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }



    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        // transaction.addToBackStack("home_frag");
        transaction.commit();
    }

    private void user_profile() {
        // Tag used to cancel the request

        final String tag_string_req = "user_profile";

        mView.showDialog();
        String url = ApplicationConstants.user_profile;
        Log.d(TAG, "user_profile: url >> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("device", "mobile");
            js.put("user_token", globalClass.getUser_token());
            //  js.put("country_code", ccp.getSelectedCountryCode());

            Log.d(TAG, "user_profile: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "user_profile Response: " + jobj);


                            Gson gson = new Gson();

                            try {


                                String result =jobj.getString("result");
                                String message =jobj.getString("message");
                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                    JSONObject data = jobj.getJSONObject("data");
                                    String id = data.getString("id");
                                    String first_name = data.getString("first_name");
                                    String last_name = data.getString("last_name");
                                    String email = data.getString("email");
                                    String country_code = data.getString("country_code");
                                    String mobile = data.getString("mobile");
                                    String student_class_name = data.getString("student_class_name");

                                    Log.d(TAG, "onResponse:request_key>>>> " + mobile);
                                    Log.d(TAG, "onResponse:request_token>>> " + country_code);

                                    //globalClass.setRequest_token(request_token);
                                    globalClass.setPhone_number(mobile);
                                    globalClass.setCountry_code(country_code);
                                    globalClass.setClass_name(student_class_name);
                                    globalClass.setPhone_number(mobile);
                                    globalClass.setEmail(email);
                                    globalClass.setF_name(first_name);
                                    globalClass.setL_name(last_name);
                                    shared_preference.savePrefrence();



                                    mView.hideDialog();
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onSuccess:id "+message);
                                }else {

                                    mView.hideDialog();
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "user_profile Error: " + error.getMessage());
                    Toast.makeText(MainActivity.this,
                            "Connection Error", Toast.LENGTH_LONG).show();
                    //  pd.dismiss();
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();


                    return params;
                }

            };

            // Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(jsonObjReq);
            globalClass.addToRequestQueue(MainActivity.this, jsonObjReq, tag_string_req);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
