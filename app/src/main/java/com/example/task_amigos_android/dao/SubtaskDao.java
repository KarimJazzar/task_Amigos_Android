package com.example.task_amigos_android.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;

import java.util.List;

@Dao
public interface SubtaskDao {
    @Query("SELECT * FROM subtask")
    LiveData<List<Subtask>> getAll();

    @Query("SELECT * FROM subtask WHERE task_id IN (:taskId)")
    List<Subtask> getByTaskId(int taskId);

    @Insert
    void insert(Subtask subtask);

    @Update
    void update(Subtask subtask);

    @Delete
    void delete(Subtask subtask);
}
