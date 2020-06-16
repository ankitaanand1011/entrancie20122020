package com.exam.entranceinew.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.ViewDialog;
import com.exam.entranceinew.adapter.SolutionsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NCERTSolution extends AppCompatActivity {
    String TAG = "NCERTSolution";
    RecyclerView rvNcertSol;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_ncert;
    TextView tv_header;
    ImageView iv_back;
    List<String> ncert_arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncert_solution);
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
        rvNcertSol = findViewById(R.id.rvNcertSol);
        rvNcertSol.setNestedScrollingEnabled(false);
    }
    private void function() {

        tv_header.setText("NCERT Solutions");
        ncert_arr = new ArrayList<>();
       // notes_list();

        ncert_arr.add("NCERT Solutions for class 6");
        ncert_arr.add("NCERT Solutions for class 7");
        ncert_arr.add("NCERT Solutions for class 8");
        ncert_arr.add("NCERT Solutions for class 9");
        ncert_arr.add("NCERT Solutions for class 10");
        ncert_arr.add("NCERT Solutions for class 11");
        ncert_arr.add("NCERT Solutions for class 12");


        rvNcertSol.setLayoutManager(new LinearLayoutManager(NCERTSolution.this, LinearLayoutManager.VERTICAL, false));
        SolutionsAdapter solutionsAdapter = new SolutionsAdapter(NCERTSolution.this,ncert_arr);
        rvNcertSol.setAdapter(solutionsAdapter);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}