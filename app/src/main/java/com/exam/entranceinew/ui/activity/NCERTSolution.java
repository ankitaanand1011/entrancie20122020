package com.exam.entranceinew.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exam.entranceinew.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NCERTSolution extends AppCompatActivity {
   CardView hindi,engish,maths,science;
    ImageView iv_back;
    private ArrayList<HashMap<String, String>> arr_ncert;
    TextView tv_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncert_solution);
        initialize_view();
    }

    private void initialize_view() {

        iv_back = findViewById(R.id.iv_back);
        tv_header = findViewById(R.id.tv_header);
        maths=findViewById(R.id.maths);
        science=findViewById(R.id.science);
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(NCERTSolution.this, NcertChapters.class);
                startActivity(i);
            }
        });



    }



}