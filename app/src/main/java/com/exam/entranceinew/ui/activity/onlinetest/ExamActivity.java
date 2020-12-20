package com.exam.entranceinew.ui.activity.onlinetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.entranceinew.R;
import com.exam.entranceinew.adapter.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    ImageView iv_layout_right,ivCloseDrawer;
    CardView llRightDrawer;
    RecyclerView rvQuestionGrid;
    QuestionAdapter questionAdapter;
    List<Integer> question_list;
    TextView tv_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        initialize_view();
        function();
    }

    private void initialize_view() {

        iv_layout_right = findViewById(R.id.iv_layout_right);
        llRightDrawer = findViewById(R.id.llRightDrawer);
        ivCloseDrawer = findViewById(R.id.ivCloseDrawer);
        rvQuestionGrid = findViewById(R.id.rvQuestionGrid);
        tv_submit = findViewById(R.id.tv_submit);


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

        llRightDrawer.setVisibility(View.GONE);

        rvQuestionGrid.setLayoutManager(new GridLayoutManager(ExamActivity.this, 4));
        questionAdapter = new QuestionAdapter(ExamActivity.this, question_list);
        rvQuestionGrid.setAdapter(questionAdapter);






        iv_layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation RightSwipe = AnimationUtils.loadAnimation(ExamActivity.this, R.anim.anim_slide_in_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.VISIBLE);
            }
        });

        ivCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation RightSwipe = AnimationUtils.loadAnimation(ExamActivity.this, R.anim.anim_slide_out_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.GONE);
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Animation RightSwipe = AnimationUtils.loadAnimation(ExamActivity.this, R.anim.anim_slide_out_right);
                llRightDrawer.startAnimation(RightSwipe);
                llRightDrawer.setVisibility(View.GONE);*/

                Intent intent = new Intent(ExamActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });



    }
}
