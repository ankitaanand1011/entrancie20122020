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

import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.ViewDialog;

import com.exam.entranceinew.ui.activity.ncertsolution.NcertChapters;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SolutionsAdapter extends RecyclerView.Adapter<SolutionsAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> ncert_arr;
   // List<String> ncert_arr;
    GlobalClass globalClass;
    ViewDialog mView;
    String pos = "0";
    String TAG= "sol_adapter";
    public SolutionsAdapter(Context  context, ArrayList<HashMap<String,String>> ncert_arr) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
     //   this.arr_study = arr_study;
        this.ncert_arr = ncert_arr;
    }
    @Override
    public SolutionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.solutions_row_item, parent, false);
        return new SolutionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SolutionsAdapter.ViewHolder holder, final int position) {

       // String name = arr_study.get(position).get("name");
        String name = ncert_arr.get(position).get("name_val");


      ///  String[] arrayString = name.split("NCERT Solutions Class-7 Maths");

       // String email = arrayString[0];
       // String title = arrayString[0];
//        String body = arrayString[1];


        holder.tv_name.setText(name);

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

       // holder.icon.setLetter(arr_study.get(position).get("name"));
        holder.icon.setLetter(name);
        holder.icon.setLetterColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.darkpurple));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(12);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(1);

       // holder.rl_icon.setBackgroundColor(randomAndroidColor);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   pos=arr_category.get(position).get("id");
                holder.tv_class.setBackground(context.getResources().getDrawable(R.drawable.gradient_bg_noradius));
                holder.tv_class.setTextColor(context.getResources().getColor(R.color.white));*/

                Intent intent = new Intent(context, NcertChapters.class);
                intent.putExtra("id",ncert_arr.get(position).get("id"));
                intent.putExtra("name",ncert_arr.get(position).get("name"));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ncert_arr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        MaterialLetterIcon icon;
        RelativeLayout rl_icon;
        ViewHolder(View itemView) {
            super(itemView);

            //     ivProduct = itemView.findViewById(R.id.ivProduct);
            tv_name = itemView.findViewById(R.id.tv_name);
            icon = itemView.findViewById(R.id.icon);
            rl_icon = itemView.findViewById(R.id.rl_icon);

        }
    }

}
