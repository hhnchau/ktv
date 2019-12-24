package com.vk2.touchsreentab.model.setting;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class Menu {
    public ObservableBoolean active = new ObservableBoolean();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> status = new ObservableField<>();
    public int icon;

    public Menu(boolean active, String title, String status, int icon) {
        this.active.set(active);
        this.title.set(title);
        this.status.set(status);
        this.icon = icon;
    }

}
