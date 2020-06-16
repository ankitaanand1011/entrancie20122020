package com.exam.entranceinew.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.ViewDialog;
import com.exam.entranceinew.adapter.SolutionsAdapter;
import com.exam.entranceinew.adapter.SolutionsChapterAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NcertChapters extends AppCompatActivity {
    String TAG = "NCERTSolution";
    RecyclerView rvNcertSol;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ViewDialog mView;
    private ArrayList<HashMap<String, String>> arr_ncert;
    TextView tv_header;
    ImageView iv_back;
    List<String> ncert_chapter_arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncert_chapters);
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
    }

}