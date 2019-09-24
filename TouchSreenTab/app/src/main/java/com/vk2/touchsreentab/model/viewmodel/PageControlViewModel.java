package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vk2.touchsreentab.model.PageControl;


public class PageControlViewModel extends ViewModel {
    private MutableLiveData<PageControl> pageControl = new MutableLiveData<>();

    public MutableLiveData<PageControl> getPageControl() {
        return pageControl;
    }

    public void setPageControl(PageControl pageControl) {
        this.pageControl.setValue(pageControl);
    }
}
