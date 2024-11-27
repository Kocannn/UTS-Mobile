package com.example.uts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private final MutableLiveData<List<String>> todoTasks = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<String>> completedTasks = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getTodoTasks() {
        return todoTasks;
    }

    public LiveData<List<String>> getCompletedTasks() {
        return completedTasks;
    }

    public void addTask(String task) {
        List<String> currentTasks = todoTasks.getValue();
        if (currentTasks != null) {
            currentTasks.add(task);
            todoTasks.setValue(currentTasks);
        }
    }

    public void completeTask(int position) {
        List<String> currentTodo = todoTasks.getValue();
        List<String> currentCompleted = completedTasks.getValue();
        if (currentTodo != null && currentCompleted != null && position >= 0 && position < currentTodo.size()) {
            String task = currentTodo.remove(position);
            currentCompleted.add(task);
            todoTasks.setValue(currentTodo);
            completedTasks.setValue(currentCompleted);
        }
    }

    public void removeTaskFromCompleted(int position) {
        List<String> currentCompleted = completedTasks.getValue();
        if (currentCompleted != null && position >= 0 && position < currentCompleted.size()) {
            currentCompleted.remove(position);
            completedTasks.setValue(currentCompleted);
        }
    }
}
