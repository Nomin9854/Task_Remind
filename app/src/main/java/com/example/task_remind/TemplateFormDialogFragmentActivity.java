package com.example.task_remind;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TemplateFormDialogFragmentActivity extends DialogFragment {

    private EditText editTextTemplateName;

    public interface TemplateFormDialogListener {
        void onTemplateAdded(String templateName);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_template_form, null);
        editTextTemplateName = view.findViewById(R.id.editTextTemplateName);

        builder.setView(view)
                .setTitle("Add Template")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String templateName = editTextTemplateName.getText().toString().trim();
                        TemplateFormDialogListener listener = (TemplateFormDialogListener) getActivity();
                        if (listener != null) {
                            listener.onTemplateAdded(templateName);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
