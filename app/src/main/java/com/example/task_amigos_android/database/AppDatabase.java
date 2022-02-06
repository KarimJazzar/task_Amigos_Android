package com.example.task_amigos_android.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.task_amigos_android.dao.TaskDao;
import com.example.task_amigos_android.entities.Category;
import com.example.task_amigos_android.entities.Subtask;
import com.example.task_amigos_android.entities.Task;
import com.example.task_amigos_android.helpers.Converter;

@TypeConverters({Converter.class})
@Database(entities = {Task.class, Category.class, Subtask.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static String DB_NAME = "amigos";
    private static AppDatabase INSTANCE = null;

    public abstract TaskDao taskDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =  Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }
}
