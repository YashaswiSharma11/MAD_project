package com.chocolate.madproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class LocationAnalysisActivity extends AppCompatActivity {

    Spinner stateSpinner;

    TextView totalTextView;
    TextView pendingTextView;
    TextView inProgressTextView;
    TextView resolvedTextView;

    TextView cityStatsTextView;
    TextView areaStatsTextView;

    Button viewComplaintsButton;

    DatabaseHelper db;

    HashMap<String, String[]> stateCities=new HashMap<>();
    HashMap<String, String[]> cityAreas=new HashMap<>();

    String[] states = {
            "Select State",
            "Madhya Pradesh",
            "Maharashtra",
            "Gujarat",
            "Rajasthan",
            "Karnataka",
            "Delhi"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_analysis);

        stateSpinner=findViewById(R.id.stateSpinner);

        totalTextView=findViewById(R.id.totalTextView);
        pendingTextView=findViewById(R.id.pendingTextView);
        inProgressTextView=findViewById(R.id.inProgressTextView);
        resolvedTextView=findViewById(R.id.resolvedTextView);

        cityStatsTextView=findViewById(R.id.cityStatsTextView);
        areaStatsTextView=findViewById(R.id.areaStatsTextView);

        viewComplaintsButton=findViewById(R.id.viewComplaintsButton);

        db=new DatabaseHelper(this);
        loadLocationData();
        setupStateSpinner();

        viewComplaintsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedState=stateSpinner.getSelectedItem().toString();
                        if (selectedState.equals("Select State")) {
                            return;
                        }
                        Intent intent=new Intent(LocationAnalysisActivity.this, OfficialComplaintsActivity.class);
                        intent.putExtra("selected_state", selectedState);
                        startActivity(intent);
                    }
                }
        );
    }

    private void loadLocationData() {
        // State -> Cities
        stateCities.put("Madhya Pradesh", new String[]{
                        "Indore",
                        "Bhopal",
                        "Jabalpur",
                        "Gwalior"
                }
        );
        stateCities.put("Maharashtra", new String[]{
                        "Mumbai",
                        "Pune",
                        "Nagpur",
                        "Nashik"
                }
        );
        stateCities.put("Gujarat", new String[]{
                        "Ahmedabad",
                        "Surat",
                        "Vadodara",
                        "Rajkot"
                }
        );

        stateCities.put("Rajasthan", new String[]{
                        "Jaipur",
                        "Jodhpur",
                        "Udaipur",
                        "Kota"
                }
        );

        stateCities.put("Karnataka", new String[]{
                        "Bengaluru",
                        "Mysuru",
                        "Mangaluru",
                        "Hubballi"
                }
        );

        stateCities.put("Delhi",new String[]{
                        "New Delhi",
                        "North Delhi",
                        "South Delhi",
                        "East Delhi"
                }
        );


        // City -> Areas

        cityAreas.put("Indore", new String[]{
                        "Vijay Nagar",
                        "Palasia",
                        "Rau",
                        "Bhanwarkuan",
                        "AB Road"
                }
        );

        cityAreas.put("Bhopal", new String[]{
                        "MP Nagar",
                        "Arera Colony",
                        "Kolar",
                        "Bairagarh"
                }
        );

        cityAreas.put("Jabalpur", new String[]{
                        "Wright Town",
                        "Napier Town",
                        "Civic Centre"
                }
        );

        cityAreas.put("Gwalior", new String[]{
                        "Lashkar",
                        "Thatipur",
                        "Morar"
                }
        );

        cityAreas.put("Mumbai", new String[]{
                        "Andheri",
                        "Bandra",
                        "Dadar",
                        "Borivali"
                }
        );

        cityAreas.put("Pune", new String[]{
                        "Hinjawadi",
                        "Kothrud",
                        "Hadapsar",
                        "Viman Nagar"
                }
        );

        cityAreas.put("Nagpur", new String[]{
                        "Dharampeth",
                        "Sitabuldi",
                        "Manish Nagar"
                }
        );

        cityAreas.put("Nashik", new String[]{
                        "College Road",
                        "Gangapur Road",
                        "Panchavati"
                }
        );

        cityAreas.put("Ahmedabad", new String[]{
                        "Navrangpura",
                        "Maninagar",
                        "Satellite",
                        "Vastrapur"
                }
        );

        cityAreas.put("Surat", new String[]{
                        "Adajan",
                        "Vesu",
                        "Katargam",
                        "Varachha"
                }
        );

        cityAreas.put("Vadodara", new String[]{
                        "Alkapuri",
                        "Manjalpur",
                        "Gotri"
                }
        );

        cityAreas.put("Rajkot", new String[]{
                        "Kalawad Road",
                        "University Road",
                        "Mavdi"
                }
        );

        cityAreas.put("Jaipur", new String[]{
                        "Malviya Nagar",
                        "Vaishali Nagar",
                        "Mansarovar",
                        "C-Scheme"
                }
        );

        cityAreas.put("Jodhpur", new String[]{
                        "Sardarpura",
                        "Ratanada",
                        "Paota"
                }
        );

        cityAreas.put(
                "Udaipur",
                new String[]{
                        "Hiran Magri",
                        "Fatehpura",
                        "Surajpole"
                }
        );

        cityAreas.put(
                "Kota",
                new String[]{
                        "Talwandi",
                        "Mahaveer Nagar",
                        "Vigyan Nagar"
                }
        );

        cityAreas.put(
                "Bengaluru",
                new String[]{
                        "Whitefield",
                        "Koramangala",
                        "Indiranagar",
                        "Electronic City"
                }
        );

        cityAreas.put(
                "Mysuru",
                new String[]{
                        "Vijayanagar",
                        "Kuvempunagar",
                        "Hebbal"
                }
        );

        cityAreas.put(
                "Mangaluru",
                new String[]{
                        "Kadri",
                        "Bejai",
                        "Kottara"
                }
        );

        cityAreas.put(
                "Hubballi",
                new String[]{
                        "Vidya Nagar",
                        "Gokul Road",
                        "Keshwapur"
                }
        );

        cityAreas.put(
                "New Delhi",
                new String[]{
                        "Connaught Place",
                        "Karol Bagh",
                        "Dwarka",
                        "Rohini"
                }
        );

        cityAreas.put(
                "North Delhi",
                new String[]{
                        "Civil Lines",
                        "Model Town",
                        "Kamla Nagar"
                }
        );

        cityAreas.put(
                "South Delhi",
                new String[]{
                        "Saket",
                        "Vasant Kunj",
                        "Hauz Khas"
                }
        );

        cityAreas.put(
                "East Delhi",
                new String[]{
                        "Laxmi Nagar",
                        "Preet Vihar",
                        "Mayur Vihar"
                }
        );
    }

    private void setupStateSpinner() {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        states
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        stateSpinner.setAdapter(adapter);

        stateSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selectedState =
                                parent.getItemAtPosition(position)
                                        .toString();

                        if (selectedState.equals("Select State")) {

                            clearStatistics();
                            return;
                        }

                        loadStateAnalysis(selectedState);
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {

                    }
                }
        );
    }

    private void loadStateAnalysis(String state) {

        int total = 0;
        int pending = 0;
        int inProgress = 0;
        int resolved = 0;

        StringBuilder cityStats =
                new StringBuilder();

        StringBuilder areaStats =
                new StringBuilder();

        String[] cities =
                stateCities.get(state);

        if (cities != null) {

            for (String city : cities) {

                int cityCount =
                        getCityCount(state, city);

                total += cityCount;

                cityStats
                        .append(city)
                        .append(": ")
                        .append(cityCount)
                        .append("\n");

                String[] areas =
                        cityAreas.get(city);

                if (areas != null) {

                    for (String area : areas) {

                        int areaCount =
                                getAreaCount(
                                        state,
                                        city,
                                        area
                                );

                        if (areaCount > 0) {

                            areaStats
                                    .append(city)
                                    .append(" - ")
                                    .append(area)
                                    .append(": ")
                                    .append(areaCount)
                                    .append("\n");
                        }
                    }
                }
            }
        }

        Cursor cursor =
                db.getAllComplaints();

        while (cursor.moveToNext()) {

            String complaintState =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "state"
                            )
                    );

            if (!complaintState.equals(state)) {
                continue;
            }

            String status =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "status"
                            )
                    );

            if (status.equals("Pending")) {
                pending++;
            } else if (status.equals("In Progress")) {
                inProgress++;
            } else if (status.equals("Resolved")) {
                resolved++;
            }
        }

        cursor.close();

        totalTextView.setText(
                "Total Complaints: " + total
        );

        pendingTextView.setText(
                "Pending: " + pending
        );

        inProgressTextView.setText(
                "In Progress: " + inProgress
        );

        resolvedTextView.setText(
                "Resolved: " + resolved
        );

        cityStatsTextView.setText(
                cityStats.length() > 0
                        ? cityStats.toString()
                        : "No complaints found"
        );

        areaStatsTextView.setText(
                areaStats.length() > 0
                        ? areaStats.toString()
                        : "No complaints found"
        );
    }

    private int getCityCount(
            String state,
            String city) {

        Cursor cursor =
                db.getAllComplaints();

        int count = 0;

        while (cursor.moveToNext()) {

            String complaintState =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "state"
                            )
                    );

            String complaintCity =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "city"
                            )
                    );

            if (complaintState.equals(state)
                    && complaintCity.equals(city)) {

                count++;
            }
        }

        cursor.close();

        return count;
    }

    private int getAreaCount(
            String state,
            String city,
            String area) {

        Cursor cursor =
                db.getAllComplaints();

        int count = 0;

        while (cursor.moveToNext()) {

            String complaintState =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "state"
                            )
                    );

            String complaintCity =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "city"
                            )
                    );

            String complaintArea =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "area"
                            )
                    );

            if (complaintState.equals(state)
                    && complaintCity.equals(city)
                    && complaintArea.equals(area)) {

                count++;
            }
        }

        cursor.close();

        return count;
    }

    private void clearStatistics() {

        totalTextView.setText(
                "Total Complaints: 0"
        );

        pendingTextView.setText(
                "Pending: 0"
        );

        inProgressTextView.setText(
                "In Progress: 0"
        );

        resolvedTextView.setText(
                "Resolved: 0"
        );

        cityStatsTextView.setText(
                "City-wise statistics will appear here"
        );

        areaStatsTextView.setText(
                "Area-wise statistics will appear here"
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (stateSpinner != null) {

            String state =
                    stateSpinner.getSelectedItem().toString();

            if (!state.equals("Select State")) {
                loadStateAnalysis(state);
            }
        }
    }
}