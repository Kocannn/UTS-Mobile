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

    private RecyclerView recyclerViewHighPriority;
    private RecyclerView recyclerViewMediumPriority;
    private RecyclerView recyclerViewLowPriority;
    private TaskAdapter highPriorityAdapter;
    private TaskAdapter mediumPriorityAdapter;
    private TaskAdapter lowPriorityAdapter;
    private TaskViewModel taskViewModel;

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        recyclerViewHighPriority = rootView.findViewById(R.id.recyclerViewHighPriority);
        recyclerViewMediumPriority = rootView.findViewById(R.id.recyclerViewMediumPriority);
        recyclerViewLowPriority = rootView.findViewById(R.id.recyclerViewLowPriority);

        // Set up RecyclerViews
        recyclerViewHighPriority.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMediumPriority.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLowPriority.setLayoutManager(new LinearLayoutManager(getContext()));

        highPriorityAdapter = new TaskAdapter(new ArrayList<>(), position -> {
            if (taskViewModel != null) {
                taskViewModel.completeTask(position, 1);
            }
        }, null);
        mediumPriorityAdapter = new TaskAdapter(new ArrayList<>(), position -> {
            if (taskViewModel != null) {
                taskViewModel.completeTask(position, 2);
            }
        }, null);
        lowPriorityAdapter = new TaskAdapter(new ArrayList<>(), position -> {
            if (taskViewModel != null) {
                taskViewModel.completeTask(position, 3);
            }
        }, null);

        recyclerViewHighPriority.setAdapter(highPriorityAdapter);
        recyclerViewMediumPriority.setAdapter(mediumPriorityAdapter);
        recyclerViewLowPriority.setAdapter(lowPriorityAdapter);

        // Set up ViewModel
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.getHighPriorityTasks().observe(getViewLifecycleOwner(), tasks -> {
            highPriorityAdapter.updateTasks(tasks);
        });
        taskViewModel.getMediumPriorityTasks().observe(getViewLifecycleOwner(), tasks -> {
            mediumPriorityAdapter.updateTasks(tasks);
        });
        taskViewModel.getLowPriorityTasks().observe(getViewLifecycleOwner(), tasks -> {
            lowPriorityAdapter.updateTasks(tasks);
        });

        return rootView;
    }
}