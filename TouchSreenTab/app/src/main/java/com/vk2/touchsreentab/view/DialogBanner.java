package com.vk2.touchsreentab.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vk2.touchsreentab.R;

public class DialogBanner {
    public static class Builder {
        private Context context;
        private int res;
        private String url;
        private View.OnClickListener listener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setResource(int res) {
            this.res = res;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setListener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public void build() {
            final Dialog dialog = new Dialog(context, R.style.MyDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_banner);
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
            }
            ImageView img = dialog.findViewById(R.id.img);

            if (url != null)
                Glide.with(img.getContext()).load(url).into(img);
            else
                Glide.with(img.getContext()).load(res).into(img);

            TextView btn = dialog.findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) listener.onClick(v);
                    dialog.dismiss();
                }
            });
        }
    }
}