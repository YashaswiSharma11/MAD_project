package com.chocolate.madproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnalyticsActivity extends AppCompatActivity {

    TextView totalTextView;
    TextView pendingTextView;
    TextView inProgressTextView;
    TextView resolvedTextView;

    TextView lowTextView;
    TextView mediumTextView;
    TextView highTextView;
    TextView criticalTextView;

    TextView categoryStatsTextView;
    TextView resolutionRateTextView;
    TextView locationStatsTextView;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        totalTextView=findViewById(R.id.totalTextView);
        pendingTextView=findViewById(R.id.pendingTextView);
        inProgressTextView=findViewById(R.id.inProgressTextView);
        resolvedTextView=findViewById(R.id.resolvedTextView);

        lowTextView=findViewById(R.id.lowTextView);
        mediumTextView=findViewById(R.id.mediumTextView);
        highTextView=findViewById(R.id.highTextView);
        criticalTextView=findViewById(R.id.criticalTextView);

        categoryStatsTextView=findViewById(R.id.categoryStatsTextView);
        resolutionRateTextView=findViewById(R.id.resolutionRateTextView);
        locationStatsTextView=findViewById(R.id.locationStatsTextView);

        db=new DatabaseHelper(this);
        loadAnalytics();
    }

    private void loadAnalytics() {

        // Overall statistics

        int total=db.getTotalComplaints();
        int pending=db.getPendingComplaints();
        int inProgress=db.getInProgressComplaints();
        int resolved=db.getResolvedComplaints();

        totalTextView.setText("Total Complaints: " + total);
        pendingTextView.setText("Pending: " + pending);
        inProgressTextView.setText("In Progress: " + inProgress);
        resolvedTextView.setText("Resolved: " + resolved);


        // Severity statistics

        int low=db.getLowPriorityComplaints();
        int medium=db.getMediumPriorityComplaints();
        int high=db.getHighPriorityComplaints();
        int critical=db.getCriticalPriorityComplaints();

        lowTextView.setText("Low: " + low);

        mediumTextView.setText("Medium: " + medium);

        highTextView.setText("High: " + high);

        criticalTextView.setText("Critical: " + critical);


        // Category statistics

        String[] categories = {
                "Roads & Footpaths",
                "Street Lighting",
                "Electricity & Power",
                "Waste & Sanitation",
                "Water Supply & Drainage",
                "Traffic & Road Safety",
                "Public Spaces & Facilities",
                "Public Infrastructure",
                "Public Transport",
                "Education",
                "Healthcare",
                "Civic Utilities & Information",
                "Public Safety Hazards",
                "Other"
        };

        StringBuilder categoryStats=new StringBuilder();
        for (String category : categories){
            int count=db.getCategoryCount(category);
            categoryStats.append(category).append(": ").append(count).append("\n");}
        categoryStatsTextView.setText(categoryStats.toString());


        // Resolution rate

        double rate=db.getResolutionRate();
        resolutionRateTextView.setText(String.format("Resolution Rate: %.1f%%", rate));


        // Location statistics

        String[] states = {
                "Madhya Pradesh",
                "Maharashtra",
                "Gujarat",
                "Rajasthan",
                "Karnataka",
                "Delhi"
        };

        StringBuilder locationStats=new StringBuilder();

        for (String state : states) {
            int count=db.getStateCount(state);
            locationStats.append(state).append(": ").append(count).append("\n");}
        locationStatsTextView.setText(locationStats.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (db != null) {
            loadAnalytics();
        }
    }
}