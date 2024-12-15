package com.example.uts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnTaskClickListener onCompleteListener; // Listener for the complete button
    private OnTaskClickListener onDeleteListener;   // Listener for the delete icon

    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }

    public TaskAdapter(List<Task> tasks, OnTaskClickListener completeListener, OnTaskClickListener deleteListener) {
        this.tasks = tasks;
        this.onCompleteListener = completeListener; // Only provided if the complete button is needed
        this.onDeleteListener = deleteListener;
    }

    public void updateTasks(List<Task> newTasks) {
        tasks.clear();
        tasks.addAll(newTasks);
        notifyDataSetChanged();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskText.setText(task.getTask());

        // Show the complete button only if the listener is provided
        if (onCompleteListener != null) {
            holder.completeButton.setVisibility(View.VISIBLE);
            holder.completeButton.setOnClickListener(v -> onCompleteListener.onTaskClick(position));
        } else {
            holder.completeButton.setVisibility(View.GONE);
        }

        // Show the delete icon if the listener is provided
        if (onDeleteListener != null) {
            holder.deleteIcon.setVisibility(View.VISIBLE);
            holder.deleteIcon.setOnClickListener(v -> onDeleteListener.onTaskClick(position));
        } else {
            holder.deleteIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        Button completeButton;
        ImageView deleteIcon;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            completeButton = itemView.findViewById(R.id.completeButton);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}