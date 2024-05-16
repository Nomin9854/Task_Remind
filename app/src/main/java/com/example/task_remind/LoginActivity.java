package com.example.task_remind;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private CheckBox checkBoxRememberMe;
    private TextView textViewForgetPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        textViewForgetPassword = findViewById(R.id.textViewForgetPassword);
        databaseHelper = new DatabaseHelper(this);

        // Set onClickListener for Forget Password text
        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetPasswordDialog();
            }
        });

        // Login button click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (authenticateUser(email, password)) {
                    // Authentication successful, navigate to another activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    // Authentication failed, show error message
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sign Up button click listener
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch SignUpActivity when Sign Up button is clicked
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean authenticateUser(String email, String password) {
        // Check if user exists in the database with the given credentials
        return databaseHelper.authenticateUser(email, password);
    }

    private void showResetPasswordDialog() {
        ResetPasswordActivity dialog = new ResetPasswordActivity(LoginActivity.this);
        dialog.setResetPasswordListener(new ResetPasswordActivity.ResetPasswordListener() {
            @Override
            public void onResetPassword(String email, String newPassword, String confirmPassword) {
                // Validate email, new password, and confirm password
                if (!databaseHelper.checkUserExists(email)) {
                    Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Update password in the database
                    boolean isPasswordUpdated = databaseHelper.updateUserPassword(email, newPassword);
                    if (isPasswordUpdated) {
                        Toast.makeText(LoginActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }
}
