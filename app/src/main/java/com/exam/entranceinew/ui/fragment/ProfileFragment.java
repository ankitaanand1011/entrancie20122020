package com.exam.entranceinew.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.R;
import com.exam.entranceinew.adapter.RvClassAdapter;
import com.exam.entranceinew.ui.activity.usersection.Signup3;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    String TAG ="profile";
    LinearLayout ll_qty;
    TextView qty_spinner,tv_cancel,tv_name,tv_phn,tv_email,tv_class,tv_submit;
    RelativeLayout rl_password;
    ImageView iv_edit;
    LinearLayout ll_show_profile,ll_edit_profile;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    EditText edit_fname,edit_lname,edit_phn,edit_email;
    ArrayList<String> class_list,class_list_id;
    String class_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialize_view(view);
        functions();


        return view;

    }

    private void initialize_view(View view) {
        globalClass = (GlobalClass)Objects.requireNonNull(getActivity()).getApplicationContext();
        shared_preference = new Shared_Preference(getActivity());
        shared_preference.loadPrefrence();
        mView = new ViewDialog(getActivity());
        ll_qty = view.findViewById(R.id.ll_qty);
        qty_spinner = view.findViewById(R.id.qty_spinner);
        rl_password = view.findViewById(R.id.rl_password);
        iv_edit = view.findViewById(R.id.iv_edit);
        ll_show_profile = view.findViewById(R.id.ll_show_profile);
        ll_edit_profile = view.findViewById(R.id.ll_edit_profile);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phn = view.findViewById(R.id.tv_phn);
        tv_email = view.findViewById(R.id.tv_email);
        tv_class = view.findViewById(R.id.tv_class);
        edit_fname = view.findViewById(R.id.edit_fname);
        edit_lname = view.findViewById(R.id.edit_lname);
        edit_phn = view.findViewById(R.id.edit_phn);
        edit_email = view.findViewById(R.id.edit_email);
        tv_submit = view.findViewById(R.id.tv_submit);
    }

    private void functions() {

        class_list = new ArrayList<>();
        class_list_id = new ArrayList<>();


        ll_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class_dialog();
            }
        });

        user_profile();
        rl_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(getActivity(), ChangePasswordScreen.class);
                startActivity(intent);*/

                dialog_change_password();
            }
        });

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_edit_profile.setVisibility(View.VISIBLE);
                ll_show_profile.setVisibility(View.GONE);
                iv_edit.setImageResource(R.drawable.edit_active);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_edit_profile.setVisibility(View.GONE);
                ll_show_profile.setVisibility(View.VISIBLE);
                iv_edit.setImageResource(R.drawable.edit);
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(!edit_fname.getText().toString().isEmpty()){
                    if (!edit_lname.getText().toString().isEmpty()){
                        if(!edit_email.getText().toString().isEmpty()){
                            update_profile();
                        }else{
                            Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getResources().getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), Objects.requireNonNull(getActivity()).getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
                }

            }
        });
        
        


    }
