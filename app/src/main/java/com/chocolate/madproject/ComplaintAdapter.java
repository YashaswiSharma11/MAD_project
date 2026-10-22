package com.chocolate.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class ComplaintAdapter extends ArrayAdapter<ComplaintItem> {

    public ComplaintAdapter(@NonNull Context context, @NonNull List<ComplaintItem> complaints) {
        super(context, 0, complaints);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_complaint, parent, false);
        }

        ComplaintItem item = getItem(position);
        if (item != null) {
            TextView itemIdTextView = view.findViewById(R.id.itemIdTextView);
            TextView itemStatusTextView = view.findViewById(R.id.itemStatusTextView);
            TextView itemCategoryTextView = view.findViewById(R.id.itemCategoryTextView);
            TextView itemIssueTextView = view.findViewById(R.id.itemIssueTextView);
            TextView itemDateTextView = view.findViewById(R.id.itemDateTextView);
            TextView itemSeverityTextView = view.findViewById(R.id.itemSeverityTextView);

            itemIdTextView.setText("CC" + item.getId());
            itemCategoryTextView.setText(item.getCategory());
            itemIssueTextView.setText(item.getIssueType());
            itemDateTextView.setText("📅 " + item.getDate());

            // Status Styling
            String status = item.getStatus();
            itemStatusTextView.setText(status);
            if ("Resolved".equalsIgnoreCase(status)) {
                itemStatusTextView.setBackgroundResource(R.drawable.bg_pill_resolved);
                itemStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.status_resolved));
            } else if ("In Progress".equalsIgnoreCase(status)) {
                itemStatusTextView.setBackgroundResource(R.drawable.bg_pill_progress);
                itemStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.status_in_progress));
            } else {
                itemStatusTextView.setBackgroundResource(R.drawable.bg_pill_pending);
                itemStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.status_pending));
            }

            // Severity Styling
            String severity = item.getSeverity();
            itemSeverityTextView.setText(severity);
            if ("Critical".equalsIgnoreCase(severity)) {
                itemSeverityTextView.setBackgroundResource(R.drawable.bg_severity_critical);
                itemSeverityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.severity_critical));
            } else if ("High".equalsIgnoreCase(severity)) {
                itemSeverityTextView.setBackgroundResource(R.drawable.bg_severity_high);
                itemSeverityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.severity_high));
            } else if ("Medium".equalsIgnoreCase(severity)) {
                itemSeverityTextView.setBackgroundResource(R.drawable.bg_severity_medium);
                itemSeverityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.severity_medium));
            } else {
                itemSeverityTextView.setBackgroundResource(R.drawable.bg_severity_low);
                itemSeverityTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.severity_low));
            }
        }

        return view;
    }
}