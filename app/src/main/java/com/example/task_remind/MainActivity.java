package com.example.task_remind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView ibAddTasks, ibCurrentTasks, ibCalendar, ibAddTemplates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views for menu items
        ibAddTasks = findViewById(R.id.ibAddTasks);
        ibCurrentTasks = findViewById(R.id.ibCurrentTasks);
        ibCalendar = findViewById(R.id.ibCalendar);
        ibAddTemplates = findViewById(R.id.ibAddTemplates);

        // Set click listeners for menu items
        ibAddTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTasksActivity.class));
            }
        });

        ibCurrentTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CurrentTasksActivity.class));
            }
        });

        ibCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }
        });

        ibAddTemplates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTemplatesActivity.class));
            }
        });
    }
}
