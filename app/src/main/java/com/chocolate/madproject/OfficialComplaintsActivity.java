package com.chocolate.madproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OfficialComplaintsActivity extends AppCompatActivity {

    Spinner categorySpinner;
    ListView officialComplaintsListView;

    DatabaseHelper db;

    ArrayList<String> complaints;
    ArrayList<Integer> complaintIds;

    ArrayAdapter<String> complaintsAdapter;

    String selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_complaints);

        categorySpinner=findViewById(R.id.categorySpinner);
        officialComplaintsListView=findViewById(R.id.officialComplaintsListView);
        db=new DatabaseHelper(this);
        complaints=new ArrayList<>();
        complaintIds=new ArrayList<>();
        complaintsAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, complaints);
        officialComplaintsListView.setAdapter(complaintsAdapter);
        selectedState=getIntent().getStringExtra("selected_state");
        if (selectedState == null) {
            selectedState = "All States";
        }

        String[] categories = {
                "All Categories",
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

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        loadComplaints("All Categories", selectedState);
        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCategory = parent.getItemAtPosition(position).toString();
                        loadComplaints(selectedCategory, selectedState);
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                }
        );

        officialComplaintsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        int complaintId=complaintIds.get(position);
                        Intent intent=new Intent(OfficialComplaintsActivity.this, OfficialComplaintDetailsActivity.class);
                        intent.putExtra("complaint_id", complaintId);
                        startActivity(intent);
                    }
                }
        );
    }

    private void loadComplaints(String selectedCategory, String selectedState) {
        complaints.clear();
        complaintIds.clear();

        Cursor cursor=db.getAllComplaints();
        while (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String category=cursor.getString(cursor.getColumnIndexOrThrow("category"));
            String state=cursor.getString(cursor.getColumnIndexOrThrow("state"));
            if (!selectedCategory.equals("All Categories") && !category.equals(selectedCategory)) {
                continue;
            }

            if (!selectedState.equals("All States") && !state.equals(selectedState)) {
                continue;
            }

            String issueType=cursor.getString(cursor.getColumnIndexOrThrow("issue_type"));
            String severity=cursor.getString(cursor.getColumnIndexOrThrow("severity"));
            String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String status=cursor.getString(cursor.getColumnIndexOrThrow("status"));
            complaintIds.add(id);
            complaints.add("CC" + id + "\nCategory: " + category + "\nIssue: " + issueType + "\nSeverity: " + severity + "\nDate: " + date + "\nStatus: " + status);
        }
        cursor.close();
        complaintsAdapter.notifyDataSetChanged();
    }
}