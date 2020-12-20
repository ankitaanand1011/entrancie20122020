package com.exam.entranceinew.ui.activity.usersection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

import java.util.HashMap;
import java.util.Map;


public class SplashScreen extends AppCompatActivity {

    String TAG = "splash";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    String btn_clicked;
    ViewDialog mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();


        if(globalClass.getLogin_status()){

            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.splash_screen);


            initialize_view();
            function();
        }

    }

    private void initialize_view() {


        mView = new ViewDialog(this);
    }

    private void function() {


    }

    public void onClick(View view) {
        switch (view.getId()){

                case R.id.tv_register:
                    btn_clicked = "register";
                    get_request_token(btn_clicked);
                    break;

                case R.id.tv_login:
                    btn_clicked = "login";
                    get_request_token(btn_clicked);
                    break;
        }
    }

    private void get_request_token(final String btn_clicked) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.get_request_token;
        Log.d(TAG, "get_request_token: url > "+url);
        try{
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>(){


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "get_request_token Response: " + response);


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
                            String request_key = data.get("request_key").getAsString().replaceAll("\"", "");
                            String request_token = data.get("request_token").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:request_key>>>> " + request_key);
                            Log.d(TAG, "onResponse:request_token>>> " + request_token);

                            globalClass.setRequest_token(request_token);
                            globalClass.setRequest_key(request_key);
                            shared_preference.savePrefrence();

                            mView.hideDialog();

                           if(btn_clicked.equals("register")){

                               Intent intent = new Intent(SplashScreen.this, Signup1.class);
                               startActivity(intent);


                            }else {
                               Intent intent1 = new Intent(SplashScreen.this, LoginScreen.class);
                               startActivity(intent1);

                           }


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
                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();
                    params.put("device", "mobile");
                    Log.d(TAG, "getParams: "+params);
                    return params;
                }
            };
            globalClass.addToRequestQueue(SplashScreen.this, strReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
