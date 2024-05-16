package com.example.task_remind;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTasksActivity extends AppCompatActivity {

    private EditText editTextTaskName;
    private EditText editTextDate;
    private EditText editTextTime;
    private Button buttonAddReminder;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        buttonAddReminder = findViewById(R.id.buttonAddReminder);

        calendar = Calendar.getInstance();

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        buttonAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReminderDialog();
            }
        });

    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = hourOfDay + ":" + minute;
                        editTextTime.setText(selectedTime);
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void showReminderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog_reminder, null);
        builder.setView(dialogView);

        final Spinner spinnerReminderAt = dialogView.findViewById(R.id.spinnerReminderAt);
        final Spinner spinnerReminderType = dialogView.findViewById(R.id.spinnerReminderType);
        final Spinner spinnerRepeat = dialogView.findViewById(R.id.spinnerRepeat);

        builder.setPositiveButton("Add Reminder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String reminderAt = spinnerReminderAt.getSelectedItem().toString();
                String reminderType = spinnerReminderType.getSelectedItem().toString();
                String repeat = spinnerRepeat.getSelectedItem().toString();

                // Call method to schedule notification based on the selected options
                scheduleNotification(reminderAt, reminderType, repeat);

                String reminderOptions = "Reminder At: " + reminderAt +
                        "\nReminder Type: " + reminderType +
                        "\nRepeat: " + repeat;

                Toast.makeText(AddTasksActivity.this, reminderOptions, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void scheduleNotification(String reminderAt, String reminderType, String repeat) {
        // Implement notification scheduling logic based on the selected options
        // Here you can use AlarmManager to schedule the notification
        // Use the values of reminderAt, reminderType, and repeat to configure the notification behavior
        // Refer to the previous response for how to use AlarmManager to schedule notifications
        // You'll need to calculate the notification trigger time based on the task due date and time
        // and the reminderAt value (e.g., 5 minutes before, 1 hour before, etc.)
        // Then use AlarmManager to schedule the notification
        // Handle the notification content and behavior in the AlarmReceiver class
        // Ensure you handle any necessary permissions and define the AlarmReceiver in the manifest
    }
}
