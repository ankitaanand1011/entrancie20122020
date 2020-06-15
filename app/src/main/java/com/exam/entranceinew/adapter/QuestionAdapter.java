package com.exam.entranceinew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.ViewDialog;
import com.exam.entranceinew.ui.activity.BookSectionActivity;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    Context context;
    List<Integer> arr_study;
    GlobalClass globalClass;
    ViewDialog mView;
    String pos = "0";
    String TAG= "adapternotes";
    public QuestionAdapter(Context  context,
                           List<Integer> arr_study) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.arr_study = arr_study;
    }
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.question_grid, parent, false);
        return new QuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.ViewHolder holder, final int position) {

        int name = arr_study.get(position);
        holder.tv_name.setText(String.valueOf(name));
/*
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        holder.icon.setLetter(arr_study.get(position).get("name"));
        holder.icon.setLetterColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.darkgreen));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(12);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(1);

       // holder.rl_icon.setBackgroundColor(randomAndroidColor);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   pos=arr_category.get(position).get("id");
                holder.tv_class.setBackground(context.getResources().getDrawable(R.drawable.gradient_bg_noradius));
                holder.tv_class.setTextColor(context.getResources().getColor(R.color.white));*//*

                Intent intent = new Intent(context, BookSectionActivity.class);
                intent.putExtra("id",arr_study.get(position).get("id"));
                intent.putExtra("name",arr_study.get(position).get("name"));
                context.startActivity(intent);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return arr_study.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        MaterialLetterIcon icon;
        RelativeLayout rl_icon;
        ViewHolder(View itemView) {
            super(itemView);

            //     ivProduct = itemView.findViewById(R.id.ivProduct);
            tv_name = itemView.findViewById(R.id.tv_name);
      /*      icon = itemView.findViewById(R.id.icon);
            rl_icon = itemView.findViewById(R.id.rl_icon);*/

        }
    }

}
