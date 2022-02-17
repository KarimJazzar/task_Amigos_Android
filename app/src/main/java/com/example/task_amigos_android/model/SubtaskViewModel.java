package com.example.task_amigos_android.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.repositories.SubtaskRepository;

import java.util.List;

public class SubtaskViewModel extends AndroidViewModel {
    private SubtaskRepository subtaskRepo;
    private LiveData<List<Subtask>> allSubtask;

    public SubtaskViewModel(@NonNull Application application) {
        super(application);
        subtaskRepo = new SubtaskRepository(application);
        allSubtask = subtaskRepo.getAllSubtask();
    }

    public LiveData<List<Subtask>> getAllSubtask() {
        return allSubtask;
    }
    public List<Subtask> getSubtaskByTaskId(int id) { return subtaskRepo.getByTaskId(id); }
    public void insert(Subtask subtask) {
        subtaskRepo.insert(subtask);
    }
    public void delete(Subtask task) { subtaskRepo.delete(task); }

    public void update(Subtask task) { subtaskRepo.update(task); }
}