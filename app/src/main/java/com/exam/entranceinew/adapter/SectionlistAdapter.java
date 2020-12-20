package com.exam.entranceinew.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public SectionlistAdapter(Context context,
                           ArrayList<HashMap<String, String>> arr_list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.arr_list = arr_list;
    }


    @NonNull
    @Override
    public SectionlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reference_book_row_item, parent, false);
        return new SectionlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = arr_list.get(position).get("id_sol");
        String title = arr_list.get(position).get("title");
        holder.tv_name.setText(title);


        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        holder.icon.setLetter(arr_list.get(position).get("title"));
        holder.icon.setLetterColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.darkblue));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(12);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(1);


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