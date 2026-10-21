package com.chocolate.madproject;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OfficialComplaintDetailsActivity extends AppCompatActivity {

    TextView complaintIdTextView;
    TextView categoryTextView;
    TextView issueTypeTextView;
    TextView descriptionTextView;
    TextView severityTextView;
    TextView dateTextView;
    TextView locationTextView;
    TextView statusTextView;

    ImageView complaintImageView;

    Spinner statusSpinner;

    Button updateButton;
    Button deleteButton;

    DatabaseHelper db;

    int complaintId;

    String currentStatus = "Pending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_complaint_details);

        complaintIdTextView=findViewById(R.id.complaintIdTextView);
        categoryTextView=findViewById(R.id.categoryTextView);
        issueTypeTextView=findViewById(R.id.issueTypeTextView);
        descriptionTextView=findViewById(R.id.descriptionTextView);
        severityTextView=findViewById(R.id.severityTextView);
        dateTextView=findViewById(R.id.dateTextView);
        locationTextView=findViewById(R.id.locationTextView);
        statusTextView=findViewById(R.id.statusTextView);
        complaintImageView=findViewById(R.id.complaintImageView);

        statusSpinner=findViewById(R.id.statusSpinner);

        updateButton=findViewById(R.id.updateButton);
        deleteButton=findViewById(R.id.deleteButton);
        db = new DatabaseHelper(this);

        complaintId=getIntent().getIntExtra("complaint_id", -1);

        if (complaintId == -1) {
            Toast.makeText(this, "Complaint not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadComplaint();
        String[] statuses = {
                "Pending",
                "In Progress",
                "Resolved"
        };

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statusSpinner.setAdapter(adapter);

        // Set spinner to current status
        if (currentStatus.equals("Pending")) {
            statusSpinner.setSelection(0);
        } else if (currentStatus.equals("In Progress")) {
            statusSpinner.setSelection(1);
        } else if (currentStatus.equals("Resolved")) {
            statusSpinner.setSelection(2);
        }

        // UPDATE STATUS
        updateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String status=statusSpinner.getSelectedItem().toString();

                        boolean updated=db.updateStatus(complaintId, status);

                        if (updated) {
                            currentStatus=status;
                            statusTextView.setText("Current Status: " + status);

                            Toast.makeText(OfficialComplaintDetailsActivity.this, "Status updated successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(OfficialComplaintDetailsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // DELETE COMPLAINT
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean deleted=db.deleteComplaint(complaintId);
                        if (deleted) {
                            Toast.makeText(OfficialComplaintDetailsActivity.this, "Complaint deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(OfficialComplaintDetailsActivity.this, "Failed to delete complaint", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void loadComplaint() {

        Cursor cursor=db.getAllComplaints();

        while (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndexOrThrow("id");

            if (id == complaintId) {
                String category=cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String issueType=cursor.getString(cursor.getColumnIndexOrThrow("issue_type"));
                String description=cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String severity=cursor.getString(cursor.getColumnIndexOrThrow("severity"));
                String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String state=cursor.getString(cursor.getColumnIndexOrThrow("state"));
                String city=cursor.getString(cursor.getColumnIndexOrThrow("city"));
                String area=cursor.getString(cursor.getColumnIndexOrThrow("area"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                currentStatus=cursor.getString(cursor.getColumnIndexOrThrow("status"));
                complaintIdTextView.setText("Complaint ID: CC" + id);
                categoryTextView.setText("Category: " + category);
                issueTypeTextView.setText("Issue: " + issueType);
                descriptionTextView.setText("Description: " + description);
                severityTextView.setText("Severity: " + severity);
                dateTextView.setText("Date: " + date);
                locationTextView.setText("State: " + state + "\nCity: " + city + "\nArea: " + area);
                statusTextView.setText("Current Status: " + currentStatus);

                if (image != null && !image.isEmpty()) {
                    complaintImageView.setImageURI(Uri.parse(image));
                    complaintImageView.setVisibility(View.VISIBLE);
                } else {
                    complaintImageView.setVisibility(View.GONE);
                }
                break;
            }
        }

        cursor.close();
    }
}