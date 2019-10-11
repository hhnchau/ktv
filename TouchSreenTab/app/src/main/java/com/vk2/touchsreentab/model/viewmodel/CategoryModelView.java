package com.vk2.touchsreentab.model.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.SparseArray;

import com.vk2.touchsreentab.aplication.MyApplication;
import com.vk2.touchsreentab.model.Category;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class CategoryModelView extends ViewModel {
    public MutableLiveData<Map<String, List<Category>>> mapCategory = new MutableLiveData<>();

    public void getAllCategory() {
        Flowable.zip(
                MyApplication.appDatabase.categoryDao().getAlLSongType(),
                MyApplication.appDatabase.categoryDao().getAlLSongLang(),
                new BiFunction<List<Category>, List<Category>, Map<String, List<Category>>>() {
                    @Override
                    public Map<String, List<Category>> apply(List<Category> categories, List<Category> categories2) throws Exception {
                        return combineResult(categories, categories2);
                    }
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private Map<String, List<Category>> combineResult(List<Category> categories, List<Category> categories2) {
        Map<String, List<Category>> map = new HashMap<>();
        map.put("0", new ArrayList<Category>());
        categories.get(0).setSelected(true);
        map.put("1", categories);
        map.put("2", new ArrayList<Category>());
        map.put("3", categories2);
        mapCategory.postValue(map);
        return map;
    }
}
