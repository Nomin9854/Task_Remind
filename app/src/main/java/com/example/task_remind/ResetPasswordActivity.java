package com.example.task_remind;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Dialog {

    private ResetPasswordListener resetPasswordListener;
    private EditText editTextResetEmail;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonChangePassword;

    public ResetPasswordActivity(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextResetEmail = findViewById(R.id.editTextResetEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextResetEmail.getText().toString().trim();
                String newPassword = editTextNewPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (!email.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    resetPasswordListener.onResetPassword(email, newPassword, confirmPassword);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface ResetPasswordListener {
        void onResetPassword(String email, String newPassword, String confirmPassword);
    }

    public void setResetPasswordListener(ResetPasswordListener listener) {
        this.resetPasswordListener = listener;
    }
}
