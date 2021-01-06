package com.exam.entranceinew.ui.activity.studynotes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.R;
import com.exam.entranceinew.adapter.InstructionAdapter;
import com.exam.entranceinew.adapter.OptionAdapter;
import com.exam.entranceinew.adapter.QuestionAdapter;
import com.exam.entranceinew.ui.activity.onlinetest.ReportActivity;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseActivity extends AppCompatActivity  {
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    ImageView iv_back;
    RecyclerView rv_options;
    TextView tv_show_answer,tv_solution,tv_question,tv_answer_description;
    ImageView iv_previous,iv_next;
    LinearLayout ll_answer;
    String question_set_id,question_id;
    String TAG = "exercise";
    private ArrayList<HashMap<String, String>> arr_option;
    OptionAdapter optionAdapter;
    String previous_question_id, next_question_id;
    WebView wv_answer,wv_question,wv_answer_description;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        initialize_view();
        function();
    }

    private void initialize_view() {
        globalClass = (GlobalClass)getApplicationContext();
        shared_preference = new Shared_Preference(this);
        shared_preference.loadPrefrence();
        mView = new ViewDialog(this);
        iv_back = findViewById(R.id.iv_back);


        iv_previous = findViewById(R.id.iv_previous);
        iv_next = findViewById(R.id.iv_next);
        tv_show_answer = findViewById(R.id.tv_show_answer);
        ll_answer = findViewById(R.id.ll_answer);
        wv_answer = findViewById(R.id.wv_answer);
        wv_question = findViewById(R.id.wv_question);
        rv_options = findViewById(R.id.rv_options);
        tv_solution = findViewById(R.id.tv_solution);   
        wv_answer_description = findViewById(R.id.wv_answer_description);


    }

    private void function() {

        question_id = getIntent().getStringExtra("next_question_id");
        question_set_id = getIntent().getStringExtra("question_set_id");

        arr_option = new ArrayList<>();
        exercise_question(question_id);


        tv_show_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_answer.getVisibility() == View.VISIBLE){
                    ll_answer.setVisibility(View.GONE);
                    tv_show_answer.setText("Show Answer");
                } else {
                    ll_answer.setVisibility(View.VISIBLE);
                    tv_show_answer.setText("Hide Answer");
                }
            }
        });


        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!previous_question_id.equals("null")) {
                    exercise_question(previous_question_id);
                }
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!next_question_id.equals("null")) {
                    exercise_question(next_question_id);
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void exercise_question(String question_id) {
        mView.showDialog();
        final String tag_string_req = "exercise_question";
        String url = ApplicationConstants.notes_get_chapter_exercise_question;
        Log.d(TAG, "exercise_question: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("question_set_id", question_set_id);
            js.put("question_id", question_id);
            js.put("device", "mobile");

            Log.d(TAG, "exercise_question: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "exercise_question Response: " + jobj);
                            arr_option.clear();
                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                    JSONObject data = jobj.getJSONObject("data");
                                    String question =data.getString("question");
                                    String answer =data.getString("answer");
                                    String answer_description =data.getString("answer_description");
                                     previous_question_id =data.getString("previous_question_id");
                                     next_question_id =data.getString("next_question_id");

                                    JSONArray options = data.getJSONArray("options");
                                    for( int i = 0 ; i < options.length() ; i++ ) {


                                        String options_value = options.getString(i);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("options_value", options_value);


                                        arr_option.add(hashMap);
                                    }

                                    wv_question.loadData(question, "text/html", "UTF-8");
                                    wv_answer.loadData(answer, "text/html", "UTF-8");
                                    if(!answer_description.isEmpty()){
                                        tv_solution.setVisibility(View.VISIBLE);
                                        wv_answer_description.setVisibility(View.VISIBLE);
                                        wv_answer_description.loadData(answer_description, "text/html", "UTF-8");
                                    }else{

                                        tv_solution.setVisibility(View.GONE);
                                        wv_answer_description.setVisibility(View.GONE);
                                    }


                                    rv_options.setLayoutManager(new LinearLayoutManager(ExerciseActivity.this, LinearLayoutManager.VERTICAL, false));
                                    optionAdapter = new OptionAdapter(ExerciseActivity.this, arr_option);
                                    rv_options.setAdapter(optionAdapter);

                                    optionAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(ExerciseActivity.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "exercise_question Error: " + error.getMessage());
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

            globalClass.addToRequestQueue(ExerciseActivity.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
