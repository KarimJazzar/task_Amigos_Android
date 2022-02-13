package com.example.task_amigos_android.helpers;

import android.graphics.Color;

import com.example.task_amigos_android.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryHelper {
    public static List<Category> categories = new ArrayList<>();

    public static int getCategoryColor(int id) {
        for(Category category : categories)  {
            if(category.getId() == id) {
                return category.getColor();
            }
        }

        return Color.WHITE;
    }

    public static int getCategoryIndex(int id) {
        int index = 1; // Start in 1 since we have a default None category;
        for(Category category : categories)  {
            if(category.getId() == id) {
                return index;
            }
            index++;
        }

        return 0;
    }

    public static int getCategoryId(String name) {
        for(Category category : categories)  {
            if(category.getName().equals(name)) {
                return category.getId();
            }
        }

        return 0;
    }

    public static Category getCategory(int id) {
        for(Category category : categories)  {
            if(category.getId() == id) {
                return category;
            }
        }

        Category def = new Category();
        def.setName("No Category");
        def.setColor(Color.WHITE);

        return def;
    }
}
