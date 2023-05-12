package com.example.lostandfoundapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lostandfoundapp.data.Item;
import com.example.lostandfoundapp.data.ItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button createButton, showButton;
    ItemViewModel itemViewModel;
    LiveData<List<Item>> items;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = findViewById(R.id.button);
        showButton = findViewById(R.id.button2);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        items = itemViewModel.getItems();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(i, 1);
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, ItemActivity.class);
                for (int k = 0; k < items.getValue().size(); k++) {
                    names.add(items.getValue().get(k).getName());
                }
                j.putStringArrayListExtra("names", names);
                startActivityForResult(j, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            itemViewModel.insert((Item) data.getSerializableExtra("create"));
        } else if (requestCode == 2) {
            int pos = data.getIntExtra("pos", 0);
            Intent removeIntent = new Intent(MainActivity.this, RemoveActivity.class);
            removeIntent.putExtra("item", items.getValue().get(pos));
            removeIntent.putExtra("pos", pos);
            startActivityForResult(removeIntent, 3);
        } else if (requestCode == 3) {
            itemViewModel.delete(items.getValue().get(data.getIntExtra("pos", 0)));
        }
    }
}