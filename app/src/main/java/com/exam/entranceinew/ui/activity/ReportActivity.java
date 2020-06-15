package com.exam.entranceinew.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.entranceinew.R;
import com.exam.entranceinew.adapter.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    RecyclerView rvQuestionGrid;
    QuestionAdapter questionAdapter;
    List<Integer> question_list;
    ImageView ivCloseDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initialize_view();
        function();


    }

    private void initialize_view() {
        rvQuestionGrid = findViewById(R.id.rvQuestionGrid);
        ivCloseDrawer = findViewById(R.id.ivCloseDrawer);

    }

    private void function() {
        question_list = new ArrayList<>();

        question_list.add(1);
        question_list.add(2);
        question_list.add(3);
        question_list.add(4);
        question_list.add(5);
        question_list.add(6);
        question_list.add(7);
        question_list.add(8);
        question_list.add(9);
        question_list.add(10);
        question_list.add(11);
        question_list.add(12);
        question_list.add(13);
        question_list.add(14);
        question_list.add(15);
        question_list.add(16);
        question_list.add(17);
        question_list.add(18);
        question_list.add(19);
        question_list.add(20);
        question_list.add(21);
        question_list.add(22);
        question_list.add(23);
        question_list.add(24);
        question_list.add(25);


        rvQuestionGrid.setLayoutManager(new GridLayoutManager(ReportActivity.this, 5));
        questionAdapter = new QuestionAdapter(ReportActivity.this, question_list);
        rvQuestionGrid.setAdapter(questionAdapter);
        rvQuestionGrid.setNestedScrollingEnabled(false);

        ivCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
