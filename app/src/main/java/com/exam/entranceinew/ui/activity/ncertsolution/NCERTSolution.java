package com.exam.entranceinew.ui.activity.ncertsolution;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.ExpandableTextView;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.SolutionsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NCERTSolution extends AppCompatActivity {
    String TAG = "NCERTSolution";
    RecyclerView rvNcertSol;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> ncert_arr;
    TextView tv_header,tv_class_name;
    ImageView iv_back;
    ExpandableTextView ex_tv;
    //List<String> ncert_arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_ncert_solution);
        initialize_view();
        function();
    }

    private void initialize_view() {
        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();
        mView = new ViewDialog(this);
        iv_back = findViewById(R.id.iv_back);
        tv_header = findViewById(R.id.tv_header);
        rvNcertSol = findViewById(R.id.rvNcertSol);
        ex_tv = findViewById(R.id.ex_tv);

        rvNcertSol.setNestedScrollingEnabled(false);
    }
    private void function() {

        tv_header.setText(getIntent().getStringExtra("name"));
        ncert_arr = new ArrayList<>();
        ncert_get_chapters();

      /*  ncert_arr.add("NCERT Solutions for class 6");
        ncert_arr.add("NCERT Solutions for class 7");
        ncert_arr.add("NCERT Solutions for class 8");
        ncert_arr.add("NCERT Solutions for class 9");
        ncert_arr.add("NCERT Solutions for class 10");
        ncert_arr.add("NCERT Solutions for class 11");
        ncert_arr.add("NCERT Solutions for class 12");*/





        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ncert_get_chapters() {
        mView.showDialog();
        final String tag_string_req = "ncert_get_chapters";
        String url = ApplicationConstants.ncert_solution_description;
        Log.d(TAG, "ncert_get_chapters: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("user_token", globalClass.getUser_token());
            js.put("ncert_solution_id", getIntent().getStringExtra("value"));
            js.put("device", "mobile");

            Log.d(TAG, "ncert_get_chapters: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "ncert_sol_book Response: " + jobj);

                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {
                                   JSONObject data = jobj.getJSONObject("data");
                                   String id = data.getString("id");
                                   String name = data.getString("name");
                                   String description = data.getString("description");

                                   JSONArray topic_discussion_list = data.getJSONArray("topic_discussion_list");
                                   // JSONArray subject_list = jobj.getJSONArray("data");
                                    for( int i = 0 ; i < topic_discussion_list.length() ; i++ ) {

                                        JSONObject obj_data = topic_discussion_list.getJSONObject(i);
                                        String id_val = obj_data.getString("id");
                                        String name_val = obj_data.getString("name");

                                        Log.d(TAG, "onResponse:name>>>> " + name);
                                        Log.d(TAG, "onResponse:id>>> " + id);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id_val", id_val);
                                        hashMap.put("name_val", name_val);
                                        ncert_arr.add(hashMap);
                                    }
                              //      tv_class_name.setText("NCERT Solutions for "+student_class_name);
                                    ex_tv.setText(Html.fromHtml(description));
                                    rvNcertSol.setLayoutManager(new LinearLayoutManager(NCERTSolution.this, LinearLayoutManager.VERTICAL, false));
                                    SolutionsAdapter solutionsAdapter = new SolutionsAdapter(NCERTSolution.this,ncert_arr);
                                    rvNcertSol.setAdapter(solutionsAdapter);

                                    solutionsAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(NCERTSolution.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "ncert_get_chapters Error: " + error.getMessage());
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
                    params.put("user_token", globalClass.getUser_token());


                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(NCERTSolution.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}