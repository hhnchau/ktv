package com.vk2.touchsreentab.view.DiscreteScrollView.demodata;

import com.vk2.touchsreentab.R;

import java.util.Arrays;
import java.util.List;

public class Shop {
    public static Shop get() {
        return new Shop();
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "Everyday Candle", "$12.00 USD", R.drawable.album1),
                new Item(2, "Small Porcelain Bowl", "$50.00 USD", R.drawable.album2),
                new Item(3, "Favourite Board", "$265.00 USD", R.drawable.album3),
                new Item(4, "Earthenware Bowl", "$18.00 USD", R.drawable.album4),
                new Item(5, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.album1),
                new Item(51, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.album2),
                new Item(52, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.album3),
                new Item(53, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.album4),
                new Item(54, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.album1),
                new Item(6, "Detailed Rolling Pin", "$145.00 USD", R.drawable.album2));
    }
}