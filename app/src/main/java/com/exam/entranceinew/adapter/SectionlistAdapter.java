package com.exam.entranceinew.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exam.entranceinew.ui.activity.referencebook.BookSectionActivity;
import com.exam.entranceinew.ui.activity.referencebook.ReferenceBookScreen;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.ViewDialog;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SectionlistAdapter extends RecyclerView.Adapter<SectionlistAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String, String>> arr_list;
    GlobalClass globalClass;
    ViewDialog mView;
    String pos = "0";
    String TAG = "adapterlist";
    String book_id;

    public SectionlistAdapter(Context context,
                           ArrayList<HashMap<String, String>> arr_list,String book_id) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.arr_list = arr_list;
        this.book_id = book_id;
    }


    @NonNull
    @Override
    public SectionlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reference_book_row_item, parent, false);
        return new SectionlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String id = arr_list.get(position).get("ids");
        String title = arr_list.get(position).get("section_name");
        holder.tv_name.setText(title);


        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        holder.icon.setLetter(arr_list.get(position).get("section_name"));
        holder.icon.setLetterColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.darkblue));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(12);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   pos=arr_category.get(position).get("id");
                holder.tv_class.setBackground(context.getResources().getDrawable(R.drawable.gradient_bg_noradius));
                holder.tv_class.setTextColor(context.getResources().getColor(R.color.white));*/

                Intent intent = new Intent(context, ReferenceBookScreen.class);
                intent.putExtra("ids",arr_list.get(position).get("ids"));
                intent.putExtra("section_name",arr_list.get(position).get("section_name"));
                intent.putExtra("book_id",book_id);

                Log.d("qwerty", "onClick: id ref"+arr_list.get(position).get("id"));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arr_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        MaterialLetterIcon icon;
        RelativeLayout rl_icon;
        ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            icon = itemView.findViewById(R.id.icon);
            rl_icon = itemView.findViewById(R.id.rl_icon);
        }
    }


}