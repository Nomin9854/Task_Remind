package com.example.task_remind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class TemplateAdapter extends ArrayAdapter<Template> {

    private Context context;
    private List<Template> templates;

    public TemplateAdapter(@NonNull Context context, List<Template> templates) {
        super(context, 0, templates);
        this.context = context;
        this.templates = templates;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.activity_template_item, parent, false);
        }

        final Template currentTemplate = templates.get(position);

        TextView templateNameTextView = listItemView.findViewById(R.id.templateNameTextView);
        templateNameTextView.setText(currentTemplate.getTemplateName());

        ImageButton deleteButton = listItemView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the template from the list
                templates.remove(position);
                notifyDataSetChanged(); // Notify adapter of data change
            }
        });

        return listItemView;
    }
}

