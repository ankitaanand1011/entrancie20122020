package com.exam.entranceinew.ui.activity.usersection;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.RvClassAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Signup3 extends AppCompatActivity {


    String TAG = "Signup3";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    TextView tv_create_account;
    RvClassAdapter rvClassAdapter;
    RecyclerView rv_class;
    private ArrayList<HashMap<String, String>> class_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup3);

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

        rv_class = findViewById(R.id.rv_class);
        tv_create_account = findViewById(R.id.tv_create_account);


    }

    private void function() {
        class_list = new ArrayList<>();

        String token = getIntent().getStringExtra("token");
        Log.d(TAG, "function: token >>>>> "+token);
        class_list(token);


    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_create_account:
              /*  Intent intent = new Intent(Signup3.this, MainActivity.class);
                startActivity(intent);*/
                Toast.makeText(Signup3.this, "Work in Progress.", Toast.LENGTH_LONG).show();
                break;


        }
    }

    private void class_list(final String token) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.class_list;
        Log.d(TAG, "class_list: url>> "+url);

        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("device", "mobile");

            Log.d(TAG, "class_list: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jobj) {
                    Log.d(TAG, "class_list Response: " + jobj);


                   // Gson gson = new Gson();

                    try {

                        String result =jobj.getString("result");
                        String message =jobj.getString("message");


                        Log.d(TAG, "Message: "+message);


                        if(result.equals("true")) {

                           JSONArray data = jobj.getJSONArray("data");
                            for( int i = 0 ; i < data.length() ; i++ ) {

                                JSONObject obj_data = data.getJSONObject(i);
                                String id = obj_data.getString("id");
                                String name = obj_data.getString("name");
                                String page_url = obj_data.getString("page_url");

                                Log.d(TAG, "onResponse:name>>>> " + name);
                                Log.d(TAG, "onResponse:page_url>>> " + page_url);
                                Log.d(TAG, "onResponse:id>>> " + id);

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("name", name);
                                hashMap.put("page_url", page_url);
                                class_list.add(hashMap);
                            }


                            rv_class.setLayoutManager(new GridLayoutManager(Signup3.this, 3));
                            rvClassAdapter = new RvClassAdapter(Signup3.this, class_list,tv_create_account,mView,token);
                            rv_class.setAdapter(rvClassAdapter);



                         /*   Toast.makeText(Signup3.this, message, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);*/
                            mView.hideDialog();
                         /*   Intent intent = new Intent(Signup3.this, MainActivity.class);
                            startActivity(intent);*/

                        }else{
                            mView.hideDialog();
                            Toast.makeText(Signup3.this, message, Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "class_list Error: " + error.getMessage());
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


                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(Signup3.this, jsonObjReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}