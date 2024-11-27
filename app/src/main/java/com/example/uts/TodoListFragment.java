package com.example.uts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TodoListFragment extends Fragment {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        recyclerViewTasks = rootView.findViewById(R.id.recyclerViewTasks);

        // Set up RecyclerView
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(new ArrayList<>(), position -> {
            if (taskViewModel != null) {
                taskViewModel.completeTask(position);
            }
        }, null); // Tidak ada listener hapus di fragment ini
        recyclerViewTasks.setAdapter(taskAdapter);

        // Set up ViewModel
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.getTodoTasks().observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.updateTasks(tasks);
        });

        return rootView;
    }
}
