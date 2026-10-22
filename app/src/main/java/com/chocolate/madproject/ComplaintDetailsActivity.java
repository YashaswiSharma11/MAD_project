package com.chocolate.madproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class ComplaintDetailsActivity extends AppCompatActivity {

    TextView complaintIdTextView;
    TextView categoryTextView;
    TextView issueTypeTextView;
    TextView descriptionTextView;
    TextView severityTextView;
    TextView dateTextView;
    TextView locationTextView;
    TextView statusTextView;

    ImageView complaintImageView;

    Button mapButton;
    Button deleteButton;

    DatabaseHelper db;

    int complaintId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        complaintIdTextView=findViewById(R.id.complaintIdTextView);

        categoryTextView=findViewById(R.id.categoryTextView);

        issueTypeTextView=findViewById(R.id.issueTypeTextView);

        descriptionTextView=findViewById(R.id.descriptionTextView);

        severityTextView=findViewById(R.id.severityTextView);

        dateTextView=findViewById(R.id.dateTextView);

        locationTextView=findViewById(R.id.locationTextView);

        statusTextView=findViewById(R.id.statusTextView);

        complaintImageView=findViewById(R.id.complaintImageView);

        mapButton=findViewById(R.id.mapButton);

        deleteButton=findViewById(R.id.deleteButton);

        db=new DatabaseHelper(this);

        complaintId=getIntent().getIntExtra("complaint_id", -1);

        if (complaintId == -1) {
            Toast.makeText(this, "Complaint not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadComplaint();

        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean deleted=db.deleteComplaint(complaintId);
                        if (deleted) {
                            Toast.makeText(ComplaintDetailsActivity.this,"Complaint deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(ComplaintDetailsActivity.this, "Failed to delete complaint", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void loadComplaint() {

        Cursor cursor=db.getAllComplaints();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            if (id == complaintId) {
                String category=cursor.getString(cursor.getColumnIndexOrThrow("category")
                        );
                String issueType=cursor.getString(cursor.getColumnIndexOrThrow("issue_type")
                        );
                String description=cursor.getString(cursor.getColumnIndexOrThrow("description")
                        );
                String severity=cursor.getString(cursor.getColumnIndexOrThrow("severity")
                        );
                String date=cursor.getString(cursor.getColumnIndexOrThrow("date")
                        );
                String location=cursor.getString(cursor.getColumnIndexOrThrow("location")
                        );

                String status=cursor.getString(cursor.getColumnIndexOrThrow("status")
                        );
                String image=cursor.getString(cursor.getColumnIndexOrThrow("image")
                        );

                complaintIdTextView.setText("Complaint ID: CC" + id);

                categoryTextView.setText("Category: " + category);

                issueTypeTextView.setText("Issue: " + issueType);

                descriptionTextView.setText("Description: " + description);

                severityTextView.setText("Severity: " + severity);

                dateTextView.setText("Date: " + date);

                locationTextView.setText("Location: " + location);

                statusTextView.setText("Status: " + status);
                if (image != null && !image.isEmpty()) {
                    complaintImageView.setImageURI(Uri.parse(image));
                    complaintImageView.setVisibility(
                            View.VISIBLE
                    );
                }
                break;
            }
        }
        cursor.close();
    }
}