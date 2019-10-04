package com.vk2.touchsreentab.model;

import com.vk2.touchsreentab.fragment.fragmentcontroller.Fragmentez;

public class PageControl {
    public static final int NEXT = 1;
    public static final int PREVIOUS = 2;
    public static final int TOP = 3;

    private int direction;
    private Fragmentez frg;

    public PageControl(Fragmentez frg, int direction) {
        this.direction = direction;
        this.frg = frg;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Fragmentez getFrg() {
        return frg;
    }

    public void setFrg(Fragmentez frg) {
        this.frg = frg;
    }
}
