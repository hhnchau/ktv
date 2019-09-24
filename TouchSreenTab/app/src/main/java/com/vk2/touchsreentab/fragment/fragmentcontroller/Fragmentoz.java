package com.vk2.touchsreentab.fragment.fragmentcontroller;

import android.support.v4.app.Fragment;

public class Fragmentoz {
    private Fragmentez fzg;
    private Fragment frg;


    public Fragmentoz(Fragmentez fzg, Fragment frg) {
        this.fzg = fzg;
        this.frg = frg;
    }

    public Fragmentez getFzg() {
        return fzg;
    }

    public Fragment getFrg() {
        return frg;
    }
}