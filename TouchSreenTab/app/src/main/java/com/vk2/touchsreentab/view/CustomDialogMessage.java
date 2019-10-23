package com.vk2.touchsreentab.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vk2.touchsreentab.R;


public class CustomDialogMessage extends Dialog implements View.OnClickListener {
    private String title, message;
    private Button btnOK, btnCancel;
    private OnDialogMessageClick onDialogMessageClick;
    public CustomDialogMessage(Context context, String title, String message, OnDialogMessageClick onDialogMessageClick) {
        super(context);
        this.title = title;
        this.message = message;
        this.onDialogMessageClick = onDialogMessageClick;
    }
    public CustomDialogMessage(Context context, OnDialogMessageClick onDialogMessageClick){
        super(context);
        this.onDialogMessageClick = onDialogMessageClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_message);
        connectView();
    }

    private void connectView() {
        btnCancel = findViewById(R.id.btn_dialog_cancel);
        btnOK = findViewById(R.id.btn_dialog_ok);

        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dialog_cancel:{
                onDialogMessageClick.onButtonCancel();
                dismiss();
                break;
            }
            case R.id.btn_dialog_ok:{
                onDialogMessageClick.onButtonOK();
                dismiss();
                break;
            }
        }
    }

   public interface OnDialogMessageClick{
        void onButtonOK();
        void onButtonCancel();
    }
}
