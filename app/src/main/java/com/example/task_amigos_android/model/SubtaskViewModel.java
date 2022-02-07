package com.example.task_amigos_android.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.repositories.SubtaskRepository;
import com.example.task_amigos_android.repositories.TaskRepository;

import java.util.List;

public class SubtaskViewModel extends AndroidViewModel {
    private int taskId;
    private SubtaskRepository subtaskRepo;
    private LiveData<List<Subtask>> allSubtask;

    public SubtaskViewModel(@NonNull Application application, int taskId) {
        super(application);
        this.taskId = taskId;
        subtaskRepo = new SubtaskRepository(application, taskId);
        allSubtask = subtaskRepo.getAllSubtask();
    }

    public LiveData<List<Subtask>> getAllSubtask() {
        return allSubtask;
    }

    public void insert(Subtask subtask) {
        subtaskRepo.insert(subtask);
    }
}