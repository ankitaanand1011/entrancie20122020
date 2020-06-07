package com.exam.entranceinew.ui.activity;

import android.content.Intent;
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
import com.exam.entranceinew.adapter.SubChaptersAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubChaptersContent extends AppCompatActivity {
    String TAG = "sub_content";
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    ImageView iv_back;
    TextView tv_header;
    HtmlTextView htmlTextView ;
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_chapter_content);
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
        webview = findViewById(R.id.webview);
        htmlTextView = findViewById(R.id.html_text);
    }
    private void function() {

        tv_header.setText(getIntent().getStringExtra("name"));
        chapter_content();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void chapter_content() {
        mView.showDialog();
        final String tag_string_req = "chapter_content";
        String url = ApplicationConstants.chapter_content;
        Log.d(TAG, "chapter_content: url>>>> " + url);
        try {
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "chapter_content Response: " + response);


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
                            String type = data.get("type").getAsString().replaceAll("\"", "");
                            String question_set_id = data.get("question_set_id").getAsString().replaceAll("\"", "");
                            String description = data.get("description").getAsString().replaceAll("\"", "");

                            Log.d(TAG, "onResponse:description>>>> " + description);
                            Log.d(TAG, "onResponse:id>>> " + id);
                            Log.d(TAG, "onResponse:name>>> " + name);
                            description = description.replace("&#39;", "\'");
                            description = description.replace("&#34;", "\"");
                            Log.d(TAG, "onResponse:description replace>>>> " + description);



                            htmlTextView.setHtml(description);
                            webview.loadData(description, "text/html", "UTF-8");
                            mView.hideDialog();


                        } else {
                            mView.hideDialog();
                            Toast.makeText(SubChaptersContent.this, message, Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "chapter_content Error: " + error.getMessage());
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
                    params.put("subchapter_id", getIntent().getStringExtra("id"));


                    Log.d(TAG, "getParams: " + params);
                    return params;
                }

            };

            globalClass.addToRequestQueue(SubChaptersContent.this, strReq, tag_string_req);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
