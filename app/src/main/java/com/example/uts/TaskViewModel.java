package com.example.uts;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Task>> highPriorityTasks = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Task>> mediumPriorityTasks = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Task>> lowPriorityTasks = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Task>> completedTasks = new MutableLiveData<>(new ArrayList<>());
    private final TaskDatabaseHelper dbHelper;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new TaskDatabaseHelper(application);
        loadTasks();
    }

    public LiveData<List<Task>> getHighPriorityTasks() {
        return highPriorityTasks;
    }

    public LiveData<List<Task>> getMediumPriorityTasks() {
        return mediumPriorityTasks;
    }

    public LiveData<List<Task>> getLowPriorityTasks() {
        return lowPriorityTasks;
    }

    public LiveData<List<Task>> getCompletedTasks() {
        return completedTasks;
    }

    public void addTask(String task, int priority, long deadline) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO tasks (task, priority, deadline, completed) VALUES (?, ?, ?, 0)", new Object[]{task, priority, deadline});
        loadTasks();
    }

    public void completeTask(int position, int priority) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Task> currentTasks = getTasksByPriority(priority).getValue();
        if (currentTasks != null && position >= 0 && position < currentTasks.size()) {
            Task task = currentTasks.get(position);
            db.execSQL("UPDATE tasks SET completed = 1 WHERE id = ?", new Object[]{task.getId()});
            loadTasks();
        }
    }

    public void removeTaskFromCompleted(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Task> currentTasks = completedTasks.getValue();
        if (currentTasks != null && position >= 0 && position < currentTasks.size()) {
            Task task = currentTasks.get(position);
            db.execSQL("DELETE FROM tasks WHERE id = ?", new Object[]{task.getId()});
            loadTasks();
        }
    }

    private LiveData<List<Task>> getTasksByPriority(int priority) {
        switch (priority) {
            case 1:
                return highPriorityTasks;
            case 2:
                return mediumPriorityTasks;
            case 3:
                return lowPriorityTasks;
            default:
                return new MutableLiveData<>(new ArrayList<>());
        }
    }

    private void loadTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, task, priority, deadline, completed FROM tasks ORDER BY priority ASC, deadline ASC", null);
        List<Task> highPriorityList = new ArrayList<>();
        List<Task> mediumPriorityList = new ArrayList<>();
        List<Task> lowPriorityList = new ArrayList<>();
        List<Task> completedList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String task = cursor.getString(1);
            int priority = cursor.getInt(2);
            long deadline = cursor.getLong(3);
            boolean completed = cursor.getInt(4) == 1;
            Task taskObj = new Task(id, task, priority, deadline, completed);
            if (completed) {
                completedList.add(taskObj);
            } else {
                if (priority == 1) {
                    highPriorityList.add(taskObj);
                } else if (priority == 2) {
                    mediumPriorityList.add(taskObj);
                } else if (priority == 3) {
                    lowPriorityList.add(taskObj);
                }
            }
        }
        cursor.close();
        highPriorityTasks.setValue(highPriorityList);
        mediumPriorityTasks.setValue(mediumPriorityList);
        lowPriorityTasks.setValue(lowPriorityList);
        completedTasks.setValue(completedList);
    }
}