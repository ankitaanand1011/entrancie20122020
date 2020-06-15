package com.exam.entranceinew.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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
import com.android.volley.toolbox.StringRequest;
import com.exam.entranceinew.ApplicationConstants;
import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.ViewDialog;
import com.exam.entranceinew.adapter.BookSectionAdapter;
import com.exam.entranceinew.adapter.ReferenceBookAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookSectionActivity extends AppCompatActivity {
    String TAG = "book_section";
    RecyclerView rvStudyNotes;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_study;
    BookSectionAdapter bookSectionAdapter;
    ImageView iv_back;
    TextView tv_header;
    WebView bottomwebview,topwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_section);
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
        bottomwebview = findViewById(R.id.bottomwebview);
        topwebview = findViewById(R.id.topwebview);
        rvStudyNotes.setNestedScrollingEnabled(false);
        rvStudyNotes.setLayoutManager(new LinearLayoutManager(BookSectionActivity.this, LinearLayoutManager.VERTICAL, false));
    }
    private void function() {

        tv_header.setText(getIntent().getStringExtra("name"));
        arr_study = new ArrayList<>();
        reference_book_content_description();
        reference_book_section();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void reference_book_section() {
      //  mView.showDialog();
        final String tag_string_req = "reference_book_section";
        String url = ApplicationConstants.reference_book_section;
        Log.d(TAG, "reference_book_section: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("book_id", getIntent().getStringExtra("id"));
            js.put("device", "mobile");

            Log.d(TAG, "reference_book_section: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "reference_book_section Response: " + jobj);

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

                                        JSONArray solutions = obj_data.getJSONArray("solutions");
                                        Log.d(TAG, "onResponse: solution>>"+solutions);

                                        for( int j = 0 ; j < solutions.length() ;j++ ) {

                                            JSONObject solutions_data = solutions.getJSONObject(j);

                                            String id_title = solutions_data.getString("id");
                                            String title = solutions_data.getString("title");

                                            Log.d(TAG, "onResponse:name>>>> " + id_title);
                                            Log.d(TAG, "onResponse:id>>> " + title);

                                            HashMap<String, String> hashMap = new HashMap<>();
                                            hashMap.put("id", id_title);
                                            hashMap.put("name", title);
                                            arr_study.add(hashMap);
                                        }

                                        Log.d(TAG, "onResponse: out loop j");

                                    }


                                    Log.d(TAG, "onResponse: out loop i");

                                    rvStudyNotes.setLayoutManager(new LinearLayoutManager(BookSectionActivity.this, LinearLayoutManager.VERTICAL, false));
                                    bookSectionAdapter = new BookSectionAdapter(BookSectionActivity.this, arr_study);
                                    rvStudyNotes.setAdapter(bookSectionAdapter);

                                    bookSectionAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(BookSectionActivity.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "reference_book_section Error: " + error.getMessage());
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

            globalClass.addToRequestQueue(BookSectionActivity.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reference_book_content_description() {
        mView.showDialog();
        final String tag_string_req = "reference_book_content_description";
        String url = ApplicationConstants.reference_book_content_description;
        Log.d(TAG, "reference_book_content_description: url>>>> " + url);
        try {
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "reference_book_content_description Response: " + response);


                    Gson gson = new Gson();

                    try {


                        JsonObject jobj = gson.fromJson(response, JsonObject.class);
                        String result = jobj.get("result").getAsString().replaceAll("\"", "");
                        String message = jobj.get("message").getAsString().replaceAll("\"", "");


                        Log.d(TAG, "Message: " + message);


                        if (result.equals("true")) {

                            JsonObject data = jobj.getAsJsonObject("data");
                            String name = data.get("name").getAsString().replaceAll("\"", "");
                            String id = data.get("id").getAsString().replaceAll("\"", "");
                        //    String type = data.get("type").getAsString().replaceAll("\"", "");
                        //    String question_set_id = data.get("question_set_id").getAsString().replaceAll("\"", "");
                            String top_description = data.get("top_description").getAsString().replaceAll("\"", "");
                            String bottom_description = data.get("bottom_description").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:description>>>> " + top_description);
                            Log.d(TAG, "onResponse:id>>> " + id);
                            Log.d(TAG, "onResponse:name>>> " + name);
                            top_description = top_description.replace("&#39;", "\'");
                            top_description = top_description.replace("&#34;", "\"");
                            Log.d(TAG, "onResponse:description replace>>>> " + top_description);

                            bottom_description = bottom_description.replace("&#39;", "\'");
                            bottom_description = bottom_description.replace("&#34;", "\"");
                            Log.d(TAG, "onResponse:description replace>>>> " + bottom_description);



                         //   bottomwebview.setHtml(description);
                            bottomwebview.loadData(bottom_description, "text/html", "UTF-8");
                            topwebview.loadData(top_description, "text/html", "UTF-8");
                           // mView.hideDialog();
                            reference_book_section();

                        } else {
                            mView.hideDialog();
                            Toast.makeText(BookSectionActivity.this, message, Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "reference_book_content_description Error: " + error.getMessage());
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
                    params.put("book_id", getIntent().getStringExtra("id"));


                    Log.d(TAG, "getParams: " + params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(BookSectionActivity.this, strReq, tag_string_req);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
