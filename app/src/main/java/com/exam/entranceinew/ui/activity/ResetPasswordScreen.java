package com.exam.entranceinew.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exam.entranceinew.ApplicationConstants;
import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.ViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordScreen extends AppCompatActivity {
    String TAG = "reset";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    CountryCodePicker ccp;
    EditText mobile_edt,otp_edt,edt_new_password,edt_confirm_password;
    TextView get_otp_tv,submit_otp_tv;
    LinearLayout ll_otp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        initialize_views();
        functions();
        
    }

    private void initialize_views() {
        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();

        mView = new ViewDialog(this);

        ccp =  findViewById(R.id.ccp);
        mobile_edt = findViewById(R.id.mobile_edt);
        otp_edt = findViewById(R.id.otp_edt);
        ll_otp = findViewById(R.id.ll_otp);
        get_otp_tv = findViewById(R.id.get_otp_tv);
        submit_otp_tv = findViewById(R.id.submit_otp_tv);
        edt_new_password = findViewById(R.id.edt_new_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
    }

    private void functions() {

        mobile_edt.setEnabled(false);
        mobile_edt.setText(globalClass.getPhone_number());
        submit_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.isNetworkAvailable()) {
                    if (!mobile_edt.getText().toString().trim().isEmpty()) {
                        if (!otp_edt.getText().toString().trim().isEmpty()) {
                            if (!edt_new_password.getText().toString().trim().isEmpty()) {
                                if (!edt_confirm_password.getText().toString().trim().isEmpty()) {
                                    if(edt_confirm_password.getText().toString().matches(edt_new_password.getText().toString())){
                                        validate_otp(mobile_edt.getText().toString(),otp_edt.getText().toString(),edt_new_password.getText().toString(),edt_confirm_password.getText().toString());
                                    } else {
                                        Toast.makeText(ResetPasswordScreen.this, "Password mismatch.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(ResetPasswordScreen.this, "Please enter confirm password.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ResetPasswordScreen.this, "Please enter new password.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ResetPasswordScreen.this, "Please enter OTP.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ResetPasswordScreen.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ResetPasswordScreen.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    private void validate_otp(final String mobile,final String otp,final String password,final String confirm_password) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.login_otp_validate;
        Log.d(TAG, "validate_otp: url>>> "+url);
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


                         /*   get_otp_tv.setVisibility(View.GONE);
                            ll_otp.setVisibility(View.VISIBLE);*/
                           // mobile_edt.setEnabled(true);

                            Toast.makeText(ResetPasswordScreen.this, "", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            mView.hideDialog();
                            Intent intent = new Intent(ResetPasswordScreen.this, LoginScreen.class);
                            startActivity(intent);
                            finish();


                        }else{
                            mView.hideDialog();
                            Toast.makeText(ResetPasswordScreen.this, message, Toast.LENGTH_LONG).show();
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
                    params.put("password", password);
                    params.put("confirm_password", confirm_password);


                    Log.d(TAG, "getParams: "+params);

                    return params;
                }

            };

            globalClass.addToRequestQueue(ResetPasswordScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
