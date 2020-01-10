package com.vk2.touchsreentab.fragment.setting;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.databinding.FragmentSettingChangePasswordBinding;
import com.vk2.touchsreentab.view.MyNumPad;

import java.lang.reflect.Method;

public class FragmentChangePassword extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSettingChangePasswordBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_change_password, container, false);
        binding.sticky.setSticky(getString(R.string.txt_change_password));
        keyboardClose(binding.inputOld);
        keyboardClose(binding.inputNew);
        keyboardClose(binding.inputConfirm);
        binding.sticky.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        binding.boxButton.setListener(new MyNumPad.OnNumPadListener() {
            @Override
            public void onNumber(final String key) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (binding.inputOld.hasFocus())
                                binding.inputOld.append(key);
                            else if (binding.inputNew.hasFocus())
                                binding.inputNew.append(key);
                            else if (binding.inputConfirm.hasFocus())
                                binding.inputConfirm.append(key);
                        }
                    });
            }

            @Override
            public void onButton(boolean yes) {
                if (yes) {

                } else {

                }
            }
        });

        binding.clearOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Editable s = binding.inputOld.getText();
                            if (s != null && !s.toString().isEmpty()) {
                                String str = s.toString().substring(0, s.length() - 1);
                                binding.inputOld.setText(str);
                                binding.inputOld.setSelection(binding.inputOld.length());
                            }
                        }
                    });
            }
        });

        binding.clearNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Editable s = binding.inputNew.getText();
                            if (s != null && !s.toString().isEmpty()) {
                                String str = s.toString().substring(0, s.length() - 1);
                                binding.inputNew.setText(str);
                                binding.inputNew.setSelection(binding.inputNew.length());
                            }
                        }
                    });
            }
        });

        binding.clearConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Editable s = binding.inputConfirm.getText();
                            if (s != null && !s.toString().isEmpty()) {
                                String str = s.toString().substring(0, s.length() - 1);
                                binding.inputConfirm.setText(str);
                                binding.inputConfirm.setSelection(binding.inputConfirm.length());
                            }
                        }
                    });
            }
        });

        return binding.getRoot();
    }

    public void keyboardClose(EditText edt) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edt.setShowSoftInputOnFocus(false);
        } else {
            try {
                final Method method = EditText.class.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(this, false);
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
