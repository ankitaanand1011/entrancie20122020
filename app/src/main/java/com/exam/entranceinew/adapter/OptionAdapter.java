package com.exam.entranceinew.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exam.entranceinew.R;
import com.exam.entranceinew.utils.GlobalClass;
import com.exam.entranceinew.utils.ViewDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> option_list;

    public OptionAdapter(Context  context,
                         ArrayList<HashMap<String, String>> option_list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.option_list = option_list;
    }
    @Override
    public OptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.option_single_row, parent, false);
        return new OptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OptionAdapter.ViewHolder holder, final int position) {

        String options_value = option_list.get(position).get("options_value");
      //  holder.tv_option.setText(Html.fromHtml(options_value));
        assert options_value != null;
        if(options_value.contains("src")){
            holder.wv_option.setVisibility(View.VISIBLE);
            holder.tv_option.setVisibility(View.GONE);
           // holder.wv_option.setText(Html.fromHtml(options_value));

            holder.wv_option.loadData(options_value, "text/html", "UTF-8");
          /*  RequestOptions options = new RequestOptions()
                    // .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);


            Glide.with(context).load(Html.fromHtml(options_value)).apply(options).into(holder.iv_option);*/

        }else{
            holder.tv_option.setVisibility(View.VISIBLE);
            holder.wv_option.setVisibility(View.GONE);
            holder.tv_option.setText(Html.fromHtml(options_value));
        }


    }

    @Override
    public int getItemCount() {
        return option_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_option;
        ImageView iv_option;
        WebView wv_option;

        ViewHolder(View itemView) {
            super(itemView);

            tv_option = itemView.findViewById(R.id.tv_option);
            iv_option = itemView.findViewById(R.id.iv_option);
            wv_option = itemView.findViewById(R.id.wv_option);


        }
    }

}
