package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vk2.touchsreentab.model.TextSearch;

public class TextSearchViewModel extends ViewModel {
    public MutableLiveData<TextSearch> textSearch = new MutableLiveData<>();

    public MutableLiveData<TextSearch> getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(TextSearch textSearch) {
        this.textSearch.setValue(textSearch);
    }
}
