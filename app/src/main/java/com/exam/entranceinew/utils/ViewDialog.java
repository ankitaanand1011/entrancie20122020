package com.exam.entranceinew.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.exam.entranceinew.R;

import java.util.Objects;


public class ViewDialog {
    Activity activity;
    Dialog dialog;
    //..we need the context else we can not create the dialog so get context in constructor
    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);
        Glide.with(activity)
                .load(R.drawable.education_gif)
                .placeholder(R.drawable.education_gif)
           //     .centerCrop()
                .into(gifImageView);


        dialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public void hideDialog(){
        dialog.dismiss();
    }

}