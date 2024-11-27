package com.example.uts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private final MutableLiveData<List<String>> tasks = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getTasks() {
        return tasks;
    }

    public void addTask(String task) {
        List<String> currentTasks = tasks.getValue();
        if (currentTasks != null) {
            currentTasks.add(task);
            tasks.setValue(currentTasks);
        }
    }

    public void removeTask(int position) {
        List<String> currentTasks = tasks.getValue();
        if (currentTasks != null && position >= 0 && position < currentTasks.size()) {
            currentTasks.remove(position);
            tasks.setValue(currentTasks);
        }
    }
}
