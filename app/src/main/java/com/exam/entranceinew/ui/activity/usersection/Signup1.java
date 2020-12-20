package com.exam.entranceinew.ui.activity.usersection;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;


public class Signup1 extends AppCompatActivity {

    EditText otp_edt;
    TextView tv_signup,tv_get_otp;
    String TAG = "sign_up";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]";
    CountryCodePicker ccp;
    EditText phn_edt;
    LinearLayout ll_otp_layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ccp =  findViewById(R.id.ccp);
        phn_edt =  findViewById(R.id.phn_edt);
        tv_get_otp =  findViewById(R.id.tv_get_otp);
        ll_otp_layout =  findViewById(R.id.ll_otp_layout);
        otp_edt =  findViewById(R.id.otp_edt);

        Log.d(TAG, "onCreate: ccp"+ccp.getSelectedCountryCode());


        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();

        mView = new ViewDialog(Signup1.this);

    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_create_account:
                if(globalClass.isNetworkAvailable()) {
                    if (!phn_edt.getText().toString().trim().isEmpty()) {
                         validate_otp(phn_edt.getText().toString(),otp_edt.getText().toString());
                    } else {
                        Toast.makeText(Signup1.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Signup1.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_get_otp:
                if(globalClass.isNetworkAvailable()) {
                    if (!phn_edt.getText().toString().trim().isEmpty()) {
                        request_otp(phn_edt.getText().toString());
                    } else {
                        Toast.makeText(Signup1.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Signup1.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
                break;


        }
    }



    private void request_otp( final String mobile) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.request_otp;
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


                            tv_get_otp.setVisibility(View.GONE);
                            ll_otp_layout.setVisibility(View.VISIBLE);
                            phn_edt.setEnabled(false);
                            mView.hideDialog();
                            Toast.makeText(Signup1.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                        }else {

                            mView.hideDialog();
                            Toast.makeText(Signup1.this, message, Toast.LENGTH_LONG).show();
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

            globalClass.addToRequestQueue(Signup1.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validate_otp(final String mobile,final String otp) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

       mView.showDialog();
        String url = ApplicationConstants.validate_otp;
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
                            String token = data.get("token").getAsString().replaceAll("\"", "");
                            String mobile = data.get("mobile").getAsString().replaceAll("\"", "");
                            String country_code = data.get("country_code").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:request_key>>>> " + mobile);
                            Log.d(TAG, "onResponse:request_token>>> " + country_code);
                            Log.d(TAG, "onResponse:token>>> " + token);

                            //globalClass.setRequest_token(request_token);
                            globalClass.setPhone_number(mobile);
                            shared_preference.savePrefrence();


                            tv_get_otp.setVisibility(View.GONE);
                            ll_otp_layout.setVisibility(View.VISIBLE);
                            phn_edt.setEnabled(true);

                            Toast.makeText(Signup1.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            mView.hideDialog();
                            Intent intent = new Intent(Signup1.this, Signup2.class);
                            intent.putExtra("token",token);
                            startActivity(intent);

                        }else{
                            mView.hideDialog();
                            Toast.makeText(Signup1.this, message, Toast.LENGTH_LONG).show();
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

            globalClass.addToRequestQueue(Signup1.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}