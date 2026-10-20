package com.chocolate.madproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TrackComplaintActivity extends AppCompatActivity {

    EditText complaintIdEditText;
    Button trackButton;
    TextView resultTextView;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);

        complaintIdEditText =
                findViewById(R.id.complaintIdEditText);

        trackButton =
                findViewById(R.id.trackButton);

        resultTextView =
                findViewById(R.id.resultTextView);

        db = new DatabaseHelper(this);

        trackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String input =
                                complaintIdEditText
                                        .getText()
                                        .toString()
                                        .trim();

                        if (input.isEmpty()) {

                            Toast.makeText(
                                    TrackComplaintActivity.this,
                                    "Please enter complaint ID",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        int complaintId;

                        try {
                            complaintId =
                                    Integer.parseInt(input);
                        } catch (NumberFormatException e) {

                            Toast.makeText(
                                    TrackComplaintActivity.this,
                                    "Enter a valid complaint ID",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        trackComplaint(complaintId);
                    }
                }
        );
    }

    private void trackComplaint(int complaintId) {

        Cursor cursor =
                db.getAllComplaints();

        boolean found = false;

        while (cursor.moveToNext()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

            if (id == complaintId) {

                String category =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("category")
                        );

                String issueType =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("issue_type")
                        );

                String status =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("status")
                        );

                resultTextView.setText(
                        "Complaint ID: CC" + id +
                                "\n\nCategory: " + category +
                                "\nIssue: " + issueType +
                                "\n\nCurrent Status: " + status
                );

                found = true;
                break;
            }
        }

        cursor.close();

        if (!found) {

            resultTextView.setText(
                    "Complaint not found."
            );
        }
    }
}