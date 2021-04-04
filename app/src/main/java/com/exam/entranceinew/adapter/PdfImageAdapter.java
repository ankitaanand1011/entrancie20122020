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
import com.exam.entranceinew.R;
import com.exam.entranceinew.ui.activity.ncertsolution.NCERTSolution;
import com.exam.entranceinew.ui.activity.onlinetest.TestActivity;
import com.exam.entranceinew.ui.activity.referencebook.SectionListActivity;
import com.exam.entranceinew.ui.activity.studynotes.ChaptersScreen;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.utils.Shared_Preference;
import com.exam.entranceinew.utils.ViewDialog;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class PdfImageAdapter extends RecyclerView.Adapter<PdfImageAdapter.ViewHolder> {

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

    public PdfImageAdapter(Context c, ArrayList<HashMap<String, String>> arrayList) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.arrayList = arrayList;


       /* globalClass = (GlobalClass)context.getApplicationContext();
        shared_preference = new Shared_Preference(context);
        shared_preference.loadPrefrence();*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.pdf_row, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
     ;


 //    int colr= colors_array.get(position);

      /*  Drawable myDrawable = ContextCompat.getDrawable(context,R.drawable.home_bg);
        myDrawable.setTint(ContextCompat.getColor(context, colr));*/




        RequestOptions options = new RequestOptions()
               .centerInside()
                .placeholder(R.drawable.no_image_pic)
                .error(R.drawable.no_image_pic);
        Glide.with(context).load(arrayList.get(position).get("images_link")).apply(options).into(holder.iv_image);


       /* switch (arrayList.get(position).get("next_section_code")) {

            case "NCERT Solutions":{
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_sample_paper)).apply(options).into(holder.iv_image);
                break;
            }
            case "subject_chapters": {
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_notes)).apply(options).into(holder.iv_image);
                break;
            }
            case "reference_books": {
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_reference_book)).apply(options).into(holder.iv_image);
                break;
            }
            case "Online Test": {
                Glide.with(context).load(context.getResources().getDrawable(R.mipmap.ic_test)).apply(options).into(holder.iv_image);
                break;
            }
        }*/


      //  Glide.with(context).load(arrayList.get(position).get("image")).apply(options).into(holder.iv_image);


     /*   holder.tv_name.setText(arrayList.get(position).get("name"));
        holder.tv_chapters.setText(arrayList.get(position).get("short_description"));
        holder.tv_topic.setText(arrayList.get(position).get("next_section_code"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (arrayList.get(position).get("next_section_code")) {

                    case "NCERT Solutions":{
                        Intent intent = new Intent(context, NCERTSolution.class);
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
*/



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
          /*  tv_name = itemView.findViewById(R.id.tv_name);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            tv_chapters = itemView.findViewById(R.id.tv_chapters);
            tv_topic = itemView.findViewById(R.id.tv_topic);
            roundedImageView = itemView.findViewById(R.id.roundedImageView);*/

        }
    }




}