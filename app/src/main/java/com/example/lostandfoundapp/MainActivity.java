package com.example.lostandfoundapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = findViewById(R.id.button);
        showButton = findViewById(R.id.button2);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        items = itemViewModel.getItems();
        itemViewModel.getItems().observe(this, words -> {
            Log.println(Log.DEBUG, "tag", "items inline count = " + items.getValue().stream().count());
        });

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
                if (items.getValue() != null) {
                    names.clear();
                    for (int k = 0; k < items.getValue().size(); k++) {
                        names.add(items.getValue().get(k).getName());
                    }
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
            if (resultCode == RESULT_OK) {
                // itemViewModel.insert((Item) data.getSerializableExtra("create"));
                Item item = new Item(0, data.getBooleanExtra("bool", false), data.getStringArrayExtra("create")[0], data.getStringArrayExtra("create")[1], data.getStringArrayExtra("create")[2], data.getStringArrayExtra("create")[3], data.getStringArrayExtra("create")[4]);
                itemViewModel.insert(item);
                List<Item> blobs = itemViewModel.getItems().getValue();
                Log.e("egg", "egg");
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("pos", 0);
                Intent removeIntent = new Intent(MainActivity.this, RemoveActivity.class);
                removeIntent.putExtra("item", items.getValue().get(pos));
                removeIntent.putExtra("pos", pos);
                startActivityForResult(removeIntent, 3);
            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                itemViewModel.delete(items.getValue().get(data.getIntExtra("pos", 0)));
            }
        }
    }
}