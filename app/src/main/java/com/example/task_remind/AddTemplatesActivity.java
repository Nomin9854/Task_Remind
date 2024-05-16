package com.example.task_remind;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AddTemplatesActivity extends AppCompatActivity implements TemplateFormDialogFragmentActivity.TemplateFormDialogListener {

    private List<Template> templateList;
    private TemplateAdapter templateAdapter;
    private TextView textNoTemplates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_templates);

        // Initialize list of templates
        templateList = new ArrayList<>();

        // Initialize adapter
        templateAdapter = new TemplateAdapter(this, templateList);

        // Find ListView and set adapter
        ListView listView = findViewById(R.id.listViewTemplates);
        listView.setAdapter(templateAdapter);

        // Find No Templates TextView
        textNoTemplates = findViewById(R.id.textNoTemplates);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the template form dialog
                showTemplateFormDialog();
            }
        });

        // Check initial visibility of No Templates text
        updateNoTemplatesVisibility();
    }

    private void showTemplateFormDialog() {
        TemplateFormDialogFragmentActivity dialog = new TemplateFormDialogFragmentActivity();
        dialog.show(getSupportFragmentManager(), "TemplateFormDialog");
    }

    @Override
    public void onTemplateAdded(String templateName) {
        // Create a new Template object and add to the list
        Template newTemplate = new Template(templateName);
        templateList.add(newTemplate);

        // Notify adapter that the data set has changed
        templateAdapter.notifyDataSetChanged();

        // Display a toast message
        Toast.makeText(this, "Template Added: " + templateName, Toast.LENGTH_SHORT).show();

        // Update visibility of No Templates text based on list size
        updateNoTemplatesVisibility();
    }

    private void updateNoTemplatesVisibility() {
        if (templateList.isEmpty()) {
            // No templates in the list, show the "No Templates" text
            textNoTemplates.setVisibility(View.VISIBLE);
        } else {
            // Templates are present in the list, hide the "No Templates" text
            textNoTemplates.setVisibility(View.GONE);
        }
    }
}
