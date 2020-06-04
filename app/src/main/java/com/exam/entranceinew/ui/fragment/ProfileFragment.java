package com.exam.entranceinew.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exam.entranceinew.R;
import com.exam.entranceinew.ui.activity.ChangePasswordScreen;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    String TAG ="profile";
    LinearLayout ll_qty;
    TextView qty_spinner,tv_cancel;
    RelativeLayout rl_password;
    ImageView iv_edit;
    LinearLayout ll_show_profile,ll_edit_profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialize_view(view);
        functions();


        return view;

    }

    private void initialize_view(View view) {
        ll_qty = view.findViewById(R.id.ll_qty);
        qty_spinner = view.findViewById(R.id.qty_spinner);
        rl_password = view.findViewById(R.id.rl_password);
        iv_edit = view.findViewById(R.id.iv_edit);
        ll_show_profile = view.findViewById(R.id.ll_show_profile);
        ll_edit_profile = view.findViewById(R.id.ll_edit_profile);
        tv_cancel = view.findViewById(R.id.tv_cancel);
    }

    private void functions() {
        ll_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty_dialog();
            }
        });

        rl_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(getActivity(), ChangePasswordScreen.class);
                startActivity(intent);*/

                dialog_change_password();
            }
        });

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_edit_profile.setVisibility(View.VISIBLE);
                ll_show_profile.setVisibility(View.GONE);
                iv_edit.setImageResource(R.drawable.edit_active);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_edit_profile.setVisibility(View.GONE);
                ll_show_profile.setVisibility(View.VISIBLE);
                iv_edit.setImageResource(R.drawable.edit);
            }
        });


    }

    private void qty_dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Class");
        final String[] qty_arr = getResources().getStringArray(R.array.class_array);
        builder.setItems(qty_arr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                String selected_item= qty_arr[item];
                Log.d(TAG, "onClick:selected_item>> "+selected_item);
                String[] separated = selected_item.split(" ");
                String str1 = separated[0];
                String str2 = separated[1];
                qty_spinner.setText(str2);

               // update_cart_qty(holder,sku,quote_id,item_id,selected_item);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialog_change_password() {

        final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_change_password);

        final EditText edtOldPwd =  dialog.findViewById(R.id.edtOldPwd);
        final EditText edtNewPwd =  dialog.findViewById(R.id.edtNewPwd);
        final EditText edtConfirmPwd =  dialog.findViewById(R.id.edtConfirmPwd);
        TextView tv_ChangePassword = dialog.findViewById(R.id.tv_ChangePassword);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);



        tv_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String old_pass = edtOldPwd.getText().toString();
                String new_pass = edtNewPwd.getText().toString();
                String confirm_pass = edtConfirmPwd.getText().toString();
                if(!old_pass.isEmpty() ){
                    if (!new_pass.isEmpty()){
                        if(confirm_pass.matches(new_pass)){
                           // update_password(old_pass,new_pass,dialog);
                        }else{
                            Toast.makeText(getActivity(),"Password mismatch",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getActivity(),"Please enter your new password",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Please enter your old password",Toast.LENGTH_LONG).show();
                }


            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

}
