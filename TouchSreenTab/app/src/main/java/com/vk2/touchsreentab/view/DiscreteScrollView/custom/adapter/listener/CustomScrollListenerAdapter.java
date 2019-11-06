package com.vk2.touchsreentab.view.DiscreteScrollView.custom.adapter.listener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.vk2.touchsreentab.view.DiscreteScrollView.CustomDiscreteScrollView;

public class CustomScrollListenerAdapter<T extends RecyclerView.ViewHolder> implements CustomDiscreteScrollView.ScrollStateChangeListener<T> {

    private  CustomDiscreteScrollView.ScrollListener<T> adaptee;

    public CustomScrollListenerAdapter(@NonNull  CustomDiscreteScrollView.ScrollListener<T> adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void onScrollStart(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScrollEnd(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition,
                         int currentIndex, int newIndex,
                         @Nullable T currentHolder, @Nullable T newCurrentHolder) {
        adaptee.onScroll(scrollPosition, currentIndex, newIndex, currentHolder, newCurrentHolder);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomScrollListenerAdapter) {
            return adaptee.equals(((CustomScrollListenerAdapter) obj).adaptee);
        } else {
            return super.equals(obj);
        }
    }
}
