package com.chocolate.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CitizenHomeActivity extends AppCompatActivity {

    Button reportButton;
    Button myComplaintsButton;
    Button trackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_home);

        reportButton=findViewById(R.id.reportButton);
        myComplaintsButton=findViewById(R.id.myComplaintsButton);
        trackButton=findViewById(R.id.trackButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CitizenHomeActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        myComplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CitizenHomeActivity.this, MyComplaintsActivity.class);
                startActivity(intent);
            }
        });

        trackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(CitizenHomeActivity.this, TrackComplaintActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}