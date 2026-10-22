package com.chocolate.madproject;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class ComplaintActivity extends AppCompatActivity {

    TextView categoryTextView;
    Spinner issueSpinner;
    Spinner stateSpinner;
    Spinner citySpinner;
    Spinner areaSpinner;
    EditText descriptionEditText;
    EditText dateEditText;
    RadioGroup severityGroup;
    Button submitButton;
    DatabaseHelper db;
    String category;
    HashMap<String, String[]> stateCities = new HashMap<>();
    HashMap<String, String[]> cityAreas = new HashMap<>();
    ImageView photoImageView;
    Button photoButton;

    Uri selectedImageUri;

    ActivityResultLauncher<Void> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicturePreview(),
                    bitmap -> {
                        if (bitmap != null) {
                            photoImageView.setImageBitmap(bitmap);
                            photoImageView.setVisibility(View.VISIBLE);
                            selectedImageUri = saveBitmapToCache(bitmap);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        categoryTextView = findViewById(R.id.categoryTextView);
        issueSpinner = findViewById(R.id.issueSpinner);
        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        areaSpinner = findViewById(R.id.areaSpinner);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        severityGroup = findViewById(R.id.severityGroup);
        submitButton = findViewById(R.id.submitButton);
        db = new DatabaseHelper(this);
        photoButton = findViewById(R.id.photoButton);
        photoImageView = findViewById(R.id.photoImageView);

        category = getIntent().getStringExtra("category");
        if (category == null) {
            category = "Other";
        }

        categoryTextView.setText("Report: " + category);

        // Issue Types
        String[] issueTypes = {
                "Select Issue Type",
                "General Issue",
                "Maintenance Issue",
                "Safety Issue",
                "Service Issue",
                "Other"
        };

        ArrayAdapter<String> issueAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                issueTypes
        );
        issueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueSpinner.setAdapter(issueAdapter);

        // States
        String[] states = {
                "Select State",
                "Madhya Pradesh",
                "Maharashtra",
                "Gujarat",
                "Rajasthan",
                "Delhi",
                "Karnataka"
        };

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                states
        );
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        // State -> Cities
        stateCities.put("Madhya Pradesh", new String[]{"Indore", "Bhopal", "Jabalpur", "Gwalior"});
        stateCities.put("Maharashtra", new String[]{"Mumbai", "Pune", "Nagpur", "Nashik"});
        stateCities.put("Gujarat", new String[]{"Ahmedabad", "Surat", "Vadodara", "Rajkot"});
        stateCities.put("Rajasthan", new String[]{"Jaipur", "Jodhpur", "Udaipur", "Kota"});
        stateCities.put("Delhi", new String[]{"New Delhi", "North Delhi", "South Delhi", "East Delhi"});
        stateCities.put("Karnataka", new String[]{"Bengaluru", "Mysuru", "Mangaluru", "Hubballi"});

        // City -> Areas
        cityAreas.put("Indore", new String[]{"Vijay Nagar", "Palasia", "Rau", "Bhanwarkuan", "AB Road"});
        cityAreas.put("Bhopal", new String[]{"MP Nagar", "Arera Colony", "Kolar", "Bairagarh"});
        cityAreas.put("Jabalpur", new String[]{"Wright Town", "Napier Town", "Vijay Nagar"});
        cityAreas.put("Mumbai", new String[]{"Andheri", "Bandra", "Dadar", "Borivali"});
        cityAreas.put("Pune", new String[]{"Hinjawadi", "Kothrud", "Hadapsar", "Viman Nagar"});
        cityAreas.put("Ahmedabad", new String[]{"Navrangpura", "Maninagar", "Satellite", "Vastrapur"});
        cityAreas.put("Surat", new String[]{"Adajan", "Vesu", "Katargam", "Varachha"});
        cityAreas.put("Jaipur", new String[]{"Malviya Nagar", "Vaishali Nagar", "Mansarovar", "C-Scheme"});
        cityAreas.put("New Delhi", new String[]{"Connaught Place", "Karol Bagh", "Dwarka", "Rohini"});
        cityAreas.put("Bengaluru", new String[]{"Whitefield", "Koramangala", "Indiranagar", "Electronic City"});

        // State selection
        stateSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedState = parent.getItemAtPosition(position).toString();
                String[] cities = stateCities.get(selectedState);

                if (cities != null) {
                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                            ComplaintActivity.this,
                            android.R.layout.simple_spinner_item,
                            cities
                    );
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    citySpinner.setAdapter(cityAdapter);
                } else {
                    citySpinner.setAdapter(new ArrayAdapter<>(ComplaintActivity.this, android.R.layout.simple_spinner_item, new String[]{"Select City"}));
                    areaSpinner.setAdapter(new ArrayAdapter<>(ComplaintActivity.this, android.R.layout.simple_spinner_item, new String[]{"Select Area"}));
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // City selection
        citySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                String[] areas = cityAreas.get(selectedCity);

                if (areas != null) {
                    ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(
                            ComplaintActivity.this,
                            android.R.layout.simple_spinner_item,
                            areas
                    );
                    areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);
                } else {
                    areaSpinner.setAdapter(new ArrayAdapter<>(ComplaintActivity.this, android.R.layout.simple_spinner_item, new String[]{"Select Area"}));
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Date Picker
        dateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    ComplaintActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateEditText.setText(date);
                    },
                    year, month, day
            );
            dialog.show();
        });

        // Submit
        submitButton.setOnClickListener(v -> {
            String issueType = issueSpinner.getSelectedItem().toString();
            String description = descriptionEditText.getText().toString();
            int selectedId = severityGroup.getCheckedRadioButtonId();
            String date = dateEditText.getText().toString();
            String state = stateSpinner.getSelectedItem().toString();
            String city = citySpinner.getSelectedItem().toString();
            String area = areaSpinner.getSelectedItem().toString();

            if (description.isEmpty()) {
                Toast.makeText(ComplaintActivity.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == -1) {
                Toast.makeText(ComplaintActivity.this, "Please select severity", Toast.LENGTH_SHORT).show();
                return;
            }

            if (date.isEmpty()) {
                Toast.makeText(ComplaintActivity.this, "Please select date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (state.equals("Select State") || city.equals("Select City") || area.equals("Select Area")) {
                Toast.makeText(ComplaintActivity.this, "Please select location", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadio = findViewById(selectedId);
            String severity = selectedRadio.getText().toString();

            String image = "";
            if (selectedImageUri != null) {
                image = selectedImageUri.toString();
            }

            boolean inserted = db.insertComplaint(
                    category,
                    issueType,
                    description,
                    severity,
                    date,
                    state,
                    city,
                    area,
                    image,
                    "Pending"
            );

            if (inserted) {
                Toast.makeText(ComplaintActivity.this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ComplaintActivity.this, "Failed to submit complaint", Toast.LENGTH_SHORT).show();
            }
        });

        // Direct Camera Launch
        photoButton.setOnClickListener(v -> cameraLauncher.launch(null));
    }

    private Uri saveBitmapToCache(Bitmap bitmap) {
        try {
            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "complaint_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.close();
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}