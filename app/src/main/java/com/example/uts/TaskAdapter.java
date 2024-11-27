package com.example.uts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // Data untuk task yang akan ditampilkan di RecyclerView
    private List<String> tasks;
    private OnTaskClickListener onTaskClickListener;

    // Interface untuk mendengarkan klik pada task
    public interface OnTaskClickListener {
        void onTaskClick(int position);  // untuk menghapus task misalnya
    }

    // Konstruktor TaskAdapter
    public TaskAdapter(List<String> tasks, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.onTaskClickListener = listener;
    }

    // Metode untuk memperbarui data dalam RecyclerView
    public void updateTasks(List<String> newTasks) {
        tasks.clear();          // Bersihkan data lama
        tasks.addAll(newTasks); // Tambahkan data baru
        notifyDataSetChanged(); // Beri tahu adapter bahwa data telah berubah
    }

    // Membuat view holder untuk item RecyclerView
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Menginflate layout item_task ke dalam RecyclerView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    // Mengatur data untuk setiap item di RecyclerView
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Menampilkan teks task pada TextView
        holder.taskText.setText(tasks.get(position));

        // Menambahkan listener untuk menghapus task saat gambar delete di klik
        holder.deleteIcon.setOnClickListener(v -> {
            if (onTaskClickListener != null) {
                onTaskClickListener.onTaskClick(position);  // memanggil listener untuk menghapus task
            }
        });
    }

    // Menghitung jumlah item dalam RecyclerView
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // ViewHolder untuk setiap item RecyclerView
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;  // untuk menampilkan teks task
        ImageView deleteIcon;  // untuk tombol hapus task

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);  // menghubungkan dengan TextView
            deleteIcon = itemView.findViewById(R.id.deleteIcon);  // menghubungkan dengan ImageView
        }
    }
}
