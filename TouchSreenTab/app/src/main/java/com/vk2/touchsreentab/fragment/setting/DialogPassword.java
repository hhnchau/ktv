package com.vk2.touchsreentab.fragment.setting;

import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Handler;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.databinding.DialogPasswordBinding;
import com.vk2.touchsreentab.view.MyNumPad;


public class DialogPassword {

    public void build(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final DialogPasswordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_password, null, false);
        dialog.setContentView(binding.getRoot());
        Window window = dialog.getWindow();

        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            dialog.show();

            binding.clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Editable s = binding.input.getText();
                            if (s != null && !s.toString().isEmpty()) {
                                String str = s.toString().substring(0, s.length() - 1);
                                binding.input.setText(str);
                            }
                        }
                    });
                }
            });

            binding.boxButton.setListener(new MyNumPad.OnNumPadListener() {
                @Override
                public void onNumber(final String key) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            binding.input.append(key);
                        }
                    });
                }

                @Override
                public void onButton(boolean yes) {
                    if (yes){

                    }else {

                    }
                }
            });


        }
    }
}
