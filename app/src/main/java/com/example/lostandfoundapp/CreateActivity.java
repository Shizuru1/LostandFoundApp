package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.lostandfoundapp.data.Item;

public class CreateActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText name, phone, description, date, location;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        radioGroup = findViewById(R.id.radioGroup);
        name = findViewById(R.id.editTextTextPersonName);
        phone = findViewById(R.id.editTextPhone);
        description = findViewById(R.id.editTextTextPersonName2);
        date = findViewById(R.id.editTextDate);
        location = findViewById(R.id.editTextTextPersonName3);
        saveButton = findViewById(R.id.button4);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                boolean lostorfound = radioGroup.getCheckedRadioButtonId() == R.id.radioButton;
                String nameValue = name.getText().toString();
                String phoneValue = phone.getText().toString();
                String descriptionValue = description.getText().toString();
                String dateValue = date.getText().toString();
                String locationValue = location.getText().toString();
                Item item = new Item(0, lostorfound, nameValue, phoneValue, descriptionValue, dateValue, locationValue);
                i.putExtra("create", item);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}