package com.chocolate.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    Button roadsButton, streetLightButton, electricityButton;
    Button wasteButton, waterButton, trafficButton;
    Button publicSpaceButton, infrastructureButton;
    Button transportButton, educationButton, healthcareButton;
    Button civicButton, safetyButton, otherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        roadsButton=findViewById(R.id.roadsButton);
        streetLightButton=findViewById(R.id.streetLightButton);
        electricityButton=findViewById(R.id.electricityButton);
        wasteButton=findViewById(R.id.wasteButton);
        waterButton=findViewById(R.id.waterButton);
        trafficButton=findViewById(R.id.trafficButton);
        publicSpaceButton=findViewById(R.id.publicSpaceButton);
        infrastructureButton=findViewById(R.id.infrastructureButton);
        transportButton=findViewById(R.id.transportButton);
        educationButton=findViewById(R.id.educationButton);
        healthcareButton=findViewById(R.id.healthcareButton);
        civicButton=findViewById(R.id.civicButton);
        safetyButton=findViewById(R.id.safetyButton);
        otherButton=findViewById(R.id.otherButton);

        roadsButton.setOnClickListener(v -> openComplaint("Roads & Footpaths"));
        streetLightButton.setOnClickListener(v -> openComplaint("Street Lighting"));
        electricityButton.setOnClickListener(v -> openComplaint("Electricity & Power"));
        wasteButton.setOnClickListener(v -> openComplaint("Waste & Sanitation"));
        waterButton.setOnClickListener(v -> openComplaint("Water Supply & Drainage"));
        trafficButton.setOnClickListener(v -> openComplaint("Traffic & Road Safety"));
        publicSpaceButton.setOnClickListener(v -> openComplaint("Public Spaces & Facilities"));
        infrastructureButton.setOnClickListener(v -> openComplaint("Public Infrastructure"));
        transportButton.setOnClickListener(v -> openComplaint("Public Transport"));
        educationButton.setOnClickListener(v -> openComplaint("Education"));
        healthcareButton.setOnClickListener(v -> openComplaint("Healthcare"));
        civicButton.setOnClickListener(v -> openComplaint("Civic Utilities & Information"));
        safetyButton.setOnClickListener(v -> openComplaint("Public Safety Hazards"));
        otherButton.setOnClickListener(v -> openComplaint("Other"));
    }

    private void openComplaint(String category) {
        Intent intent=new Intent(CategoryActivity.this, ComplaintActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}