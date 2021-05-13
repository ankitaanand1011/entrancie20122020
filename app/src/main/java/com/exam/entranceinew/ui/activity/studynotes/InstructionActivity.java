package com.exam.entranceinew.ui.activity.studynotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exam.entranceinew.adapter.InstructionAdapter;
import com.exam.entranceinew.ui.activity.onlinetest.ExamActivity;
import com.exam.entranceinew.utils.ApplicationConstants;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructionActivity extends AppCompatActivity {

    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    ImageView iv_back;
    TextView tv_header,tv_exam_name;
    TextView tv_start_test;
    String TAG = "instruction";
    RecyclerView rv_instruction;
    InstructionAdapter instructionAdapter;
    private ArrayList<HashMap<String, String>> arr_instruction;
    String chapter_name,exercise_name,next_question_id,question_set_id,subchapter_id,chapter_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_instruction);

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
        tv_start_test = findViewById(R.id.tv_start_test);
        rv_instruction = findViewById(R.id.rv_instruction);
        tv_exam_name = findViewById(R.id.tv_exam_name);
    }

    private void function() {
        arr_instruction = new ArrayList<>();

        tv_header.setText("Instructions");
        tv_start_test.setText("Go to Exercise");

        instruction_list();
        tv_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionActivity.this, ExerciseActivity.class);
                intent.putExtra("next_question_id",next_question_id);
                intent.putExtra("question_set_id",question_set_id);
                Log.d(TAG, "onClick next_question_id: "+next_question_id);
                Log.d(TAG, "onClick question_set_id: "+question_set_id);
                startActivity(intent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void instruction_list() {
        mView.showDialog();
        final String tag_string_req = "instruction_list";
        String url = ApplicationConstants.notes_get_chapter_exercise_instruction;
        Log.d(TAG, "sub_chapter_list: url >>> "+url);
        JSONObject js = new JSONObject();
        try {

            js.put("request_key",  globalClass.getRequest_key());
            js.put("request_token", globalClass.getRequest_token());
            js.put("subchapter_id", getIntent().getStringExtra("id"));
            js.put("device", "mobile");

            Log.d(TAG, "instruction_list: js >  "+js.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jobj) {
                            Log.d(TAG, "instruction_list Response: " + jobj);

                            try {

                                String result =jobj.getString("result");
                                String message =jobj.getString("message");


                                Log.d(TAG, "Message: "+message);


                                if(result.equals("true")) {

                                    JSONObject data = jobj.getJSONObject("data");
                                    chapter_name =data.getString("chapter_name");
                                    exercise_name =data.getString("exercise_name");
                                    next_question_id =data.getString("next_question_id");
                                    question_set_id =data.getString("question_set_id");
                                    subchapter_id =data.getString("subchapter_id");
                                    chapter_id =data.getString("chapter_id");

                                    JSONArray instructions = data.getJSONArray("instructions");
                                    for( int i = 0 ; i < instructions.length() ; i++ ) {


                                        String instruction_value = instructions.getString(i);

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("instruction_value", instruction_value);

                                        arr_instruction.add(hashMap);
                                    }

                                    tv_exam_name.setText(chapter_name+"\n"+exercise_name);
                                    rv_instruction.setLayoutManager(new LinearLayoutManager(InstructionActivity.this, LinearLayoutManager.VERTICAL, false));
                                    instructionAdapter = new InstructionAdapter(InstructionActivity.this, arr_instruction);
                                    rv_instruction.setAdapter(instructionAdapter);

                                    instructionAdapter.notifyDataSetChanged();
                                    mView.hideDialog();



                                }else{
                                    mView.hideDialog();
                                    Toast.makeText(InstructionActivity.this, message, Toast.LENGTH_LONG).show();
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            //  mView.hideDialog();

                        }
                    }, new Response.ErrorListener() {

                @Override

                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "instruction_list Error: " + error.getMessage());
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

            globalClass.addToRequestQueue(InstructionActivity.this, jsonObjReq, tag_string_req);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
