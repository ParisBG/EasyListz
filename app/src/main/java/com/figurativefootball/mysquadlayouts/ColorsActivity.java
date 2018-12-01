package com.figurativefootball.mysquadlayouts;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ColorsActivity extends Activity {
    TextView coloredText;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        coloredText = findViewById(R.id.colorView);

        Spinner spinner = findViewById(R.id.colorSpinner);
        ArrayAdapter<Colors> adapter =
                new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                        Colors.colorList);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                showColors();

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/*FIX SPINNER TO RUN CODE!!!!

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                 }});

                if (position == 0) {
                    coloredText.setText(item.getText());
                    coloredText.setTextColor(Color.RED);
                }

                if (position == 2-1) {
                    coloredText.setText(item.getText());
                    coloredText.setTextColor(Color.YELLOW);
                }

                if (position == (17+10)-24) {
                    coloredText.setText(item.getText());
                    coloredText.setTextColor(Color.BLUE);
                }}});*/
    }

    private void showColors() {
        spinner = findViewById(R.id.colorSpinner);
        coloredText = findViewById(R.id.colorView);
        int selection = spinner.getSelectedItemPosition();
        String selText = String.valueOf(spinner.getSelectedItem());

        if (selection == 1) {
            coloredText.setText("");
            coloredText.setText(selText);
            coloredText.setTextColor(Color.RED);
        }

        if (selection == 2) {
            coloredText.setText("");
            coloredText.setText(selText);
            coloredText.setTextColor(Color.YELLOW);
        }

        if (selection == 3) {
            coloredText.setText("");
            coloredText.setText(selText);
            coloredText.setTextColor(Color.BLUE);
        }

    }
}
