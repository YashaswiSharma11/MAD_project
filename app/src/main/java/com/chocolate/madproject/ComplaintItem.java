package com.chocolate.madproject;

public class ComplaintItem {
    private final int id;
    private final String category;
    private final String issueType;
    private final String severity;
    private final String date;
    private final String status;

    public ComplaintItem(int id, String category, String issueType, String severity, String date, String status) {
        this.id = id;
        this.category = category;
        this.issueType = issueType;
        this.severity = severity;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getSeverity() {
        return severity;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}