package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lostandfoundapp.data.Item;

public class RemoveActivity extends AppCompatActivity {

    TextView text;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        text = findViewById(R.id.textView8);
        removeButton = findViewById(R.id.button3);
        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");
        int pos = intent.getIntExtra("pos", 0);
        String lostorfound = item.isFound() ? "Found" : "Lost";
        String textValue = lostorfound + "\n" + item.getName() + "\n" + item.getPhone() + "\n" + item.getDescription() + "\n" + item.getDate() + "\n" + item.getLocation();
        text.setText(textValue);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                backIntent.putExtra("pos", pos);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });
    }
}