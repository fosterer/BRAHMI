package com.application.brahmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private CardView pat,car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pat=(CardView) findViewById(R.id.patient);
        car=(CardView) findViewById(R.id.caretaker);

        pat.setOnClickListener(this);
        car.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId())
        {
            case R.id.patient:
                i=new Intent(this,Dashboard.class);
                Bundle bn=new Bundle();
                i.putExtras(bn);
                startActivity(i);
                break;

            case R.id.caretaker:
                i=new Intent(this,CaretakerDashboard.class);
                Bundle bun=new Bundle();
                i.putExtras(bun);
                startActivity(i);
                break;
        }

    }
}