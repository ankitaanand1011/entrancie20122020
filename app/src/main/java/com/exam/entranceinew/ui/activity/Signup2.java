package com.exam.entranceinew.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;


public class Signup2 extends AppCompatActivity {


    TextView tv_signup;
    String TAG = "Signup2";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]";

    EditText fname_edt,lname_edt,email_edt,phn_edt,password_edt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initialize_view();
        function();


       /* tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   if (globalClass.isNetworkAvailable()) {

                    if (!input_user_name.getText().toString().trim().isEmpty()) {
                        if (!input_email.getText().toString().trim().isEmpty()) {
                            if (!input_password.getText().toString().trim().isEmpty()) {
                                if (!input_mobile.getText().toString().trim().isEmpty()) {
                                    if (input_email.getText().toString().matches(emailPattern)) {
                                        checkLogin(input_email.getText().toString(), input_password.getText().toString(),input_user_name.getText().toString(),input_mobile.getText().toString());
                                    } else {
                                        Toasty.error(Signup.this, "Invalid email address.", Toast.LENGTH_LONG, true).show();
                                    }

                                } else {
                                    Toasty.warning(Signup.this, "Please enter the mobile number.", Toast.LENGTH_LONG, true).show();
                                }
                            } else {
                                Toasty.warning(Signup.this, "Please enter the password.", Toast.LENGTH_LONG, true).show();
                            }
                        } else {
                            Toasty.warning(Signup.this, "Please enter the email id.", Toast.LENGTH_LONG, true).show();
                        }
                    } else {
                        Toasty.info(Signup.this, "Please enter your name.", Toast.LENGTH_LONG, true).show();
                    }
                } else {
                    Toasty.info(Signup.this, "Check your internet connection.", Toast.LENGTH_LONG, true).show();
                }*//*

            }
        });
*/
    }

    private void initialize_view() {
        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();
        mView = new ViewDialog(this);

        fname_edt = findViewById(R.id.fname_edt);
        lname_edt = findViewById(R.id.lname_edt);
        email_edt = findViewById(R.id.email_edt);
        phn_edt = findViewById(R.id.phn_edt);
        password_edt = findViewById(R.id.password_edt);

    }

    private void function() {

        phn_edt.setEnabled(false);
        phn_edt.setText(globalClass.getPhone_number());

    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_create_account:
                if(globalClass.isNetworkAvailable()) {
                    if (!fname_edt.getText().toString().trim().isEmpty()) {
                        if (!lname_edt.getText().toString().trim().isEmpty()) {
                            if (!email_edt.getText().toString().trim().isEmpty()) {
                                if (!password_edt.getText().toString().trim().isEmpty()) {
                                    registration_2(fname_edt.getText().toString(), lname_edt.getText().toString(),
                                            email_edt.getText().toString(), password_edt.getText().toString());
                                } else {
                                    Toast.makeText(Signup2.this, "Please enter the password.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Signup2.this, "Please enter the email id.", Toast.LENGTH_LONG).show();
                            }
                        }else {
                                Toast.makeText(Signup2.this, "Please enter your last name.", Toast.LENGTH_LONG).show();
                            }
                    }else {
                        Toast.makeText(Signup2.this, "Please enter your first name.", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Signup2.this, "Check your internet connection.", Toast.LENGTH_LONG).show();
                }

                break;


        }
    }

    private void registration_2(final String fname,final String lname,final String email,final String password) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.registration_1;
        Log.d(TAG, "registration_2: url>>>> "+url);
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
                            shared_preference.savePrefrence();


                            Toast.makeText(Signup2.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            mView.hideDialog();
                            Intent intent = new Intent(Signup2.this, Signup3.class);
                            intent.putExtra("token",token);
                            startActivity(intent);

                        }else{
                            mView.hideDialog();
                            Toast.makeText(Signup2.this, message, Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();

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
                    params.put("mobile", globalClass.getPhone_number());
                    params.put("token", getIntent().getStringExtra("token"));
                    params.put("first_name", fname);
                    params.put("last_name", lname);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("confirm_password", password);

                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(Signup2.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}