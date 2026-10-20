package com.chocolate.madproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CivicConnect.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_NAME = "complaints";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category TEXT, " +
                "issue_type TEXT, " +
                "description TEXT, " +
                "severity TEXT, " +
                "date TEXT, " +
                "state TEXT, " +
                "city TEXT, " +
                "area TEXT, " +
                "image TEXT, " +
                "status TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // INSERT
    public boolean insertComplaint(
            String category,
            String issueType,
            String description,
            String severity,
            String date,
            String state,
            String city,
            String area,
            String image,
            String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("category", category);
        values.put("issue_type", issueType);
        values.put("description", description);
        values.put("severity", severity);
        values.put("date", date);
        values.put("state", state);
        values.put("city", city);
        values.put("area", area);
        values.put("image", image);
        values.put("status", status);

        long result = db.insert(
                TABLE_NAME,
                null,
                values
        );

        return result != -1;
    }

    // SELECT ALL
    public Cursor getAllComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NAME,
                null
        );
    }

    // UPDATE STATUS
    public boolean updateStatus(
            int id,
            String status) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("status", status);

        int result = db.update(
                TABLE_NAME,
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    // DELETE
    public boolean deleteComplaint(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_NAME,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

    // TOTAL COMPLAINTS
    public int getTotalComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME,
                null
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // PENDING
    public int getPendingComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE status = ?",
                new String[]{"Pending"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // IN PROGRESS
    public int getInProgressComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE status = ?",
                new String[]{"In Progress"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // RESOLVED
    public int getResolvedComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE status = ?",
                new String[]{"Resolved"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // HIGH PRIORITY
    public int getHighPriorityComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE severity = ?",
                new String[]{"High"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
    // TOTAL COMPLAINTS FOR LOCATION
    public int getLocationTotal(
            String state,
            String city,
            String area) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE state = ? AND city = ? AND area = ?",
                new String[]{state, city, area}
        );

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    // PENDING FOR LOCATION
    public int getLocationPending(
            String state,
            String city,
            String area) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE state = ? AND city = ? AND area = ?" +
                        " AND status = ?",
                new String[]{
                        state,
                        city,
                        area,
                        "Pending"
                }
        );

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    // IN PROGRESS FOR LOCATION
    public int getLocationInProgress(
            String state,
            String city,
            String area) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE state = ? AND city = ? AND area = ?" +
                        " AND status = ?",
                new String[]{
                        state,
                        city,
                        area,
                        "In Progress"
                }
        );

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    // RESOLVED FOR LOCATION
    public int getLocationResolved(
            String state,
            String city,
            String area) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE state = ? AND city = ? AND area = ?" +
                        " AND status = ?",
                new String[]{
                        state,
                        city,
                        area,
                        "Resolved"
                }
        );

        cursor.moveToFirst();

        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }
    // LOW PRIORITY
    public int getLowPriorityComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE severity = ?",
                new String[]{"Low"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // MEDIUM PRIORITY
    public int getMediumPriorityComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE severity = ?",
                new String[]{"Medium"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // CRITICAL PRIORITY
    public int getCriticalPriorityComplaints() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE severity = ?",
                new String[]{"Critical"}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // RESOLUTION RATE
    public double getResolutionRate() {

        int total = getTotalComplaints();
        int resolved = getResolvedComplaints();

        if (total == 0) {
            return 0;
        }

        return (resolved * 100.0) / total;
    }

    // CATEGORY COUNT
    public int getCategoryCount(String category) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE category = ?",
                new String[]{category}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    // STATE COUNT
    public int getStateCount(String state) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_NAME +
                        " WHERE state = ?",
                new String[]{state}
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
}