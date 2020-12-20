package com.exam.entranceinew.adapter;

import android.content.Context;
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
import com.exam.entranceinew.ui.activity.NcertChapters;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SolutionsChapterAdapter extends RecyclerView.Adapter<SolutionsChapterAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> arr_study;
   // List<String> ncert_arr;
    GlobalClass globalClass;
    ViewDialog mView;
    String pos = "0";
    String TAG= "sol_adapter";

    public SolutionsChapterAdapter(Context  context, ArrayList<HashMap<String,String>> arr_study) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
     //   this.arr_study = arr_study;
        this.arr_study = arr_study;

    }
    @Override
    public SolutionsChapterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.solutions_row_item, parent, false);
        return new SolutionsChapterAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(final SolutionsChapterAdapter.ViewHolder holder, final int position) {

        String name = arr_study.get(position).get("name");
      //  String name = arr_study.get(position);
        holder.tv_name.setText(name);

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        holder.icon.setLetter(arr_study.get(position).get("name"));
      //  holder.icon.setLetter(arr_study.get(position));
        holder.icon.setLetterColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.darkpurple));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(12);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(1);

       // holder.rl_icon.setBackgroundColor(randomAndroidColor);



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
            icon = itemView.findViewById(R.id.icon);
            rl_icon = itemView.findViewById(R.id.rl_icon);

        }
    }

}
