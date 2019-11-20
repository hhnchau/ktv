package com.vk2.touchsreentab.view.DiscreteScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.vk2.touchsreentab.view.DiscreteScrollView.custom.CustomDSVOrientation;
import com.vk2.touchsreentab.view.DiscreteScrollView.custom.CustomDiscreteScrollLayoutManager;
import com.vk2.touchsreentab.view.DiscreteScrollView.custom.adapter.listener.CustomScrollListenerAdapter;
import com.vk2.touchsreentab.view.DiscreteScrollView.custom.transform.CustomScaleTransformer;

import java.util.ArrayList;
import java.util.List;

public class CustomDiscreteScrollView extends RecyclerView {
    private static final int DEFAULT_ORIENTATION = CustomDSVOrientation.HORIZONTAL.ordinal();

    private CustomDiscreteScrollLayoutManager layoutManager;

    private List<CustomDiscreteScrollView.ScrollStateChangeListener> scrollStateChangeListeners;
    private List<CustomDiscreteScrollView.OnItemChangedListener> onItemChangedListeners;

    private boolean isOverScrollEnabled;

    public CustomDiscreteScrollView(Context context) {

        super(context);
        init(null);
    }

    public CustomDiscreteScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomDiscreteScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        scrollStateChangeListeners = new ArrayList<>();
        onItemChangedListeners = new ArrayList<>();

