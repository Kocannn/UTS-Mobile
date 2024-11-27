package com.example.uts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Menetapkan listener untuk item yang dipilih di BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Ganti switch dengan if-else
            if (item.getItemId() == R.id.nav_todo) {
                selectedFragment = new TodoListFragment(); // Fragment untuk daftar todo
            } else if (item.getItemId() == R.id.nav_completed) {
                selectedFragment = new CompletedTasksFragment(); // Fragment untuk task yang selesai
            } else if (item.getItemId() == R.id.nav_add) {
                selectedFragment = new AddTaskFragment(); // Fragment untuk menambah task
            }

            // Ganti fragment yang ditampilkan
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Set fragment default
        bottomNavigationView.setSelectedItemId(R.id.nav_todo);
    }
}
