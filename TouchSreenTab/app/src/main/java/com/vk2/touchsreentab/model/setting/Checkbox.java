package com.vk2.touchsreentab.model.setting;

import androidx.databinding.ObservableBoolean;

public class Checkbox {
    public ObservableBoolean checked = new ObservableBoolean();
    private String title;

    public Checkbox(boolean checked, String title) {
        this.checked.set(checked);
        this.title = title;
    }

    public Checkbox(String title) {
        this.title = title;
    }

    public ObservableBoolean getActive() {
        return checked;
    }

    public void setActive(ObservableBoolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
