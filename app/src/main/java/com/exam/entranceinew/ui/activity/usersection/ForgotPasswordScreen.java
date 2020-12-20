package com.exam.entranceinew.ui.activity.usersection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class ForgotPasswordScreen extends AppCompatActivity {
    String TAG = "forgot";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    CountryCodePicker ccp;
    EditText mobile_edt;
    TextView get_otp_tv;

    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        
        
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
        get_otp_tv = findViewById(R.id.get_otp_tv);
    }

    private void functions() {
        get_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.isNetworkAvailable()) {
                    if (!mobile_edt.getText().toString().trim().isEmpty()) {
                        request_otp(mobile_edt.getText().toString());
                    } else {
                        Toast.makeText(ForgotPasswordScreen.this, "Please enter the mobile number.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ForgotPasswordScreen.this,  "Check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void request_otp( final String mobile) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.reset_request_otp;
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

                            globalClass.setPhone_number(mobile);
                            shared_preference.savePrefrence();
                            mView.hideDialog();
                            Toast.makeText(ForgotPasswordScreen.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            Intent intent = new Intent(ForgotPasswordScreen.this, ResetPasswordScreen.class);
                            startActivity(intent);
                            finish();

                            //globalClass.setRequest_token(request_token);


                           /* get_otp_tv.setVisibility(View.GONE);
                            ll_otp.setVisibility(View.VISIBLE);
                            mobile_edt.setEnabled(false);*/

                        }else {

                            mView.hideDialog();
                            Toast.makeText(ForgotPasswordScreen.this, message, Toast.LENGTH_LONG).show();
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

            globalClass.addToRequestQueue(ForgotPasswordScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
