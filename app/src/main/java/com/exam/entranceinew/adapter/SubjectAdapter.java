package com.exam.entranceinew.adapter;

import android.content.Context;
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
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.SubjectData;
import com.exam.entranceinew.utils.ViewDialog;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<SubjectData> arrayList;
    GlobalClass globalClass;
    ViewDialog mView;
    TextView tv_create_account;
    Shared_Preference shared_preference;
    String token;
    String pos = "0";
    String TAG= "adapterclass";
    ArrayList<Integer> colorslist;

    public SubjectAdapter(Context c, ArrayList<SubjectData> arrayList) {
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
            View view = mInflater.inflate(R.layout.test_grid_row_horizontal, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final  SubjectData subjectData = arrayList.get(position);


        Drawable myDrawable = ContextCompat.getDrawable(context,R.drawable.circle_bg);

        switch (subjectData.name) {
            case "Latest":

                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkpink));
                //myDrawable.setTint(ContextCompat.getColor(context, R.color.darkpink));
                break;
            case "Maths":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkred));
              //  myDrawable.setTint(ContextCompat.getColor(context,  R.color.darkred));
                break;
            case "Science":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkgreen));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.darkgreen));
                break;
            case "Biology":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkpurple));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.darkpurple));
                break;
            case "Chemistry":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkorange));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.darkorange));
                break;
            case "Physics":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_blue));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.blue));
                break;
            case "Reasoning":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkred));
              //  myDrawable.setTint(ContextCompat.getColor(context, R.color.darkred));
                break;
            case "Civics":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkpink));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.darkpink));
                break;
            case "Economics":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkgreen));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.darkgreen));
                break;
            case "Geography":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkpurple));
              //  myDrawable.setTint(ContextCompat.getColor(context, R.color.darkpurple));
                break;
            case "History":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkorange));
             //   myDrawable.setTint(ContextCompat.getColor(context, R.color.darkorange));
                break;
            case "Botany":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_blue));
               // myDrawable.setTint(ContextCompat.getColor(context, R.color.blue));
                break;
            case "Zoology":
                holder.rl_bg.setBackground(context.getResources().getDrawable(R.drawable.circle_bg_darkpink));
               // myDrawable.setTint(ContextCompat.getColor(context,  R.color.darkpink));
                break;
        }




        RequestOptions options = new RequestOptions()
               // .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);


        Glide.with(context).load(subjectData.image).apply(options).into(holder.iv_image);


        holder.tv_name.setText(subjectData.name);
  /*      holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (subjectData.name) {
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
*/



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