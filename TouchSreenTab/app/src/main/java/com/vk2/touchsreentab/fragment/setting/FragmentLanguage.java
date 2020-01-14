package com.vk2.touchsreentab.fragment.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk2.touchsreentab.R;
import com.vk2.touchsreentab.activity.SettingActivity;
import com.vk2.touchsreentab.adapter.SettingAdapter;
import com.vk2.touchsreentab.adapter.viewholder.SettingLanguageViewHolder;
import com.vk2.touchsreentab.databinding.FragmentSettingLanguageBinding;
import com.vk2.touchsreentab.databinding.ItemSettingLanguageBinding;
import com.vk2.touchsreentab.model.setting.Language;
import com.vk2.touchsreentab.utils.SaveDataUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentLanguage extends Fragment implements View.OnClickListener {
    private List<Language> languages;
    private int posChecked;
    private int pos;
    public ObservableBoolean checked = new ObservableBoolean();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingLanguageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting_language, container, false);
        binding.setDataBinding(this);
        binding.btnNo.setOnClickListener(this);
        binding.btnYes.setOnClickListener(this);

        int[] flags = {
                R.mipmap.flag_vietnam,
                R.mipmap.flag_thailand,
                R.mipmap.flag_lao,
                R.mipmap.flag_english,
                R.mipmap.flag_japan,
                R.mipmap.flag_korea,
                R.mipmap.flag_china,
                R.mipmap.flag_hongkong
        };
        String[] names = {
                getResources().getString(R.string.language_vietnam),
                getResources().getString(R.string.language_thailand),
                getResources().getString(R.string.language_lao),
                getResources().getString(R.string.language_english),
                getResources().getString(R.string.language_japan),
                getResources().getString(R.string.language_korea),
                getResources().getString(R.string.language_china),
                getResources().getString(R.string.language_hongkong)
        };

        languages = new ArrayList<>();
        for (int i = 0; i < flags.length; i++) {
            languages.add(new Language(
                    names[i], flags[i]
            ));
        }

        languages.get(SaveDataUtils.getInstance(getActivity()).getLanguageSetting()).checked.set(true);

        SettingAdapter<SettingLanguageViewHolder> adapter = new SettingAdapter<SettingLanguageViewHolder>() {
            @NonNull
            @Override
            public SettingLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ItemSettingLanguageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_setting_language, viewGroup, false);
                return new SettingLanguageViewHolder(binding);
            }

            @Override
            public void onBindViewHolder(@NonNull final SettingLanguageViewHolder holder, int i) {
                final Language language = languages.get(holder.getAdapterPosition());
                if (language != null) {
                    holder.languageBinding.setLanguage(language);
                    holder.languageBinding.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pos = holder.getAdapterPosition();
                            int p = removeChecked();
                            if (!checked.get())posChecked = p;
                            language.checked.set(true);
                            checked.set(true);
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return languages.size();
            }
        };

        binding.rcv.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        binding.rcv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    private int removeChecked() {
        int p = 0;
        for (int i = 0; i < languages.size(); i++) {
            if (languages.get(i).checked.get()) p = i;
            languages.get(i).checked.set(false);
        }
        return p;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNo) {
            checked.set(false);
            removeChecked();
            languages.get(posChecked).checked.set(true);
        } else if (view.getId() == R.id.btnYes) {
            checked.set(false);
            if (getActivity() != null) {
                ((SettingActivity) getActivity()).updateStatus(languages.get(pos).getFlagName());
                SaveDataUtils.getInstance(getActivity()).setLanguageSetting(pos);
            }
        }
    }
}