/*
    private void qty_dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Class");
        final String[] qty_arr = getResources().getStringArray(R.array.class_array);
        builder.setItems(qty_arr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                String selected_item= qty_arr[item];
                Log.d(TAG, "onClick:selected_item>> "+selected_item);
                String[] separated = selected_item.split(" ");
                String str1 = separated[0];
                String str2 = separated[1];
                qty_spinner.setText(str2);

               // update_cart_qty(holder,sku,quote_id,item_id,selected_item);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }*/

    public void class_dialog() {


        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_class_dialog, null);
        builderSingle.setView(dialogView);



        Log.d(TAG, "class_list: " + class_list);
        Log.d(TAG, "class_list_id: " + class_list_id);

        final ListView listView = (ListView) dialogView.findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), R.layout.class_lv_single_row, class_list);
        listView.setAdapter(adapter);


        final AlertDialog alertDialog = builderSingle.create();
        alertDialog.show();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id_1) {
                String strName = String.valueOf(class_list.get(position));
                class_id = String.valueOf(class_list_id.get(position));
                Log.d(TAG, "class_name: " + strName);
                Log.d(TAG, "class_id: " + class_id);
                qty_spinner.setText(strName);
                alertDialog.dismiss();


            }
        });

    }


    private void dialog_change_password() {

        final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_change_password);

        final EditText edtOldPwd =  dialog.findViewById(R.id.edtOldPwd);
        final EditText edtNewPwd =  dialog.findViewById(R.id.edtNewPwd);
        final EditText edtConfirmPwd =  dialog.findViewById(R.id.edtConfirmPwd);
        TextView tv_ChangePassword = dialog.findViewById(R.id.tv_ChangePassword);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);



        tv_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String old_pass = edtOldPwd.getText().toString();
                String new_pass = edtNewPwd.getText().toString();
                String confirm_pass = edtConfirmPwd.getText().toString();
                if(!old_pass.isEmpty() ){
                    if (!new_pass.isEmpty()){
                        if(!confirm_pass.isEmpty()){

                            if(confirm_pass.matches(new_pass)){
                                change_password(old_pass,new_pass,confirm_pass,dialog);
                            }else{
                                Toast.makeText(getActivity(),"Password mismatch",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getActivity(),"Please enter confirm password",Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Toast.makeText(getActivity(),"Please enter your new password",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Please enter your old password",Toast.LENGTH_LONG).show();
                }


            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


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
                                    shared_preference.savePrefrence();


                                    tv_name.setText("Hi, "+first_name+" "+last_name);
                                    tv_phn.setText(country_code+" "+mobile);
                                    tv_email.setText(email);
                                    tv_class.setText(student_class_name);
                                    edit_fname.setText(first_name);
                                    edit_lname.setText(last_name);
                                    edit_phn.setText(country_code+" "+mobile);
                                    edit_email.setText(email);
                                    qty_spinner.setText(student_class_name);

                                    class_list();

                                    //mView.hideDialog();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onSuccess:id "+message);
                                }else {

                                    mView.hideDialog();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "user_profile Error: " + error.getMessage());
                    Toast.makeText(getActivity(),
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
            globalClass.addToRequestQueue(getActivity(), jsonObjReq, tag_string_req);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void class_list() {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";

       // mView.showDialog();
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

                                        /*HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", id);
                                        hashMap.put("name", name);
                                        hashMap.put("page_url", page_url);*/
                                        class_list.add(name);
                                        class_list_id.add(id);
                                    }



                                    mView.hideDialog();


                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "class_list Error: " + error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();

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

            globalClass.addToRequestQueue(getActivity(), jsonObjReq, tag_string_req);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void update_profile() {
        // Tag used to cancel the request

        final String tag_string_req = "user_profile";

        mView.showDialog();
        String url = ApplicationConstants.update_profile;
        Log.d(TAG, "update_profile: url >> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("device", "mobile");
            js.put("user_token", globalClass.getUser_token());
            js.put("first_name", edit_fname.getText().toString());
            js.put("last_name", edit_lname.getText().toString());
            js.put("email", edit_email.getText().toString());
            js.put("student_class_id",class_id);
            js.put("mobile",edit_phn.getText().toString());
            //  js.put("country_code", ccp.getSelectedCountryCode());

            Log.d(TAG, "update_profile: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "update_profile Response: " + jobj);


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
                                    String student_class_id = data.getString("student_class_id");

                                    Log.d(TAG, "onResponse:request_key>>>> " + mobile);
                                    Log.d(TAG, "onResponse:request_token>>> " + country_code);

                                    //globalClass.setRequest_token(request_token);
                                    globalClass.setPhone_number(mobile);
                                    globalClass.setCountry_code(country_code);
                                    shared_preference.savePrefrence();


                                    tv_name.setText("Hi, "+first_name+" "+last_name);
                                    tv_phn.setText(country_code+" "+mobile);
                                    tv_email.setText(email);
                                    tv_class.setText(student_class_name);
                                    edit_fname.setText(first_name);
                                    edit_lname.setText(last_name);
                                    edit_phn.setText(country_code+" "+mobile);
                                    edit_email.setText(email);
                                    qty_spinner.setText(student_class_name);

                                    globalClass.setF_name(first_name);
                                    globalClass.setL_name(last_name);
                                    globalClass.setPhone_number(mobile);
                                    globalClass.setEmail(email);

                                    shared_preference.savePrefrence();
                                    ll_edit_profile.setVisibility(View.GONE);
                                    ll_show_profile.setVisibility(View.VISIBLE);
                                    iv_edit.setImageResource(R.drawable.edit);
                                    
                                    mView.hideDialog();
                                //    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onSuccess:id "+message);
                                }else {

                                    mView.hideDialog();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "update_profile Error: " + error.getMessage());
                    Toast.makeText(getActivity(),
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
            globalClass.addToRequestQueue(getActivity(), jsonObjReq, tag_string_req);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void change_password(String old_password, String new_password, String confirm_password, final Dialog dialog) {
        // Tag used to cancel the request

        final String tag_string_req = "change_password";

        mView.showDialog();
        String url = ApplicationConstants.change_password;
        Log.d(TAG, "change_password: url >> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("device", "mobile");
            js.put("user_token", globalClass.getUser_token());
            js.put("country_code", globalClass.getCountry_code());
            js.put("mobile", globalClass.getPhone_number());
            js.put("password", old_password);
            js.put("new_password", new_password);
            js.put("confirm_new_password", confirm_password);

            Log.d(TAG, "change_password: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "change_password Response: " + jobj);


                            Gson gson = new Gson();

                            try {


                                String result =jobj.getString("result");
                                String message =jobj.getString("message");
                                Log.d(TAG, "Message: "+message);


                                dialog.dismiss();
                                mView.hideDialog();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onSuccess:id "+message);


                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "change_password Error: " + error.getMessage());
                    Toast.makeText(getActivity(),
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
            globalClass.addToRequestQueue(getActivity(), jsonObjReq, tag_string_req);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