        int orientation = DEFAULT_ORIENTATION;
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable") TypedArray ta = getContext().obtainStyledAttributes(attrs, com.yarolegovich.discretescrollview.R.styleable.DiscreteScrollView);
            orientation = ta.getInt(com.yarolegovich.discretescrollview.R.styleable.DiscreteScrollView_dsv_orientation, DEFAULT_ORIENTATION);
            ta.recycle();
        }

        isOverScrollEnabled = getOverScrollMode() != OVER_SCROLL_NEVER;

        layoutManager = new CustomDiscreteScrollLayoutManager(
                getContext(), new ScrollStateListener(),
                CustomDSVOrientation.values()[orientation]);
        setLayoutManager(layoutManager);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof CustomDiscreteScrollLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            throw new IllegalArgumentException(getContext().getString(com.yarolegovich.discretescrollview.R.string.dsv_ex_msg_dont_set_lm));
        }
    }


    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean isFling = super.fling(velocityX, velocityY);
        if (isFling) {
            layoutManager.onFling(velocityX, velocityY);
        } else {
            layoutManager.returnToCurrentPosition();
        }
        return isFling;
    }

    @Nullable
    public ViewHolder getViewHolder(int position) {
        View view = layoutManager.findViewByPosition(position);
        return view != null ? getChildViewHolder(view) : null;
    }

    /**
     * @return adapter position of the current item or -1 if nothing is selected
     */
    public int getCurrentItem() {
        return layoutManager.getCurrentPosition();
    }

    public float getScaleHeight(){
        return layoutManager.getScaleHeight();
    }
    public void setScaleHeight(float scaleHeight){
        layoutManager.setScaleHeight(scaleHeight);
    }
    public void setItemTransformer(CustomScaleTransformer transformer) {
        layoutManager.setItemTransformer(transformer);
    }

    public void setItemTransitionTimeMillis(@IntRange(from = 10) int millis) {
        layoutManager.setTimeForItemSettle(millis);
    }

    public void setSlideOnFling(boolean result){
        layoutManager.setShouldSlideOnFling(result);
    }

    public void setSlideOnFlingThreshold(int threshold){
        layoutManager.setSlideOnFlingThreshold(threshold);
    }

    public void setOrientation(CustomDSVOrientation orientation) {
        layoutManager.setOrientation(orientation);
    }

    public void setOffscreenItems(int items) {
        layoutManager.setOffscreenItems(items);
    }

    public void setClampTransformProgressAfter(@IntRange(from = 1) int itemCount) {
        if (itemCount <= 1) {
            throw new IllegalArgumentException("must be >= 1");
        }
        layoutManager.setTransformClampItemCount(itemCount);
    }

    public void setOverScrollEnabled(boolean overScrollEnabled) {
        isOverScrollEnabled = overScrollEnabled;
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public void addScrollStateChangeListener(@NonNull ScrollStateChangeListener<?> scrollStateChangeListener) {
        scrollStateChangeListeners.add(scrollStateChangeListener);
    }

    public void addScrollListener(@NonNull CustomDiscreteScrollView.ScrollListener<?> scrollListener) {
        addScrollStateChangeListener(new CustomScrollListenerAdapter(scrollListener));
    }

    public void addOnItemChangedListener(@NonNull CustomDiscreteScrollView.OnItemChangedListener<?> onItemChangedListener) {
        onItemChangedListeners.add(onItemChangedListener);
    }

    public void removeScrollStateChangeListener(@NonNull CustomDiscreteScrollView.ScrollStateChangeListener<?> scrollStateChangeListener) {
        scrollStateChangeListeners.remove(scrollStateChangeListener);
    }

    public void removeScrollListener(@NonNull CustomDiscreteScrollView.ScrollListener<?> scrollListener) {
        removeScrollStateChangeListener(new CustomScrollListenerAdapter<>(scrollListener));
    }

    public void removeItemChangedListener(@NonNull CustomDiscreteScrollView.OnItemChangedListener<?> onItemChangedListener) {
        onItemChangedListeners.remove(onItemChangedListener);
    }

    private void notifyScrollStart(ViewHolder holder, int current) {
        for (CustomDiscreteScrollView.ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScrollStart(holder, current);
        }
    }

    private void notifyScrollEnd(ViewHolder holder, int current) {
        for (CustomDiscreteScrollView.ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScrollEnd(holder, current);
        }
    }

    private void notifyScroll(float position,
                              int currentIndex, int newIndex,
                              ViewHolder currentHolder, ViewHolder newHolder) {
        for (CustomDiscreteScrollView.ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScroll(position, currentIndex, newIndex,
                    currentHolder,
                    newHolder);
        }
    }

    private void notifyCurrentItemChanged(ViewHolder holder, int current) {
        for (CustomDiscreteScrollView.OnItemChangedListener listener : onItemChangedListeners) {
            listener.onCurrentItemChanged(holder, current);
        }
    }

    private void notifyCurrentItemChanged() {
        if (onItemChangedListeners.isEmpty()) {
            return;
        }
        int current = layoutManager.getCurrentPosition();
        ViewHolder currentHolder = getViewHolder(current);
        notifyCurrentItemChanged(currentHolder, current);
    }

    private class ScrollStateListener implements CustomDiscreteScrollLayoutManager.ScrollStateListener {

        @Override
        public void onIsBoundReachedFlagChange(boolean isBoundReached) {
            if (isOverScrollEnabled) {
                setOverScrollMode(isBoundReached ? OVER_SCROLL_ALWAYS : OVER_SCROLL_NEVER);
            }
        }

        @Override
        public void onScrollStart() {
            if (scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = layoutManager.getCurrentPosition();
            ViewHolder holder = getViewHolder(current);
            if (holder != null) {
                notifyScrollStart(holder, current);
            }
        }

        @Override
        public void onScrollEnd() {
            if (onItemChangedListeners.isEmpty() && scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = layoutManager.getCurrentPosition();
            ViewHolder holder = getViewHolder(current);
            if (holder != null) {
                notifyScrollEnd(holder, current);
                notifyCurrentItemChanged(holder, current);
            }
        }

        @Override
        public void onScroll(float currentViewPosition) {
            if (scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int currentIndex = getCurrentItem();
            int newIndex = layoutManager.getNextPosition();
            if (currentIndex != newIndex) {
                notifyScroll(currentViewPosition,
                        currentIndex, newIndex,
                        getViewHolder(currentIndex),
                        getViewHolder(newIndex));
            }
        }

        @Override
        public void onCurrentViewFirstLayout() {
            post(new Runnable() {
                @Override
                public void run() {
                    notifyCurrentItemChanged();
                }
            });
        }

        @Override
        public void onDataSetChangeChangedPosition() {
            notifyCurrentItemChanged();
        }
    }

    public interface ScrollStateChangeListener<T extends ViewHolder> {

        void onScrollStart(@NonNull T currentItemHolder, int adapterPosition);

        void onScrollEnd(@NonNull T currentItemHolder, int adapterPosition);

        void onScroll(float scrollPosition,
                      int currentPosition,
                      int newPosition,
                      @Nullable T currentHolder,
                      @Nullable T newCurrent);
    }

    public interface ScrollListener<T extends ViewHolder> {

        void onScroll(float scrollPosition,
                      int currentPosition, int newPosition,
                      @Nullable T currentHolder,
                      @Nullable T newCurrent);
    }

    public interface OnItemChangedListener<T extends ViewHolder> {
        /*
         * This method will be also triggered when view appears on the screen for the first time.
         * If data set is empty, viewHolder will be null and adapterPosition will be NO_POSITION
         */
        void onCurrentItemChanged(@Nullable T viewHolder, int adapterPosition);
    }
}