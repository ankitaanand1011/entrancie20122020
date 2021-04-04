package com.exam.entranceinew.ui.activity.studynotes;

import android.os.Bundle;
import android.text.Html;
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
import com.exam.entranceinew.utils.ExpandableTextView;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.ChaptersAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChaptersScreen extends AppCompatActivity {
    String TAG = "chapters";
    RecyclerView rvStudyNotes;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_study;
    ChaptersAdapter chaptersAdapter;
    ImageView iv_back;
    TextView tv_header;
    ExpandableTextView ex_tv,ex_tv_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_notes_description);
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
        ex_tv = findViewById(R.id.ex_tv);

        rvStudyNotes.setLayoutManager(new LinearLayoutManager(ChaptersScreen.this, LinearLayoutManager.VERTICAL, false));
    }
    private void function() {

        tv_header.setText(getIntent().getStringExtra("name"));
        arr_study = new ArrayList<>();
        chapters_list();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void chapters_list() {
        mView.showDialog();
        final String tag_string_req = "chapters_list";
        String url = ApplicationConstants.notes_subject_description;
        Log.d(TAG, "chapters_list: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("subject_id", getIntent().getStringExtra("value"));
            js.put("device", "mobile");

            Log.d(TAG, "chapters_list: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "chapters_list Response: " + jobj);

                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                    JSONObject data = jobj.getJSONObject("data");

                                    String subject_title = data.getString("subject_title");
                                    String subject_description = data.getString("subject_description");

                                    JSONArray chapter_list = data.getJSONArray("chapter_list");
                                    for( int i = 0 ; i < chapter_list.length() ; i++ ) {

                                        JSONObject obj_data = chapter_list.getJSONObject(i);
                                        String id = obj_data.getString("id");
                                        String name = obj_data.getString("name");

                                        Log.d(TAG, "onResponse:name>>>> " + name);
                                        Log.d(TAG, "onResponse:id>>> " + id);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", id);
                                        hashMap.put("name", name);
                                        arr_study.add(hashMap);

                                    }

                                    ex_tv.setText(Html.fromHtml(subject_description));
                                    rvStudyNotes.setLayoutManager(new LinearLayoutManager(ChaptersScreen.this, LinearLayoutManager.VERTICAL, false));
                                    chaptersAdapter = new ChaptersAdapter(ChaptersScreen.this, arr_study);
                                    rvStudyNotes.setAdapter(chaptersAdapter);

                                    chaptersAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(ChaptersScreen.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "chapters_list Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();

                    mView.hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();

                  /*  params.put("request_key", globalClass.getRequest_key());
                    params.put("request_token", globalClass.getRequest_token());
                    params.put("device", "mobile");*/


                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(ChaptersScreen.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
