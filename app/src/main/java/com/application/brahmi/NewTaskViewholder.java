package com.application.brahmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;



public class NewTaskViewholder extends RecyclerView.ViewHolder {
    public TextView textView;

    public NewTaskViewholder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }
}