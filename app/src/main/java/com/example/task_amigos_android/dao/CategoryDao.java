package com.example.task_amigos_android.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.task_amigos_android.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    LiveData<List<Category>> getAll();

    @Insert
    void insert(Category tasks);

    @Update
    void update(Category task);

    @Query("DELETE FROM category WHERE id = :categoryId")
    void deleteCategoryById(long categoryId);

    @Delete
    void delete(Category task);
}
