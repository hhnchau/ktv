package com.vk2.touchsreentab.model.setting;

import androidx.databinding.ObservableBoolean;

public class Language implements Cloneable {

    private String flagName;
    private int flag;
    public ObservableBoolean checked = new ObservableBoolean();


    public Language(String flagName, int flag) {
        this.flagName = flagName;
        this.flag = flag;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    @Override
    public Language clone() {
        Language language;

        try {
            language = (Language) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return language;
    }
}
