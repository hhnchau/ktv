package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SearchInputViewModel extends ViewModel {
    public MutableLiveData<String> searchInput = new MutableLiveData<>();

    public MutableLiveData<String> getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String input) {
        this.searchInput.setValue(input);
    }
}
