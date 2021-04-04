package com.exam.entranceinew.ui.activity.usersection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.ui.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;


public class LoginScreen extends AppCompatActivity {
    TextView submit_tv_password, signup_tv, tv_forgot_password;
    String TAG = "login";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    
    ViewDialog mView;
    EditText email_edt,password_edt,mobile_edt,otp_edt;
    TextView tv_otp,tv_password,get_otp_tv,submit_otp_tv,regenerate_otp_tv;
    EditText input_layout_otp;
    CountryCodePicker ccp;
    LinearLayout ll_gt_otp,ll_password,ll_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();

        mView = new ViewDialog(this);

        ccp =  findViewById(R.id.ccp);
        signup_tv = findViewById(R.id.signup_tv);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        ll_gt_otp = findViewById(R.id.ll_gt_otp);
        ll_password = findViewById(R.id.ll_password);
        mobile_edt = findViewById(R.id.mobile_edt);
        otp_edt = findViewById(R.id.otp_edt);
        tv_otp = findViewById(R.id.tv_otp);
        tv_password = findViewById(R.id.tv_password);
        get_otp_tv = findViewById(R.id.get_otp_tv);
        ll_otp = findViewById(R.id.ll_otp);
        submit_otp_tv = findViewById(R.id.submit_otp_tv);
        submit_tv_password = findViewById(R.id.submit_tv_password);
        regenerate_otp_tv = findViewById(R.id.regenerate_otp_tv);


