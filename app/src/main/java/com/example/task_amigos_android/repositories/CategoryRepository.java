package com.example.task_amigos_android.repositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.task_amigos_android.dao.CategoryDao;
import com.example.task_amigos_android.database.AppDatabase;
import com.example.task_amigos_android.entities.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        categoryDao = db.categoryDao();
        allCategories = categoryDao.getAll();
    }

    public void insert(Category category) {
        new CategoryRepository.AsyncInsert(categoryDao).execute(category);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    private static class AsyncInsert extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryAsyncDao;

        private AsyncInsert(CategoryDao dao) {
            this.categoryAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Category... category) {
            categoryAsyncDao.insert(category[0]);
            return null;
        }
    }
}
