package com.example.task_amigos_android.repositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.task_amigos_android.dao.TaskDao;
import com.example.task_amigos_android.database.AppDatabase;
import com.example.task_amigos_android.entities.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTask;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        taskDao = db.taskDao();
        allTask = taskDao.getAll();
    }

    public void insert(Task task) {
        new AsyncInsert(taskDao).execute(task);
    }
    public void delete(Task task) {
        new AsyncDelete(taskDao).execute(task);
    }
    public void update(Task task) {
        new AsyncUpdate(taskDao).execute(task);
    }

    public LiveData<List<Task>> getAllTask() {
        return allTask;
    }

    private static class AsyncInsert extends AsyncTask<Task, Void, Void> {
        private TaskDao taskAsyncDao;

        private AsyncInsert(TaskDao dao) {
            this.taskAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskAsyncDao.insert(task[0]);
            return null;
        }
    }

    private static class AsyncDelete extends AsyncTask<Task, Void, Void> {
        private TaskDao taskAsyncDao;

        private AsyncDelete(TaskDao dao) {
            this.taskAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskAsyncDao.delete(task[0]);
            return null;
        }
    }

    private static class AsyncUpdate extends AsyncTask<Task, Void, Void> {
        private TaskDao taskAsyncDao;

        private AsyncUpdate(TaskDao dao) {
            this.taskAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskAsyncDao.update(task[0]);
            return null;
        }
    }
}
