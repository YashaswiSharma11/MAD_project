package com.chocolate.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OfficialDashboardActivity extends AppCompatActivity {

    TextView totalTextView;
    TextView pendingTextView;
    TextView progressTextView;
    TextView resolvedTextView;
    TextView highPriorityTextView;

    Button viewComplaintsButton;
    Button categoriesButton;
    Button analyticsButton;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_dashboard);

        totalTextView = findViewById(R.id.totalTextView);
        pendingTextView = findViewById(R.id.pendingTextView);
        progressTextView = findViewById(R.id.progressTextView);
        resolvedTextView = findViewById(R.id.resolvedTextView);
        highPriorityTextView = findViewById(R.id.highPriorityTextView);

        viewComplaintsButton =
                findViewById(R.id.viewComplaintsButton);

        categoriesButton =
                findViewById(R.id.categoriesButton);

        analyticsButton =
                findViewById(R.id.analyticsButton);

        db = new DatabaseHelper(this);

        loadStatistics();

        viewComplaintsButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    OfficialDashboardActivity.this,
                    OfficialComplaintsActivity.class
            );

            startActivity(intent);
        });

        categoriesButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    OfficialDashboardActivity.this,
                    LocationAnalysisActivity.class
            );

            startActivity(intent);
        });

        analyticsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(
                                OfficialDashboardActivity.this,
                                AnalyticsActivity.class
                        );

                        startActivity(intent);
                    }
                }
        );
    }

    private void loadStatistics() {

        int total = db.getTotalComplaints();
        int pending = db.getPendingComplaints();
        int inProgress = db.getInProgressComplaints();
        int resolved = db.getResolvedComplaints();
        int highPriority = db.getHighPriorityComplaints();

        totalTextView.setText(
                "Total Complaints: " + total
        );

        pendingTextView.setText(
                "Pending: " + pending
        );

        progressTextView.setText(
                "In Progress: " + inProgress
        );

        resolvedTextView.setText(
                "Resolved: " + resolved
        );

        highPriorityTextView.setText(
                "High Priority: " + highPriority
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (db != null) {
            loadStatistics();
        }
    }
}