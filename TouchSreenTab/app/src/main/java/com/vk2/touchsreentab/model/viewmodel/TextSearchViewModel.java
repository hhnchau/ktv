package com.vk2.touchsreentab.model.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
