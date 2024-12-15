package com.example.uts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class AddTaskFragment extends Fragment {

    private EditText editTextTask;
    private Button buttonSelectPriority;
    private TextView textViewPriority;
    private Button buttonSelectDeadline;
    private TextView textViewDeadline;
    private Button buttonAddTask;
    private TaskViewModel taskViewModel;
    private GoogleAccountCredential credential;
    private com.google.api.services.calendar.Calendar calendarService;
    private long deadlineTimestamp;
    private int selectedPriority;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        editTextTask = rootView.findViewById(R.id.editTextTask);
        buttonSelectPriority = rootView.findViewById(R.id.buttonSelectPriority);
        textViewPriority = rootView.findViewById(R.id.textViewPriority);
        buttonSelectDeadline = rootView.findViewById(R.id.buttonSelectDeadline);
        textViewDeadline = rootView.findViewById(R.id.textViewDeadline);
        buttonAddTask = rootView.findViewById(R.id.buttonAddTask);

        // Set up ViewModel
        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        // Set up Google Calendar API
        credential = GoogleAccountCredential.usingOAuth2(
                getContext(), Collections.singleton("https://www.googleapis.com/auth/calendar"));
        credential.setSelectedAccountName("dwicandraandika4@gmail.com"); // Set your email here

        try {
            calendarService = new com.google.api.services.calendar.Calendar.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("UTS")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up Select Priority button
        buttonSelectPriority.setOnClickListener(v -> showPriorityPicker());

        // Set up Select Deadline button
        buttonSelectDeadline.setOnClickListener(v -> showDateTimePicker());

        // Set up Add Task button
        buttonAddTask.setOnClickListener(v -> {
            String task = editTextTask.getText().toString();
            if (task.isEmpty()) {
                Toast.makeText(getContext(), "Task cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedPriority == 0) {
                Toast.makeText(getContext(), "Please select a priority", Toast.LENGTH_SHORT).show();
                return;
            }
            if (deadlineTimestamp == 0) {
                Toast.makeText(getContext(), "Please select a deadline", Toast.LENGTH_SHORT).show();
                return;
            }
            if (taskViewModel != null) {
                taskViewModel.addTask(task, selectedPriority, deadlineTimestamp);
                addEventToCalendar(task, deadlineTimestamp);
                editTextTask.setText("");
                textViewPriority.setText("No priority selected");
                textViewDeadline.setText("No deadline selected");
                Toast.makeText(getContext(), "Task \"" + task + "\" has been added successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void showPriorityPicker() {
        String[] priorities = {"High", "Medium", "Low"};
        new AlertDialog.Builder(getContext())
                .setTitle("Select Priority")
                .setItems(priorities, (dialog, which) -> {
                    textViewPriority.setText(priorities[which]);
                    selectedPriority = which + 1; // High = 1, Medium = 2, Low = 3
                })
                .show();
    }

    private void showDateTimePicker() {
        final java.util.Calendar currentDate = java.util.Calendar.getInstance();
        final java.util.Calendar date = java.util.Calendar.getInstance();
        new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                date.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(java.util.Calendar.MINUTE, minute);
                if (date.getTimeInMillis() <= currentDate.getTimeInMillis()) {
                    Toast.makeText(getContext(), "Selected time must be in the future", Toast.LENGTH_SHORT).show();
                } else {
                    deadlineTimestamp = date.getTimeInMillis();
                    textViewDeadline.setText(date.getTime().toString());
                }
            }, currentDate.get(java.util.Calendar.HOUR_OF_DAY), currentDate.get(java.util.Calendar.MINUTE), false).show();
        }, currentDate.get(java.util.Calendar.YEAR), currentDate.get(java.util.Calendar.MONTH), currentDate.get(java.util.Calendar.DATE)).show();
    }

    private void addEventToCalendar(String task, long deadline) {
        Event event = new Event()
                .setSummary(task)
                .setDescription("Task from UTS app");

        Date startDate = new Date();
        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(startDate))
                .setTimeZone("Asia/Jakarta");
        event.setStart(start);

        Date endDate = new Date(deadline);
        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(endDate))
                .setTimeZone("Asia/Jakarta");
        event.setEnd(end);

        new Thread(() -> {
            try {
                calendarService.events().insert("primary", event).execute();
                Log.d("AddTaskFragment", "Event added to Google Calendar");
            } catch (IOException e) {
                Log.e("AddTaskFragment", "Error adding event to Google Calendar", e);
            }
        }).start();
    }
}