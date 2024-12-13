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
    private final MutableLiveData<List<String>> todoTasks = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<String>> completedTasks = new MutableLiveData<>(new ArrayList<>());
    private final TaskDatabaseHelper dbHelper;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new TaskDatabaseHelper(application);
        loadTasks();
    }

    public LiveData<List<String>> getTodoTasks() {
        return todoTasks;
    }

    public LiveData<List<String>> getCompletedTasks() {
        return completedTasks;
    }

    public void addTask(String task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO tasks (task, completed) VALUES (?, 0)", new Object[]{task});
        loadTasks();
    }

    public void completeTask(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<String> currentTodo = todoTasks.getValue();
        if (currentTodo != null && position >= 0 && position < currentTodo.size()) {
            String task = currentTodo.get(position);
            db.execSQL("UPDATE tasks SET completed = 1 WHERE task = ?", new Object[]{task});
            loadTasks();
        }
    }

    public void removeTaskFromCompleted(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<String> currentCompleted = completedTasks.getValue();
        if (currentCompleted != null && position >= 0 && position < currentCompleted.size()) {
            String task = currentCompleted.get(position);
            db.execSQL("DELETE FROM tasks WHERE task = ?", new Object[]{task});
            loadTasks();
        }
    }

    private void loadTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT task, completed FROM tasks", null);
        List<String> todoList = new ArrayList<>();
        List<String> completedList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String task = cursor.getString(0);
            int completed = cursor.getInt(1);
            if (completed == 0) {
                todoList.add(task);
            } else {
                completedList.add(task);
            }
        }
        cursor.close();
        todoTasks.setValue(todoList);
        completedTasks.setValue(completedList);
    }
}