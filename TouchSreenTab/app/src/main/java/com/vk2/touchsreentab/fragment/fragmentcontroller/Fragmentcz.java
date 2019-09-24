package com.vk2.touchsreentab.fragment.fragmentcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.vk2.touchsreentab.R;

import java.util.List;

public class Fragmentcz {
    public static final int NONE = -1;
    public static final int NEXT = 0;
    public static final int BACK = 1;
    private static Fragmentez fragmentez;

    public static void replaceFragment(FragmentManager fragmentManager, Fragmentez fzg, boolean backStack, int frame, Bundle bundle, int direction) {

        int enter, exit, pop_enter, pop_exit;
        if (direction == NEXT) {
            enter = R.anim.enter_from_right;
            exit = R.anim.exit_to_left;

            pop_enter = R.anim.enter_from_left;
            pop_exit = R.anim.exit_to_right;
        } else {
            enter = R.anim.enter_from_left;
            exit = R.anim.exit_to_right;

            pop_enter = R.anim.enter_from_right;
            pop_exit = R.anim.exit_to_left;
        }

        Fragment frg = Fragmentiz.getFragment(fzg);

        if (frg != null && bundle != null)
            frg.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (frg != null) {

            if (backStack) transaction.addToBackStack(frg.getClass().getName());

            if (direction > NONE) transaction.setCustomAnimations(enter, exit, pop_enter, pop_exit);
            transaction.replace(frame, frg, frg.getClass().getName())
                    .commit();
        }
    }

    public static void addFragment(List<Fragmentoz> lstFragment, FragmentManager fragmentManager, Fragmentez fzg, boolean backStack, int frame, Bundle bundle, int direction) {
        fragmentez = fzg;
        boolean exist = false;
        for (Fragmentoz item : lstFragment)
            if (fzg == item.getFzg()) {
                exist = true;
                break;
            }


        int enter, exit;
        if (direction == NEXT) {
            enter = R.anim.enter_from_right;
            exit = R.anim.exit_to_left;
        } else {
            enter = R.anim.enter_from_left;
            exit = R.anim.exit_to_right;
        }

        if (exist) {
            //Exist
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment frg = null;

            for (Fragmentoz item : lstFragment) {
                transaction.hide(item.getFrg());
                if (fzg == item.getFzg()) frg = item.getFrg();
            }
            if (frg != null) {
                if (direction > NONE) transaction.setCustomAnimations(enter, exit);
                transaction.show(frg)
                        .commit();
            }
        } else {
            //Add
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (Fragmentoz item : lstFragment) {
                if (fzg != item.getFzg()) transaction.hide(item.getFrg());
            }
            Fragment frg = Fragmentiz.getFragment(fzg);

            if (frg != null) {

                if (bundle != null) frg.setArguments(bundle);

                if (backStack) transaction.addToBackStack(frg.getClass().getName());

                if (direction > NONE) transaction.setCustomAnimations(enter, exit);
                transaction.add(frame, frg, frg.getClass().getName())
                        .commit();
                lstFragment.add(new Fragmentoz(fzg, frg));
            }
        }
    }

    public static void removeFragment(List<Fragmentoz> lstFragment, FragmentManager fragmentManager) {
        if (fragmentManager != null && lstFragment != null)
            for (Fragmentoz f : lstFragment)
                for (Fragment frg : fragmentManager.getFragments())
                    if (frg == f.getFrg()) {
                        fragmentManager.beginTransaction().remove(frg).commit();
                        break;
                    }
    }

    public static void removeAllFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null)
            for (Fragment frg : fragmentManager.getFragments())
                fragmentManager.beginTransaction().remove(frg).commit();
    }

    public static void clearAllPopBackStack(FragmentManager fragmentManager) {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }

    public static Fragmentez getCurrentFragment() {
        return fragmentez;
    }
}