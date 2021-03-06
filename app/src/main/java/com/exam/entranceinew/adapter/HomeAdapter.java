package com.exam.entranceinew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exam.entranceinew.CategoryData;
import com.exam.entranceinew.ui.activity.ncertsolution.NCERTSolution;
import com.exam.entranceinew.ui.activity.referencebook.BookSectionActivity;
import com.exam.entranceinew.ui.activity.referencebook.SectionListActivity;
import com.exam.entranceinew.ui.activity.studynotes.ChaptersScreen;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;

import com.exam.entranceinew.ui.activity.referencebook.ReferenceBookScreen;
import com.exam.entranceinew.ui.activity.studynotes.StudyNotesScreen;

import com.exam.entranceinew.ui.activity.onlinetest.TestActivity;
import com.github.siyamed.shapeimageview.RoundedImageView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    GlobalClass globalClass;
    ViewDialog mView;
    TextView tv_create_account;
    Shared_Preference shared_preference;
    String token;
    String pos = "0";
    String TAG= "adapterclass";
    ArrayList<Integer> colors_array;
    ArrayList<HashMap<String, String>> arr_api_param,arrayList;

    public HomeAdapter(Context c, ArrayList<HashMap<String, String>> arrayList,ArrayList<HashMap<String, String>> arr_api_param, ArrayList<Integer> colors_array) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.arrayList = arrayList;
        this.arr_api_param = arr_api_param;
        this.colors_array = colors_array;


       /* globalClass = (GlobalClass)context.getApplicationContext();
        shared_preference = new Shared_Preference(context);
        shared_preference.loadPrefrence();*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.home_row_item, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
     ;


     int colr= colors_array.get(position);

        Drawable myDrawable = ContextCompat.getDrawable(context,R.drawable.home_bg);
        myDrawable.setTint(ContextCompat.getColor(context, colr));




        RequestOptions options = new RequestOptions()
               // .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        switch (Objects.requireNonNull(arrayList.get(position).get("next_section_code"))) {

            case "ncert_solutions":{
                holder.tv_topic.setText(context.getResources().getString(R.string.ncert_solutions));
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_solution)).apply(options).into(holder.iv_image);
                break;
            }
            case "subject_chapters": {
                holder.tv_topic.setText(context.getResources().getString(R.string.subject_chapters));
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_notes)).apply(options).into(holder.iv_image);
                break;
            }
            case "reference_books": {
                holder.tv_topic.setText(context.getResources().getString(R.string.reference_books));
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_reference_book)).apply(options).into(holder.iv_image);
                break;
            }
            case "online_test": {
                holder.tv_topic.setText(context.getResources().getString(R.string.online_test));
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_test)).apply(options).into(holder.iv_image);
                break;
            }
        }



        holder.tv_name.setText(arrayList.get(position).get("name"));
        holder.tv_chapters.setText(arrayList.get(position).get("short_description"));
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Objects.requireNonNull(arrayList.get(position).get("next_section_code"))) {

                    case "ncert_solutions":{
                        Intent intent = new Intent(context, NCERTSolution.class);
                        intent.putExtra("name_val",arr_api_param.get(position).get("name_val"));
                        intent.putExtra("value",arr_api_param.get(position).get("value"));
                        intent.putExtra("name",arrayList.get(position).get("name"));
                        context.startActivity(intent);
                        break;
                    }
                    case "subject_chapters": {
                        Intent intent = new Intent(context, ChaptersScreen.class);
                        intent.putExtra("name_val",arr_api_param.get(position).get("name_val"));
                        intent.putExtra("value",arr_api_param.get(position).get("value"));
                        intent.putExtra("name",arrayList.get(position).get("name"));
                        context.startActivity(intent);
                        break;
                    }
                    case "reference_books": {
                        Intent intent = new Intent(context, SectionListActivity.class);
                        intent.putExtra("name_val",arr_api_param.get(position).get("name_val"));
                        intent.putExtra("value",arr_api_param.get(position).get("value"));
                        intent.putExtra("name",arrayList.get(position).get("name"));
                        context.startActivity(intent);
                        break;
                    }
                    case "Online Test": {
                        Intent intent = new Intent(context, TestActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                }
            }
        });




       /* String name = arr_category.get(position).get("name");
        holder.tv_class.setText(name);*/

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos=arr_category.get(position).get("id");
                holder.tv_class.setBackground(context.getResources().getDrawable(R.drawable.gradient_bg_noradius));
                holder.tv_class.setTextColor(context.getResources().getColor(R.color.white));

            }
        });

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: pos>>> "+pos);
                registration_2(pos);
            }
        });*/
    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_chapters,tv_topic;
        ImageView iv_image;
        RelativeLayout rl_bg;
        RoundedImageView roundedImageView;
        ViewHolder(View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            tv_chapters = itemView.findViewById(R.id.tv_chapters);
            tv_topic = itemView.findViewById(R.id.tv_topic);
            roundedImageView = itemView.findViewById(R.id.roundedImageView);

        }
    }




}