package com.application.brahmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;


public class MainRVInactiveViewholder extends RecyclerView.ViewHolder {
    TextView title, time_remaining;

    public MainRVInactiveViewholder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_title);
        time_remaining = (TextView) itemView.findViewById(R.id.time_remaining);
    }
}