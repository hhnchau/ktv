package com.vk2.touchsreentab.model.setting;

public class General {
    public static final int VALUES = 0;
    public static final int TOGGLE = 1;
    private String title;
    private String value;
    private int type;
    private boolean selected;

    public General() {
    }


    public General(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public General(String title, int type, boolean selected) {
        this.title = title;
        this.type = type;
        this.selected = selected;
    }

    public General(String title, int type, String value) {
        this.title = title;
        this.value = value;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
