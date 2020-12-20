package com.exam.entranceinew.ui.activity.ncertsolution;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.RecyclerItemClickListener;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.SolutionsChapterAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NcertChapters extends AppCompatActivity {
    String TAG = "NCERTSolution";
    RecyclerView rvNcertSol;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> ncert_chapter_arr;
    TextView tv_header;
    ImageView iv_back;
 //   List<String> ncert_chapter_arr;
    TextView topic_name;
    WebView bottomwebview,topwebview;
    ImageView iv_layout_right,ivCloseDrawer;
    CardView llRightDrawer;
    String selected_id;
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
        topic_name = findViewById(R.id.topic_name);
        bottomwebview = findViewById(R.id.bottomwebview);
        topwebview = findViewById(R.id.topwebview);
        iv_layout_right = findViewById(R.id.iv_layout_right);
        llRightDrawer = findViewById(R.id.llRightDrawer);
        ivCloseDrawer = findViewById(R.id.ivCloseDrawer);
        rvNcertSol = findViewById(R.id.rvStudyNotes);
        bottomwebview.setVisibility(View.GONE);



      //  rvNcertSol.setNestedScrollingEnabled(false);
     //   rvNcertSol.setLayoutManager(new LinearLayoutManager(NcertChapters.this, LinearLayoutManager.VERTICAL, false));

    }



    private void function() {

        tv_header.setText("Contents");
        ncert_chapter_arr = new ArrayList<>();
        Log.d(TAG, "function: intent value >>>  "+getIntent().getStringExtra("id"));


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selected_id = getIntent().getStringExtra("id");
        ncert_sol_get_content(selected_id);

        iv_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation RightSwipe = AnimationUtils.loadAnimation(NcertChapters.this, R.anim.anim_slide_in_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.VISIBLE);
            }
        });

        ivCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation RightSwipe = AnimationUtils.loadAnimation(NcertChapters.this, R.anim.anim_slide_out_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.GONE);
            }
        });

        rvNcertSol.addOnItemTouchListener(
                new RecyclerItemClickListener(NcertChapters.this, rvNcertSol ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d("qwerty", "onItemClick: "+ncert_chapter_arr.get(position).get("name"));
                        selected_id = ncert_chapter_arr.get(position).get("id");
                        ncert_sol_get_content(selected_id);
                        Animation RightSwipe = AnimationUtils.loadAnimation(NcertChapters.this, R.anim.anim_slide_out_right);
                        llRightDrawer.startAnimation(RightSwipe);
                        llRightDrawer.setVisibility(View.GONE);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Log.d("qwerty", "onLongItemClick: "+ncert_chapter_arr.get(position).get("name"));
                    }
                })
        );

    }




    private void ncert_sol_get_content(final String selected_id) {
        mView.showDialog();
        final String tag_string_req = "ncert_sol_get_content";
        String url = ApplicationConstants.ncert_sol_get_content;
        Log.d(TAG, "ncert_sol_get_content: url>>>> " + url);
        try {
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "ncert_sol_get_content Response: " + response);


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

                            String description = data.get("description").getAsString().replaceAll("\"", "");

                            description = description.replace("&#39;", "\'");
                            description = description.replace("&#34;", "\"");
/*
                            Log.d(TAG, "onResponse:description>>>> " + top_description);
                            Log.d(TAG, "onResponse:id>>> " + id);
                            Log.d(TAG, "onResponse:name>>> " + name);

                            Log.d(TAG, "onResponse:description replace>>>> " + top_description);

                            bottom_description = bottom_description.replace("&#39;", "\'");
                            bottom_description = bottom_description.replace("&#34;", "\"");
                            Log.d(TAG, "onResponse:description replace>>>> " + bottom_description);
*/

                            topic_name.setText(name);

                            //   bottomwebview.setHtml(description);
                          //  bottomwebview.loadData(bottom_description, "text/html", "UTF-8");
                            topwebview.loadData(description, "text/html", "UTF-8");
                            // mView.hideDialog();
                            ncert_sol_get_section(selected_id);

                        } else {
                            mView.hideDialog();
                            Toast.makeText(NcertChapters.this, message, Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "ncert_sol_get_content Error: " + error.getMessage());
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
                    params.put("ncert_solution_id", selected_id);


                    Log.d(TAG, "getParams: " + params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(NcertChapters.this, strReq, tag_string_req);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void ncert_sol_get_section(String id) {
        //  mView.showDialog();

    //    Log.d("qwerty", "ncert_sol_get_section: id "+getIntent().getStringExtra("id"));
        final String tag_string_req = "ncert_sol_get_section";
        String url = ApplicationConstants.ncert_sol_get_section;
        Log.d(TAG, "ncert_sol_get_section: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("ncert_solution_id", selected_id);
            js.put("user_token", globalClass.getUser_token());
            js.put("device", "mobile");

            Log.d(TAG, "ncert_sol_get_section: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "ncert_sol_get_section Response: " + jobj);

                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                    JSONArray data = jobj.getJSONArray("data");
                                    for( int i = 0 ; i < data.length() ; i++ ) {

                                        JSONObject obj_data = data.getJSONObject(i);
                                        String ids = obj_data.getString("id");
                                        String name = obj_data.getString("name");

                                        Log.d(TAG, "onResponse:id>>> " + ids);
                                        Log.d(TAG, "onResponse:section_name>>> " + ids);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("id", ids);
                                        hashMap.put("name", name);
                                        ncert_chapter_arr.add(hashMap);
                                    }





                                    Log.d(TAG, "onResponse: out loop i");

                                    rvNcertSol.setLayoutManager(new LinearLayoutManager(NcertChapters.this, LinearLayoutManager.VERTICAL, false));
                                    SolutionsChapterAdapter solutionsChapterAdapter = new SolutionsChapterAdapter(NcertChapters.this,ncert_chapter_arr);
                                    rvNcertSol.setAdapter(solutionsChapterAdapter);

                                    solutionsChapterAdapter.notifyDataSetChanged();
                                    mView.hideDialog();




                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(NcertChapters.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "ncert_sol_get_section Error: " + error.getMessage());
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
                    params.put("ncert_solution_id", selected_id);


                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(NcertChapters.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   /* private void initialize_view() {
        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();
        mView = new ViewDialog(this);
        iv_back = findViewById(R.id.iv_back);
        tv_header = findViewById(R.id.tv_header);
        rvNcertSol = findViewById(R.id.rvNcertSol);
        rvNcertSol.setNestedScrollingEnabled(false);
    }
    private void function() {

        tv_header.setText("NCERT Chapters");
        ncert_chapter_arr = new ArrayList<>();
        // notes_list();

        ncert_chapter_arr.add("Chapter 1");
        ncert_chapter_arr.add("Chapter 2");
        ncert_chapter_arr.add("Chapter 3");
        ncert_chapter_arr.add("Chapter 4");
        ncert_chapter_arr.add("Chapter 5");
        ncert_chapter_arr.add("Chapter 6");
        ncert_chapter_arr.add("Chapter 7");



        rvNcertSol.setLayoutManager(new LinearLayoutManager(NcertChapters.this, LinearLayoutManager.VERTICAL, false));
        SolutionsChapterAdapter solutionsChapterAdapter = new SolutionsChapterAdapter(NcertChapters.this,ncert_chapter_arr);
        rvNcertSol.setAdapter(solutionsChapterAdapter);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/

}