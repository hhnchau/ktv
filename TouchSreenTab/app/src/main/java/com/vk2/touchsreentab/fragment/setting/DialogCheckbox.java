package com.vk2.touchsreentab.fragment.setting;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.CheckboxViewHolder;
import com.vk2.touchsreentab.databinding.DialogCheckboxBinding;
import com.vk2.touchsreentab.databinding.ItemCheckboxBinding;
import com.vk2.touchsreentab.model.setting.Checkbox;

import java.util.List;

public abstract class DialogCheckbox {

    public abstract void onListener(String value);

    public void build(Context context, final List<Checkbox> data) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogCheckboxBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_checkbox, null, false);
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

            SettingAdapter<CheckboxViewHolder> adapter = new SettingAdapter<CheckboxViewHolder>() {
                @NonNull
                @Override
                public CheckboxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    ItemCheckboxBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_checkbox, viewGroup, false);
                    return new CheckboxViewHolder(binding);
                }

                @Override
                public void onBindViewHolder(@NonNull CheckboxViewHolder holder, int i) {
                    final Checkbox checkbox = data.get(holder.getAdapterPosition());
                    if (checkbox != null) {
                        holder.checkboxBinding.setCheckbox(checkbox);
                        holder.checkboxBinding.root.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                removeChecked(data);
                                checkbox.checked.set(true);
                            }
                        });
                    }
                }

                @Override
                public int getItemCount() {
                    return data.size();
                }
            };

            binding.rcv.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.rcv.setLayoutManager(layoutManager);
            binding.rcv.addItemDecoration(getDivider(context, layoutManager));


            binding.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener("s");
                    dialog.dismiss();
                }
            });

        }
    }

    private DividerItemDecoration getDivider(Context context, LinearLayoutManager layoutManager) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider));
        return dividerItemDecoration;
    }


    private  void removeChecked(List<Checkbox> data) {
        for (Checkbox checkbox : data)
            checkbox.checked.set(false);
    }

}
