package com.example.task_amigos_android.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.repositories.TaskRepository;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepo;
    private LiveData<List<Task>> allTask;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepo = new TaskRepository(application);
        allTask = taskRepo.getAllTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return allTask;
    }

    public void insert(Task task) {
        taskRepo.insert(task);
    }
}
