package com.example.task_amigos_android.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import com.example.task_amigos_android.entities.Task;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Insert
    void insert(Task tasks);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
