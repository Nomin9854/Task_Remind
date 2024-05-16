package com.example.task_remind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;

    // User table constants
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Template table constants
    public static final String TABLE_TEMPLATES = "templates";
    public static final String COLUMN_TEMPLATE_ID = "id"; // Template table primary key
    public static final String COLUMN_TEMPLATE_NAME = "template_name";
    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_DUE_TIME = "due_time";
    public static final String COLUMN_REMINDER_AT = "reminder_at";
    public static final String COLUMN_REMINDER_TYPE = "reminder_type";
    public static final String COLUMN_REPEAT = "repeat";

    // SQL queries to create tables
    private static final String SQL_CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                    COLUMN_PASSWORD + " TEXT)";

    private static final String SQL_CREATE_TEMPLATES_TABLE =
            "CREATE TABLE " + TABLE_TEMPLATES + " (" +
                    COLUMN_TEMPLATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Auto-incrementing primary key
                    COLUMN_TEMPLATE_NAME + " TEXT)";


    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_NAME + " TEXT, " +
                    COLUMN_DUE_DATE + " TEXT, " +
                    COLUMN_DUE_TIME + " TEXT, " + // New column
                    COLUMN_REMINDER_AT + " TEXT, " +
                    COLUMN_REMINDER_TYPE + " TEXT, " +
                    COLUMN_REPEAT + " TEXT)";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_TEMPLATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPLATES);
        onCreate(db);
    }

    // User-related operations

    public boolean insertUser(String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = database.insert(TABLE_USERS, null, values);
        database.close();
        return result != -1;
    }

    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] projection = {COLUMN_EMAIL, COLUMN_PASSWORD};
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = database.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean isAuthenticated = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        database.close();
        return isAuthenticated;
    }

    public boolean updateUserPassword(String email, String newPassword) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        int rowsUpdated = database.update(TABLE_USERS, values, selection, selectionArgs);
        database.close();
        return rowsUpdated > 0;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] projection = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = database.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean userExists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        database.close();
        return userExists;
    }

    // Template-related operations

    public boolean addTemplate(String templateName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMPLATE_NAME, templateName);
        long result = db.insert(TABLE_TEMPLATES, null, values);
        db.close();
        return result != -1;
    }

    public List<Template> getAllTemplates() {
        List<Template> templateList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEMPLATES, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndexTemplateName = cursor.getColumnIndex(COLUMN_TEMPLATE_NAME);

            do {
                String templateName = cursor.getString(columnIndexTemplateName);
                Template template = new Template(templateName);
                templateList.add(template);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return templateList;
    }

    public long insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getTaskName());
        values.put(COLUMN_DUE_DATE, task.getDueDate());
        values.put(COLUMN_DUE_TIME, task.getDueTime()); // Insert dueTime
        values.put(COLUMN_REMINDER_AT, task.getReminderAt());
        values.put(COLUMN_REMINDER_TYPE, task.getReminderType());
        values.put(COLUMN_REPEAT, task.getRepeat());

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    // Method to retrieve all tasks from the database
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                task.setTaskName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_DUE_DATE)));
                task.setDueTime(cursor.getString(cursor.getColumnIndex(COLUMN_DUE_TIME))); // Get dueTime
                task.setReminderAt(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_AT)));
                task.setReminderType(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TYPE)));
                task.setRepeat(cursor.getString(cursor.getColumnIndex(COLUMN_REPEAT)));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

}
