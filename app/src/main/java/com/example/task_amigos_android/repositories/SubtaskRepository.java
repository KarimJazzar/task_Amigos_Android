package com.example.task_amigos_android.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.task_amigos_android.dao.CategoryDao;
import com.example.task_amigos_android.dao.SubtaskDao;
import com.example.task_amigos_android.database.AppDatabase;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Subtask;

import java.util.List;

public class SubtaskRepository {
    private int taskId;
    private SubtaskDao subtaskDao;
    private LiveData<List<Subtask>> allSubtask;

    public SubtaskRepository(Application application, int taskId) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.taskId = taskId;
        subtaskDao = db.subtaskDao();
        allSubtask = subtaskDao.getAllRelatedBy(taskId);
    }

    public void insert(Subtask subtask) {
        new SubtaskRepository.AsyncInsert(subtaskDao).execute(subtask);
    }

    public LiveData<List<Subtask>> getAllSubtask() {
        return allSubtask;
    }

    private static class AsyncInsert extends AsyncTask<Subtask, Void, Void> {
        private SubtaskDao subtaskAsyncDao;

        private AsyncInsert(SubtaskDao dao) {
            this.subtaskAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Subtask... subtasks) {
            subtaskAsyncDao.insert(subtasks[0]);
            return null;
        }
    }
}
