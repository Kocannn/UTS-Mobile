package com.example.uts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskFragment extends Fragment {

    private EditText editTextTask;
    private Button buttonAddTask;
    private TaskViewModel taskViewModel;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        editTextTask = rootView.findViewById(R.id.editTextTask);
        buttonAddTask = rootView.findViewById(R.id.buttonAddTask);

        // Set up ViewModel
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        // Set up Add Task button
        buttonAddTask.setOnClickListener(v -> {
            String task = editTextTask.getText().toString();
            if (!task.isEmpty() && taskViewModel != null) {
                taskViewModel.addTask(task);
                editTextTask.setText("");
            }
        });

        return rootView;
    }
}