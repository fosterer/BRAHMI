package com.application.brahmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class NewTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        SharedPreferences myPreferences = getSharedPreferences("tasks", MODE_PRIVATE);

        // Setting up list
        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();

        tasks.add("Cooking");
        times.add(myPreferences.getLong("Cooking", (long) (25 * 60 * 1000)));

        tasks.add("Washing");
        times.add(myPreferences.getLong("Washing", (long) (35 * 60 * 1000)));

        tasks.add("Excercise");
        times.add(myPreferences.getLong("Excercise", (long) (60 * 60 * 1000)));

        StringTokenizer tokenizer = new StringTokenizer(myPreferences.getString("tasks", ""), "|", false);

        while (tokenizer.hasMoreTokens()) {
            String task = tokenizer.nextToken();

            tasks.add(task);
            times.add(myPreferences.getLong(task, 0));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NewTaskAdapter(this, tasks, times));
    }
}