package com.chocolate.madproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyComplaintsActivity extends AppCompatActivity {

    ListView complaintsListView;
    DatabaseHelper db;

    ArrayList<ComplaintItem> complaintList;
    ArrayList<Integer> complaintIds;

    ComplaintAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaints);

        complaintsListView = findViewById(R.id.complaintsListView);

        db = new DatabaseHelper(this);

        complaintList = new ArrayList<>();
        complaintIds = new ArrayList<>();

        adapter = new ComplaintAdapter(this, complaintList);

        complaintsListView.setAdapter(adapter);

        complaintsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        int complaintId = complaintIds.get(position);

                        Intent intent = new Intent(
                                MyComplaintsActivity.this,
                                ComplaintDetailsActivity.class
                        );

                        intent.putExtra("complaint_id", complaintId);

                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadComplaints();
    }

    private void loadComplaints() {

        complaintList.clear();
        complaintIds.clear();

        Cursor cursor = db.getAllComplaints();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id")
            );

            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow("category")
            );

            String issueType = cursor.getString(
                    cursor.getColumnIndexOrThrow("issue_type")
            );

            String severity = cursor.getString(
                    cursor.getColumnIndexOrThrow("severity")
            );

            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow("date")
            );

            String status = cursor.getString(
                    cursor.getColumnIndexOrThrow("status")
            );

            complaintIds.add(id);

            complaintList.add(
                    new ComplaintItem(
                            id,
                            category,
                            issueType,
                            severity,
                            date,
                            status
                    )
            );
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }
}