package com.exam.entranceinew.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exam.entranceinew.ApplicationConstants;
import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.ui.activity.MainActivity;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.ViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RvClassAdapter extends RecyclerView.Adapter<RvClassAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> arr_category;
    GlobalClass globalClass;
    ViewDialog mView;
    TextView tv_create_account;
    Shared_Preference shared_preference;
    String token;
    String pos = "0";
    String TAG= "adapterclass";


    public RvClassAdapter(Context c, ArrayList<HashMap<String,String>> arr_category ,
                          TextView tv_create_account, ViewDialog mView, String token) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.arr_category = arr_category;
        this.tv_create_account = tv_create_account;
        this.mView = mView;
        this.token = token;

        globalClass = (GlobalClass)context.getApplicationContext();
        shared_preference = new Shared_Preference(context);
        shared_preference.loadPrefrence();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.class_row_item, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String name = arr_category.get(position).get("name");
        holder.tv_class.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos=arr_category.get(position).get("id");
                holder.tv_class.setBackground(context.getResources().getDrawable(R.drawable.gradient_bg_noradius));
                holder.tv_class.setTextColor(context.getResources().getColor(R.color.white));

            }
        });

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: pos>>> "+pos);
                registration_2(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
            return arr_category.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_class;
        ViewHolder(View itemView) {
            super(itemView);

       //     ivProduct = itemView.findViewById(R.id.ivProduct);
            tv_class = itemView.findViewById(R.id.tv_class);

        }
    }



    private void registration_2(final String pos) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

        mView.showDialog();
        String url = ApplicationConstants.registration_2;
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
                     //       String student_class = data.get("student_class").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:email>>>> " + email);
                            Log.d(TAG, "onResponse:last_name>>> " + last_name);
                            Log.d(TAG, "onResponse:first_name>>> " + first_name);
                            Log.d(TAG, "onResponse:token>>> " + token);
                         //   Log.d(TAG, "onResponse:student_class>>> " + student_class);


                            globalClass.setPhone_number(mobile);
                            globalClass.setEmail(email);
                            globalClass.setF_name(first_name);
                            globalClass.setL_name(last_name);
                            globalClass.setUser_token(token);
                            globalClass.setLogin_status(true);
                            shared_preference.savePrefrence();


                            Toast.makeText(context, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess:id "+message);
                            mView.hideDialog();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("token",token);
                            context.startActivity(intent);
                            ((Activity)context).finish();

                        }else{
                            mView.hideDialog();
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "registration Error: " + error.getMessage());
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();

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
                    params.put("token", token);
                    params.put("student_class", pos);

                    Log.d(TAG, "getParams: "+params);
                    return params;
                }
            };
            globalClass.addToRequestQueue(context, strReq, tag_string_req);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}