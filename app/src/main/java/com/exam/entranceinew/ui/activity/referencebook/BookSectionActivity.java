package com.exam.entranceinew.ui.activity.referencebook;

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
import com.exam.entranceinew.adapter.PdfImageAdapter;
import com.exam.entranceinew.adapter.SectionlistAdapter;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.BookSectionAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
    private ArrayList<HashMap<String, String>> arr_images;
    BookSectionAdapter bookSectionAdapter;
    ImageView iv_back;
    TextView tv_header,topic_name;
    WebView bottomwebview,topwebview;
    ImageView iv_layout_right,ivCloseDrawer;
    CardView llRightDrawer;
    RecyclerView rvPdfList;

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
        rvStudyNotes = findViewById(R.id.rvStudyNotes);
        bottomwebview = findViewById(R.id.bottomwebview);
        topwebview = findViewById(R.id.topwebview);
        iv_layout_right = findViewById(R.id.iv_layout_right);
        llRightDrawer = findViewById(R.id.llRightDrawer);
        ivCloseDrawer = findViewById(R.id.ivCloseDrawer);
        rvPdfList = findViewById(R.id.rvPdfList);

    }
    private void function() {

        tv_header.setText("Contents");
        topic_name.setText(getIntent().getStringExtra("title"));
        arr_images = new ArrayList<>();
      //  reference_book_content_description();
        reference_book_section();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        iv_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation RightSwipe = AnimationUtils.loadAnimation(BookSectionActivity.this, R.anim.anim_slide_in_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.VISIBLE);
            }
        });

        ivCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation RightSwipe = AnimationUtils.loadAnimation(BookSectionActivity.this, R.anim.anim_slide_out_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.GONE);
            }
        });

        //rvPdfList.setNestedScrollingEnabled(false);
    //    rvPdfList. setNestedScrollingEnabled(false);

        rvStudyNotes.setLayoutManager(new LinearLayoutManager(BookSectionActivity.this, LinearLayoutManager.VERTICAL, false));
        rvPdfList.setLayoutManager(new LinearLayoutManager(BookSectionActivity.this, LinearLayoutManager.VERTICAL, false));


    }

    private void reference_book_section() {
        mView.showDialog();

        //Log.d("qwerty", "books_solution_description: id "+getIntent().getStringExtra("id"));
        final String tag_string_req = "books_solution_description";
        String url = ApplicationConstants.books_solution_description;
        Log.d(TAG, "books_solution_description: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("book_solution_id", getIntent().getStringExtra("id_sol"));
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


                                    JSONObject obj_data = jobj.getJSONObject("data");
                                    String id = obj_data.getString("id");
                                    String title = obj_data.getString("title");
                                    String link_title = obj_data.getString("link_title");
                                    String description = obj_data.getString("description");


                                    Log.d(TAG, "onResponse:id>>> " + id);

                                    JSONArray pdf_images = obj_data.getJSONArray("pdf_images");
                                    Log.d(TAG, "onResponse: pdf_images length>>> "+pdf_images.length());
                                    if(pdf_images.length()>0) {
                                        rvPdfList.setVisibility(View.VISIBLE);
                                        for (int i = 0; i < pdf_images.length(); i++) {

                                            String images_link = String.valueOf(pdf_images.get(i));

                                            Log.d(TAG, "onResponse: images"+images_link);
                                            HashMap<String, String> hashMap = new HashMap<>();
                                            hashMap.put("images_link", images_link);

                                            arr_images.add(hashMap);

                                        }


                                        PdfImageAdapter pdfImageAdapter = new PdfImageAdapter(BookSectionActivity.this, arr_images);
                                        rvPdfList.setAdapter(pdfImageAdapter);
                                        pdfImageAdapter.notifyDataSetChanged();
                                    }

                                    description = description.replace("&#39;", "\'");
                                    description = description.replace("&#34;", "\"");
                                    Log.d(TAG, "onResponse:description replace>>>> " + description);

                                    topic_name.setText(link_title);

                                    topwebview.loadData(description, "text/html", "UTF-8");


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




                    Log.d(TAG, "getParams: "+params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(BookSectionActivity.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private void reference_book_content_description() {
        mView.showDialog();
        final String tag_string_req = "reference_book_content_description";
        String url = ApplicationConstants.books_solution_description;
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
                         //   String name = data.get("name").getAsString().replaceAll("\"", "");
                            String id = data.get("id").getAsString().replaceAll("\"", "");
                            String title = data.get("title").getAsString().replaceAll("\"", "");
                            String link_title = data.get("link_title").getAsString().replaceAll("\"", "");
                            String description = data.get("description").getAsString().replaceAll("\"", "");

                            JSONArray section_list = data.getJSONArray("section_list");
                            Log.d(TAG, "onResponse: data>>"+data);
                            for( int i = 0 ; i < section_list.length() ; i++ ) {

                                JSONObject obj_data = section_list.getJSONObject(i);
                            }
                            JSONArray pdf_images = data.ge("pdf_images");
                            for( int i = 0 ; i < pdf_images.size() ; i++ ){
                                JsonObject obj_images = pdf_images.getAsJsonObject().get(i);
                            }

                            description = description.replace("&#39;", "\'");
                            description = description.replace("&#34;", "\"");
                            Log.d(TAG, "onResponse:description replace>>>> " + description);




                            topwebview.loadData(description, "text/html", "UTF-8");
                            mView.hideDialog();
                          //  reference_book_section();

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
                    params.put("book_solution_id", getIntent().getStringExtra("id_sol"));


                    Log.d(TAG, "getParams: " + params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(BookSectionActivity.this, strReq, tag_string_req);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    

}
