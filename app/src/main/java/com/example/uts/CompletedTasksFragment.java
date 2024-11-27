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

public class CompletedTasksFragment extends Fragment {

    private RecyclerView recyclerViewCompleted;
    private TaskAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    public CompletedTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_completed_tasks, container, false);
        recyclerViewCompleted = rootView.findViewById(R.id.recyclerViewCompleted);

        // Set up RecyclerView
        recyclerViewCompleted.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(new ArrayList<>(), null, position -> {
            if (taskViewModel != null) {
                taskViewModel.removeTaskFromCompleted(position);
            }
        }); // Tidak ada listener selesai di fragment ini
        recyclerViewCompleted.setAdapter(taskAdapter);

        // Set up ViewModel
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.getCompletedTasks().observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.updateTasks(tasks);
        });

        return rootView;
    }
}
