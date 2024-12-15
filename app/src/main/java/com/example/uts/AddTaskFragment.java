package com.example.uts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;


import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class AddTaskFragment extends Fragment {

    private EditText editTextTask;
    private Button buttonAddTask;
    private TaskViewModel taskViewModel;
    private GoogleAccountCredential credential;
    private Calendar calendarService;

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

        // Set up Google Calendar API
        credential = GoogleAccountCredential.usingOAuth2(
                getContext(), Collections.singleton("https://www.googleapis.com/auth/calendar"));
        credential.setSelectedAccountName("dwicandraandika4@gmail.com"); // Set your email here

        try {
            calendarService = new Calendar.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("UTS")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up Add Task button
        buttonAddTask.setOnClickListener(v -> {
            String task = editTextTask.getText().toString();
            if (!task.isEmpty() && taskViewModel != null) {
                taskViewModel.addTask(task);
                addEventToCalendar(task);
                editTextTask.setText("");
            }
        });

        return rootView;
    }

    private void addEventToCalendar(String task) {
        Event event = new Event()
                .setSummary(task)
                .setDescription("Task from UTS app");

        Date startDate = new Date();
        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(startDate))
                .setTimeZone("Asia/Jakarta");
        event.setStart(start);

        Date endDate = new Date(startDate.getTime() + 3600000); // 1 hour later
        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(endDate))
                .setTimeZone("Asia/Jakarta");
        event.setEnd(end);

        new Thread(() -> {
            try {
                calendarService.events().insert("primary", event).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}