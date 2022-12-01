package com.application.brahmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class NewTaskAdapter extends RecyclerView.Adapter<NewTaskViewholder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> tasks;
    private SharedPreferences mySharedPreferences;
    private ArrayList<Long> times;
    private DatabaseHelper dbHandler;

    public NewTaskAdapter(Context context, ArrayList<String> tasks, ArrayList<Long> times) {
        this.inflater = LayoutInflater.from(context);
        //this.dbHandler = DatabaseHelper.getSingleton(context);
        dbHandler= new DatabaseHelper(context);
        this.mySharedPreferences = context.getSharedPreferences("tasks", Context.MODE_PRIVATE);
        this.context = context;
        this.tasks = tasks;
        this.times = times;
    }

    @Override
    public NewTaskViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.newtask_viewholder, parent, false);

        return new NewTaskViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewTaskViewholder holder, final int position) {
        if (position == tasks.size()) {
            holder.textView.setText("Add custom task");
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = inflater.inflate(R.layout.customise_dialog, null);
                    final TextInputEditText taskName =
                            (TextInputEditText) view.findViewById(R.id.task_name);
                    final TextInputEditText time = (TextInputEditText) view.findViewById(R.id.time);

                    new AlertDialog.Builder(context)
                            .setView(view)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (("".equals(taskName.getText().toString())) ||
                                            ("".equals(time.getText().toString()))) {
                                        Toast.makeText(context, "Enter some values in the fields",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                                        System.out.println(Long.valueOf(time.getText().toString()));
                                        if (!mySharedPreferences.getString("tasks", "").toLowerCase()
                                                .contains(taskName.getText().toString()))
                                            editor.putString("tasks",
                                                    mySharedPreferences.getString("tasks", "")
                                                            + "|" + taskName.getText().toString());
                                        editor.putLong(taskName.getText().toString(),
                                                Long.valueOf(time.getText().toString()) * 60000);
                                        editor.apply();

                                        tasks.add(taskName.getText().toString());
                                        times.add(Long.valueOf(time.getText().toString()) * 60000);
                                        notifyDataSetChanged();

                                        dbHandler.addTask(new TaskModal(taskName.getText().toString(),
                                                Long.valueOf(time.getText().toString()) * 60000, System.currentTimeMillis()));

                                        ((Activity) context).setResult(1);
                                        ((Activity) context).finish();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            });
        } else {
            holder.textView.setText(tasks.get(position));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = inflater.inflate(R.layout.customise_dialog, null);
                    final TextInputEditText taskName =
                            (TextInputEditText) view.findViewById(R.id.task_name);
                    final TextInputEditText time =
                            (TextInputEditText) view.findViewById(R.id.time);
                    taskName.setText(tasks.get(holder.getAdapterPosition()));
                    taskName.setEnabled(false);
                    time.setText(String.valueOf((times.get(holder.getAdapterPosition()) / (1000 * 60))));

                    new AlertDialog.Builder(context)
                            .setView(view)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (("".equals(time.getText().toString()))) {
                                        Toast.makeText(context, "Enter some values in the fields",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                                        System.out.println(Long.valueOf(time.getText().toString()));
                                        editor.putLong(taskName.getText().toString(),
                                                Long.valueOf(time.getText().toString()) * 60000);
                                        editor.apply();

                                        dbHandler.addTask(new TaskModal(taskName.getText().toString(),
                                                Long.valueOf(time.getText().toString()) * 60000, System.currentTimeMillis()));

                                        ((Activity) context).setResult(1);
                                        ((Activity) context).finish();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size() + 1;
    }
}