package com.example.task_amigos_android.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.repositories.CategoryRepository;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepo;
    private LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepo = new CategoryRepository(application);
        allCategories = categoryRepo.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insert(Category category) {
        categoryRepo.insert(category);
    }
}
