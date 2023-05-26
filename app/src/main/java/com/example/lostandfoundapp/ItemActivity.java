package com.example.lostandfoundapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lostandfoundapp.data.Item;
import com.example.lostandfoundapp.data.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    // Initialize variables
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // Set variables
        spinner = findViewById(R.id.spinner);
        Intent intent = getIntent();
        ArrayList<String> names = intent.getStringArrayListExtra("names");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ItemActivity.this, android.R.layout.simple_spinner_dropdown_item, names);

        // Initialize spinner
        spinner.setAdapter(adapter);
        int initPosition = spinner.getSelectedItemPosition();
        spinner.setSelection(initPosition, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("pos", position - 1);
                setResult(RESULT_OK, i);
                finish();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing
            }
        });
    }
}