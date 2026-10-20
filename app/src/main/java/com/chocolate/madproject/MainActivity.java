package com.chocolate.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button citizenButton, officialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citizenButton = findViewById(R.id.citizenButton);
        officialButton = findViewById(R.id.officialButton);

        citizenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        MainActivity.this,
                        CitizenHomeActivity.class
                );

                startActivity(intent);
            }
        });

        officialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        MainActivity.this,
                        OfficialDashboardActivity.class
                );

                startActivity(intent);
            }
        });
    }
}