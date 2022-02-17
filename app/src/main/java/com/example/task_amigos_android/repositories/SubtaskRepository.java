package com.example.task_amigos_android.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.task_amigos_android.dao.SubtaskDao;
import com.example.task_amigos_android.dao.TaskDao;
import com.example.task_amigos_android.database.AppDatabase;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;

import java.util.List;

public class SubtaskRepository {
    private int taskId;
    private SubtaskDao subtaskDao;
    private LiveData<List<Subtask>> allSubtask;

    public SubtaskRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        subtaskDao = db.subtaskDao();
    }

    public LiveData<List<Subtask>> getAllSubtask() {
        allSubtask = subtaskDao.getAll();
        return allSubtask;
    }

    public List<Subtask> getByTaskId(int id) {
        return (List<Subtask>) new AsyncSelectById(subtaskDao).execute(id);
    }

    public void insert(Subtask subtask) {
        new SubtaskRepository.AsyncInsert(subtaskDao).execute(subtask);
    }

    public void delete(Subtask task) {
        new SubtaskRepository.AsyncDelete(subtaskDao).execute(task);
    }
    public void update(Subtask task) {
        new SubtaskRepository.AsyncUpdate(subtaskDao).execute(task);
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

    private static class AsyncDelete extends AsyncTask<Subtask, Void, Void> {
        private SubtaskDao subtaskAsyncDao;

        private AsyncDelete(SubtaskDao dao) {
            this.subtaskAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Subtask... task) {
            subtaskAsyncDao.delete(task[0]);
            return null;
        }
    }

    private static class AsyncUpdate extends AsyncTask<Subtask, Void, Void> {
        private SubtaskDao subtaskAsyncDao;

        private AsyncUpdate(SubtaskDao dao) {
            this.subtaskAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(Subtask... task) {
            subtaskAsyncDao.update(task[0]);
            return null;
        }
    }

    private static class AsyncSelectById extends AsyncTask<Integer, Void, List<Subtask>> {
        private SubtaskDao subtaskAsyncDao;

        private AsyncSelectById(SubtaskDao dao) {
            this.subtaskAsyncDao = dao;
        }

        @Override
        protected List<Subtask> doInBackground(Integer... ids) {
            return subtaskAsyncDao.getByTaskId(ids[0]);
        }
    }



}