      /*  if(globalClass.getLogin_status()){

            Intent intent = new Intent(LoginScreen.this, Dashboard.class);
            startActivity(intent);
            finish();
        }*/


        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(LoginScreen.this, Signup1.class);
                startActivity(intent);
            }
        });


        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, ForgotPasswordScreen.class);
                startActivity(intent);
            }
        });


        
        get_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.isNetworkAvailable()) {
                    if (!mobile_edt.getText().toString().trim().isEmpty()) {
                        request_otp(mobile_edt.getText().toString());
                    } else {
                        Toast.makeText(LoginScreen.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginScreen.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });

        regenerate_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.isNetworkAvailable()) {
                    if (!mobile_edt.getText().toString().trim().isEmpty()) {
                       // regenerate_otp(mobile_edt.getText().toString());
                    } else {
                        Toast.makeText(LoginScreen.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginScreen.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });

        submit_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.isNetworkAvailable()) {
                    if (!mobile_edt.getText().toString().trim().isEmpty()) {
                        if (!otp_edt.getText().toString().trim().isEmpty()) {
                            validate_otp(mobile_edt.getText().toString(),otp_edt.getText().toString());
                        } else {
                            Toast.makeText(LoginScreen.this, "Please enter OTP.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginScreen.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginScreen.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });
        submit_tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(globalClass.isNetworkAvailable()) {
                    if (!mobile_edt.getText().toString().trim().isEmpty()) {
                        if (!password_edt.getText().toString().trim().isEmpty()) {
                            login_using_password(mobile_edt.getText().toString(),password_edt.getText().toString());
                        } else {
                            Toast.makeText(LoginScreen.this, "Please enter password.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginScreen.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginScreen.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_otp:
                ll_gt_otp.setVisibility(View.VISIBLE);
                ll_password.setVisibility(View.GONE);
                tv_forgot_password.setVisibility(View.GONE);
                tv_otp.setBackgroundColor(getResources().getColor(R.color.blue_gradient));
                tv_otp.setTextColor(getResources().getColor(R.color.white));

                tv_password.setBackgroundColor(getResources().getColor(R.color.white));
                tv_password.setTextColor(getResources().getColor(R.color.blue_gradient));
                break;

            case R.id.tv_password:
                ll_gt_otp.setVisibility(View.GONE);
                ll_password.setVisibility(View.VISIBLE);
                tv_forgot_password.setVisibility(View.VISIBLE);

                tv_password.setBackgroundColor(getResources().getColor(R.color.blue_gradient));
                tv_password.setTextColor(getResources().getColor(R.color.white));

                tv_otp.setBackgroundColor(getResources().getColor(R.color.white));
                tv_otp.setTextColor(getResources().getColor(R.color.blue_gradient));
                break;



        }
    }
    
    private void request_otp( final String mobile) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.login_otp_request;
        try{
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>(){


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "registration Response: " + response);


                    Gson gson = new Gson();

                    try {

                        JsonObject jobj = gson.fromJson(response, JsonObject.class);
                        String result = jobj.get("result").getAsString().replaceAll("\"", "");
                        String message = jobj.get("message").getAsString().replaceAll("\"", "");


                        Log.d(TAG, "Message: "+message);

                        //if(status.equals("1")) {
                        if(result.equals("true")) {
                            ///  showOptDialog(mobile);
                            JsonObject data = jobj.getAsJsonObject("data");
                            String mobile = data.get("mobile").getAsString().replaceAll("\"", "");
                            String country_code = data.get("country_code").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:request_key>>>> " + mobile);
                            Log.d(TAG, "onResponse:request_token>>> " + country_code);

                            //globalClass.setRequest_token(request_token);
                            globalClass.setPhone_number(mobile);
                            globalClass.setCountry_code(country_code);
                            shared_preference.savePrefrence();


                            get_otp_tv.setVisibility(View.GONE);
                            ll_otp.setVisibility(View.VISIBLE);
                            mobile_edt.setEnabled(false);
                            mView.hideDialog();
                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                        }else {
                            mView.hideDialog();
                            get_otp_tv.setVisibility(View.GONE);
                            regenerate_otp_tv.setVisibility(View.VISIBLE);
                           // Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Connection Error", Toast.LENGTH_LONG).show();
                    //  pd.dismiss();
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();

                    params.put("request_key", globalClass.getRequest_key());
                    params.put("request_token", globalClass.getRequest_token());
                    params.put("device", "mobile");
                    params.put("mobile", mobile);
                    params.put("country_code", ccp.getSelectedCountryCode());



                    return params;
                }

            };

            globalClass.addToRequestQueue(LoginScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void regenerate_otp( final String mobile) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.login_otp_request;
        try{
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>(){


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "registration Response: " + response);


                    Gson gson = new Gson();

                    try {

                        JsonObject jobj = gson.fromJson(response, JsonObject.class);
                        String result = jobj.get("result").getAsString().replaceAll("\"", "");
                        String message = jobj.get("message").getAsString().replaceAll("\"", "");


                        Log.d(TAG, "Message: "+message);

                        //if(status.equals("1")) {
                        if(result.equals("true")) {
                            ///  showOptDialog(mobile);
                            JsonObject data = jobj.getAsJsonObject("data");
                            String mobile = data.get("mobile").getAsString().replaceAll("\"", "");
                            String country_code = data.get("country_code").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:request_key>>>> " + mobile);
                            Log.d(TAG, "onResponse:request_token>>> " + country_code);

                            //globalClass.setRequest_token(request_token);
                            globalClass.setPhone_number(mobile);
                            shared_preference.savePrefrence();


                            get_otp_tv.setVisibility(View.GONE);
                            ll_otp.setVisibility(View.VISIBLE);
                            mobile_edt.setEnabled(false);
                            mView.hideDialog();
                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                        }else {
                            mView.hideDialog();
                            get_otp_tv.setVisibility(View.GONE);
                            regenerate_otp_tv.setVisibility(View.VISIBLE);
                            // Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Connection Error", Toast.LENGTH_LONG).show();
                    //  pd.dismiss();
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();

                    params.put("request_key", globalClass.getRequest_key());
                    params.put("request_token", globalClass.getRequest_token());
                    params.put("device", "mobile");
                    params.put("mobile", mobile);
                    params.put("country_code", ccp.getSelectedCountryCode());



                    return params;
                }

            };

            globalClass.addToRequestQueue(LoginScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validate_otp(final String mobile,final String otp) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.login_otp_validate;
        try{
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>(){


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "registration Response: " + response);


                    Gson gson = new Gson();

                    try {


                        JsonObject jobj = gson.fromJson(response, JsonObject.class);
                        String result = jobj.get("result").getAsString().replaceAll("\"", "");
                        String message = jobj.get("message").getAsString().replaceAll("\"", "");


                        Log.d(TAG, "Message: "+message);

                        //if(status.equals("1")) {
                        if(result.equals("true")) {
                            ///  showOptDialog(mobile);
                            get_otp_tv.setVisibility(View.GONE);
                            ll_otp.setVisibility(View.VISIBLE);
                            mobile_edt.setEnabled(true);

                            JsonObject data = jobj.getAsJsonObject("data");
                            String token = data.get("token").getAsString().replaceAll("\"", "");
                            String mobile = data.get("mobile").getAsString().replaceAll("\"", "");
                            String country_code = data.get("country_code").getAsString().replaceAll("\"", "");
                            String email = data.get("email").getAsString().replaceAll("\"", "");
                            String first_name = data.get("first_name").getAsString().replaceAll("\"", "");
                            String last_name = data.get("last_name").getAsString().replaceAll("\"", "");
                            String student_class_name = data.get("student_class_name").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:email>>>> " + email);
                            Log.d(TAG, "onResponse:last_name>>> " + last_name);
                            Log.d(TAG, "onResponse:first_name>>> " + first_name);
                            Log.d(TAG, "onResponse:token>>> " + token);


                            globalClass.setPhone_number(mobile);
                            globalClass.setEmail(email);
                            globalClass.setF_name(first_name);
                            globalClass.setL_name(last_name);
                            globalClass.setUser_token(token);
                            globalClass.setClass_name(student_class_name);
                            globalClass.setLogin_status(true);
                            shared_preference.savePrefrence();





                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            mView.hideDialog();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);

                        }else{
                            mView.hideDialog();
                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Connection Error", Toast.LENGTH_LONG).show();
                    //  pd.dismiss();
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();

                    params.put("request_key", globalClass.getRequest_key());
                    params.put("request_token", globalClass.getRequest_token());
                    params.put("device", "mobile");
                    params.put("mobile", mobile);
                    params.put("country_code", ccp.getSelectedCountryCode());
                    params.put("otp", otp);



                    return params;
                }

            };

            globalClass.addToRequestQueue(LoginScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void login_using_password(final String mobile,final String password) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.login_using_password;
        Log.d(TAG, "login_using_password:url>>>  "+url);
        try{
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>(){


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "registration Response: " + response);


                    Gson gson = new Gson();

                    try {


                        JsonObject jobj = gson.fromJson(response, JsonObject.class);
                        String result = jobj.get("result").getAsString().replaceAll("\"", "");
                        String message = jobj.get("message").getAsString().replaceAll("\"", "");


                        Log.d(TAG, "Message: "+message);


                        if(result.equals("true")) {
                            JsonObject data = jobj.getAsJsonObject("data");
                            String token = data.get("token").getAsString().replaceAll("\"", "");
                            String mobile = data.get("mobile").getAsString().replaceAll("\"", "");
                            String country_code = data.get("country_code").getAsString().replaceAll("\"", "");
                            String email = data.get("email").getAsString().replaceAll("\"", "");
                            String first_name = data.get("first_name").getAsString().replaceAll("\"", "");
                            String last_name = data.get("last_name").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:email>>>> " + email);
                            Log.d(TAG, "onResponse:last_name>>> " + last_name);
                            Log.d(TAG, "onResponse:first_name>>> " + first_name);
                            Log.d(TAG, "onResponse:token>>> " + token);


                            globalClass.setPhone_number(mobile);
                            globalClass.setEmail(email);
                            globalClass.setF_name(first_name);
                            globalClass.setL_name(last_name);
                            globalClass.setLogin_status(true);
                            shared_preference.savePrefrence();




                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            mView.hideDialog();
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);

                        }else{
                            mView.hideDialog();
                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Connection Error", Toast.LENGTH_LONG).show();
                    //  pd.dismiss();
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();

                    params.put("request_key", globalClass.getRequest_key());
                    params.put("request_token", globalClass.getRequest_token());
                    params.put("device", "mobile");
                    params.put("mobile", mobile);
                    params.put("country_code", ccp.getSelectedCountryCode());
                    params.put("password", password);


                    Log.d(TAG, "getParams: "+params);

                    return params;
                }

            };

            globalClass.addToRequestQueue(LoginScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}