package com.example.uts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletedTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedTasksFragment extends Fragment {

    private RecyclerView recyclerViewCompleted;
    private TaskAdapter taskAdapter;
    private ArrayList<String> completedTasks = new ArrayList<>();

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
        taskAdapter = new TaskAdapter(completedTasks, position -> {
            completedTasks.remove(position);
            taskAdapter.notifyItemRemoved(position);
        });
        recyclerViewCompleted.setAdapter(taskAdapter);

        // Simulasi tugas yang selesai
        completedTasks.add("Wash the car");
        taskAdapter.notifyDataSetChanged();

        return rootView;
    }
}
