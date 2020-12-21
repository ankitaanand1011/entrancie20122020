package com.exam.entranceinew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exam.entranceinew.R;
import com.exam.entranceinew.ui.activity.studynotes.InstructionActivity;
import com.exam.entranceinew.ui.activity.studynotes.SubChaptersContent;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.utils.ViewDialog;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> arr_instruction;
    GlobalClass globalClass;
    ViewDialog mView;
    String pos = "0";
    String TAG= "adapternotes";
    public InstructionAdapter(Context  context,
                              ArrayList<HashMap<String, String>> arr_instruction) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.arr_instruction = arr_instruction;
    }
    @Override
    public InstructionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.instruction_single_row, parent, false);
        return new InstructionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InstructionAdapter.ViewHolder holder, final int position) {

        String instruction_value = arr_instruction.get(position).get("instruction_value");
        holder.tv_instruction.setText(instruction_value);


    }

    @Override
    public int getItemCount() {
        return arr_instruction.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_instruction;

        ViewHolder(View itemView) {
            super(itemView);


            tv_instruction = itemView.findViewById(R.id.tv_instruction);


        }
    }

}
