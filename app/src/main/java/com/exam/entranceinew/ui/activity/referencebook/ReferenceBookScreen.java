package com.exam.entranceinew.ui.activity.referencebook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.ReferenceBookAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReferenceBookScreen extends AppCompatActivity {
    String TAG = "study_notes";
    RecyclerView rvStudyNotes;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_study;
    ReferenceBookAdapter referenceBookAdapter;
    ImageView iv_back;
    TextView tv_header;
    String match_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_notes);
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
        rvStudyNotes = findViewById(R.id.rvStudyNotes);
        rvStudyNotes.setLayoutManager(new LinearLayoutManager(ReferenceBookScreen.this, LinearLayoutManager.VERTICAL, false));
    }
    private void function() {
        match_id = getIntent().getStringExtra("ids");
        tv_header.setText("Chapters");
        arr_study = new ArrayList<>();
        reference_book();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void reference_book() {
        mView.showDialog();
        final String tag_string_req = "reference_book";
        String url = ApplicationConstants.books_section_description;
        Log.d(TAG, "book_list: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("book_id",getIntent().getStringExtra("book_id"));
            js.put("device", "mobile");

            Log.d(TAG, "reference_book: js >  "+js.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "reference_book Response: " + jobj);

                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {
                                    JSONObject data = jobj.getJSONObject("data");

                                    String id = data.getString("id");
                                    String name = data.getString("name");
                                    String top_description = data.getString("top_description");
                                    String bottom_description = data.getString("bottom_description");


                                    JSONArray section_list = data.getJSONArray("section_list");
                                    Log.d(TAG, "onResponse: data>>"+data);
                                    for( int i = 0 ; i < section_list.length() ; i++ ) {

                                        JSONObject obj_data = section_list.getJSONObject(i);
                                        String ids = obj_data.getString("id");

                                        String section_name = obj_data.getString("section_name");

                                        Log.d(TAG, "onResponse: section_name"+section_name);

                                        Log.d(TAG, "onResponse: hello");

                                        if(ids.matches(match_id)){

                                            JSONArray solutions = obj_data.getJSONArray("solutions");
                                            Log.d(TAG, "onResponse: sol"+solutions);
                                            for( int j = 0 ; j < solutions.length() ; j++ ) {
                                                JSONObject obj_solution = solutions.getJSONObject(j);

                                                String id_sol = obj_solution.getString("id");
                                                String title = obj_solution.getString("title");


                                                Log.d(TAG, "onResponse:id>>> " + ids);
                                                Log.d(TAG, "onResponse:section_name>>> " + ids);
                                                //     arr_sections.add(title);
                                                HashMap<String, String> hashMap1 = new HashMap<>();
                                                hashMap1.put("id_sol", id_sol);
                                                hashMap1.put("title", title);
                                                arr_study.add(hashMap1);
                                            }
                                        }
                                     //   listDataChild.put(arr_section_header.get(i), section);

                                    }


                                    rvStudyNotes.setLayoutManager(new LinearLayoutManager(ReferenceBookScreen.this, LinearLayoutManager.VERTICAL, false));
                                    referenceBookAdapter = new ReferenceBookAdapter(ReferenceBookScreen.this, arr_study);
                                    rvStudyNotes.setAdapter(referenceBookAdapter);

                                    referenceBookAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(ReferenceBookScreen.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "reference_book Error: " + error.getMessage());
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

            globalClass.addToRequestQueue(ReferenceBookScreen.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
