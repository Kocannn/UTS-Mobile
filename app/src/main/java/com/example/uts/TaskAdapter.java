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

    private List<String> tasks;
    private OnTaskClickListener onCompleteListener; // Listener untuk tombol Selesai
    private OnTaskClickListener onDeleteListener;   // Listener untuk ikon Hapus

    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }

    public TaskAdapter(List<String> tasks, OnTaskClickListener completeListener, OnTaskClickListener deleteListener) {
        this.tasks = tasks;
        this.onCompleteListener = completeListener; // Hanya diberikan jika tombol Selesai diperlukan
        this.onDeleteListener = deleteListener;
    }

    public void updateTasks(List<String> newTasks) {
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
        holder.taskText.setText(tasks.get(position));

        // Tampilkan tombol Selesai hanya jika listener disediakan
        if (onCompleteListener != null) {
            holder.completeButton.setVisibility(View.VISIBLE);
            holder.completeButton.setOnClickListener(v -> onCompleteListener.onTaskClick(position));
        } else {
            holder.completeButton.setVisibility(View.GONE);
        }

        // Tampilkan ikon Hapus jika listener disediakan
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
