package com.vk2.touchsreentab.model.setting;

public class Language {
    private String flagName;
    private int flag;

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
}
