package com.exam.entranceinew.ui.activity;

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
import com.exam.entranceinew.ApplicationConstants;
import com.exam.entranceinew.CategoryData;
import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.SubjectData;
import com.exam.entranceinew.ViewDialog;
import com.exam.entranceinew.adapter.HomeAdapter;
import com.exam.entranceinew.adapter.ReferenceBookAdapter;
import com.exam.entranceinew.adapter.SubjectAdapter;
import com.exam.entranceinew.adapter.TestAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    String TAG = "TestActivity";
    RecyclerView rvtest,rv_hor_test;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_study;
    ReferenceBookAdapter referenceBookAdapter;
    ImageView iv_back;
    TextView tv_header;
    SubjectAdapter subjectAdapter;
    List<Integer> img_list;
    TestAdapter testAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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
        rvtest = findViewById(R.id.rvtest);
        rv_hor_test = findViewById(R.id.rv_hor_test);
        rv_hor_test.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvtest.setLayoutManager(new LinearLayoutManager(TestActivity.this, LinearLayoutManager.VERTICAL, false));
    }
    private void function() {

        tv_header.setText("Online Test");
        arr_study = new ArrayList<>();
        img_list = new ArrayList<>();

        ArrayList<SubjectData> arrayList = new ArrayList<SubjectData>();

        arrayList.add(new SubjectData("Latest", R.mipmap.favorite));
        arrayList.add(new SubjectData("Maths", R.mipmap.maths_white));
        arrayList.add(new SubjectData("Science", R.mipmap.chemical));
        arrayList.add(new SubjectData("Biology", R.mipmap.dna));
        arrayList.add(new SubjectData("Chemistry", R.mipmap.chemistry_white));
        arrayList.add(new SubjectData("Physics", R.mipmap.physics_white));
        arrayList.add(new SubjectData("Reasoning", R.mipmap.logic));
        arrayList.add(new SubjectData("Civics", R.mipmap.brazil));
        arrayList.add(new SubjectData("Economics", R.mipmap.economy));
        arrayList.add(new SubjectData("Geography", R.mipmap.earth_globe));
        arrayList.add(new SubjectData("History", R.mipmap.history));
        arrayList.add(new SubjectData("Botany", R.mipmap.maple_leaf));
        arrayList.add(new SubjectData("Zoology", R.mipmap.butterfly));

        subjectAdapter = new SubjectAdapter(TestActivity.this, arrayList);
        rv_hor_test.setAdapter(subjectAdapter);



        testAdapter = new TestAdapter(TestActivity.this, arrayList);
        rvtest.setAdapter(testAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*private void reference_book() {
        mView.showDialog();
        final String tag_string_req = "reference_book";
        String url = ApplicationConstants.reference_book;
        Log.d(TAG, "notes_list: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("user_token", globalClass.getUser_token());
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

                                    JSONArray data = jobj.getJSONArray("data");
                                    for( int i = 0 ; i < data.length() ; i++ ) {

                                        JSONObject obj_data = data.getJSONObject(i);
                                        String id = obj_data.getString("id");
                                        String name = obj_data.getString("name");

                                        Log.d(TAG, "onResponse:name>>>> " + name);
                                        Log.d(TAG, "onResponse:id>>> " + id);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", id);
                                        hashMap.put("name", name);
                                        arr_study.add(hashMap);
                                    }


                                    rvStudyNotes.setLayoutManager(new LinearLayoutManager(TestActivity.this, LinearLayoutManager.VERTICAL, false));
                                    referenceBookAdapter = new ReferenceBookAdapter(TestActivity.this, arr_study);
                                    rvStudyNotes.setAdapter(referenceBookAdapter);

                                    referenceBookAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(TestActivity.this, message, Toast.LENGTH_LONG).show();
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

            globalClass.addToRequestQueue(TestActivity.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
