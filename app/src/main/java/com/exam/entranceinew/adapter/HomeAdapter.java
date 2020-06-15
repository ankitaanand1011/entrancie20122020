package com.exam.entranceinew.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exam.entranceinew.ApplicationConstants;
import com.exam.entranceinew.CategoryData;
import com.exam.entranceinew.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.Shared_Preference;
import com.exam.entranceinew.ViewDialog;
import com.exam.entranceinew.ui.activity.ExamActivity;
import com.exam.entranceinew.ui.activity.MainActivity;
import com.exam.entranceinew.ui.activity.ReferenceBookScreen;
import com.exam.entranceinew.ui.activity.StudyNotesScreen;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<CategoryData> arrayList;
    GlobalClass globalClass;
    ViewDialog mView;
    TextView tv_create_account;
    Shared_Preference shared_preference;
    String token;
    String pos = "0";
    String TAG= "adapterclass";
    ArrayList<Integer> colorslist;

    public HomeAdapter(Context c, ArrayList<CategoryData> arrayList) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.arrayList = arrayList;
        this.tv_create_account = tv_create_account;
        this.mView = mView;
        this.token = token;

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
        final CategoryData categoryData = arrayList.get(position);

      /*  String  androidColors[] = context.getResources().getStringArray(R.array.androidcolors1);
        colorslist = new ArrayList<Integer>();*/



        //  int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
    //    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
    //    int[] colors = context.getResources().getIntArray(R.array.colors);


/*
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.home_bg);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable,Color.RED);*/

        Drawable myDrawable = ContextCompat.getDrawable(context,R.drawable.home_bg);
        myDrawable.setTint(ContextCompat.getColor(context, categoryData.color));


     /*   GradientDrawable drawable = (GradientDrawable) context.getResources()
                .getDrawable(R.drawable.home_bg);
        drawable.mutate();
        drawable.setColor(categoryData.colors);*/

      //  holder.rl_bg.setBackgroundColor(categoryData.colors);

        RequestOptions options = new RequestOptions()
               // .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);


        Glide.with(context).load(categoryData.image).apply(options).into(holder.iv_image);


        holder.tv_name.setText(categoryData.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (categoryData.name) {
                    case "Study Notes": {
                        Intent intent = new Intent(context, StudyNotesScreen.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "Reference Books": {
                        Intent intent = new Intent(context, ReferenceBookScreen.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "Online Test": {
                        Intent intent = new Intent(context, ExamActivity.class);
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
        TextView tv_name;
        ImageView iv_image;
        RelativeLayout rl_bg;
        ViewHolder(View itemView) {
            super(itemView);

            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            rl_bg = itemView.findViewById(R.id.rl_bg);

        }
    }




}