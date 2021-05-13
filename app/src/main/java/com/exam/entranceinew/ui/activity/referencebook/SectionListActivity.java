package com.exam.entranceinew.ui.activity.referencebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.adapter.ExpandableListAdapter;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.ExpandableTextView;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.exam.entranceinew.adapter.SectionlistAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionListActivity extends AppCompatActivity {
    String TAG = "Section_list";
    RecyclerView rvSectionList;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_list;
    private ArrayList<HashMap<String, String>> arr_section_list;
    private List<String> arr_section_header;
    private List<String> arr_sections;
    List<String> section;

    HashMap<String, List<String>> listDataChild;
    SectionlistAdapter SectionlistAdapter;
    ImageView iv_back;
    TextView tv_header;
    ExpandableListView expListView;
    ExpandableTextView ex_tv,ex_tv_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_refrence_book);

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
        rvSectionList=findViewById(R.id.rvSectionList);
        expListView=findViewById(R.id.expListView);
        ex_tv = findViewById(R.id.ex_tv);
      //  ex_tv_bottom = findViewById(R.id.ex_tv_bottom);
        rvSectionList.setLayoutManager(new LinearLayoutManager(SectionListActivity.this, LinearLayoutManager.VERTICAL, false));
    }
    private void function() {
        tv_header.setText(getIntent().getStringExtra("name"));
        arr_list = new ArrayList<>();
        arr_sections = new ArrayList<>();
        arr_section_header = new ArrayList<>();
        arr_section_list = new ArrayList<>();
        section = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        SectionList();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void SectionList() {


        Log.d("qwerty", "SectionList: id "+getIntent().getStringExtra("id"));
        mView.showDialog();
        final String tag_string_req = "reference_book";
        String url = ApplicationConstants.books_section_description;
        Log.d(TAG, "book_list: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("book_id",getIntent().getStringExtra("value"));
            js.put("device", "mobile");

            Log.d(TAG, "reference_book: js >  "+js.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "Book list: " + jobj);

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
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("ids", ids);
                                        hashMap.put("section_name", section_name);

                                     //   arr_section_header.add(section_name);

                                        arr_section_list.add(hashMap);

                                   /*     JSONArray solutions = obj_data.getJSONArray("solutions");
                                        Log.d(TAG, "onResponse: sol"+solutions);
                                        for( int j = 0 ; j < solutions.length() ; j++ ) {
                                            JSONObject obj_solution = solutions.getJSONObject(j);

                                            String id_sol = obj_solution.getString("id");
                                            String title = obj_solution.getString("title");


                                            section.add(title);

                                            Log.d(TAG, "onResponse:id>>> " + ids);
                                            Log.d(TAG, "onResponse:section_name>>> " + ids);
                                       //     arr_sections.add(title);



                                        }
                                        listDataChild.put(arr_section_header.get(i), section);*/
                                    }

                                    ex_tv.setText(Html.fromHtml(top_description)+"\n"+Html.fromHtml(bottom_description));
                                  //  ex_tv_bottom.setText(Html.fromHtml(bottom_description));

                                   /* ExpandableListAdapter listAdapter = new ExpandableListAdapter(SectionListActivity.this, arr_section_header, listDataChild);

                                    // setting list adapter
                                    expListView.setAdapter(listAdapter);*/

                                    SectionlistAdapter = new SectionlistAdapter(SectionListActivity.this, arr_section_list,getIntent().getStringExtra("value"));
                                    rvSectionList.setAdapter(SectionlistAdapter);

                                    SectionlistAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(SectionListActivity.this, message, Toast.LENGTH_LONG).show();
                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            //  mView.hideDialog();
                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "reference_list Error: " + error.getMessage());
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

            globalClass.addToRequestQueue(SectionListActivity.this, jsonObjReq, tag_string_req);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}