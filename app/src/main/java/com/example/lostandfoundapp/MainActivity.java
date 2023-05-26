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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // Initialize variables
    Button createButton, showButton, mapButton;
    ItemViewModel itemViewModel;
    LiveData<List<Item>> items;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<LatLng> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set variables
        createButton = findViewById(R.id.button);
        showButton = findViewById(R.id.button2);
        mapButton = findViewById(R.id.button5);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        items = itemViewModel.getItems();
        itemViewModel.getItems().observe(this, words -> {
            Log.println(Log.DEBUG, "tag", "items inline count = " + items.getValue().stream().count());
        });

        // navigate to Create Activity
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(i, 1);
            }
        });

        // navigate to Item Activity
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, ItemActivity.class);
                if (items.getValue() != null) {
                    names.clear();
                    names.add("Items");
                    for (int k = 0; k < items.getValue().size(); k++) {
                        names.add(items.getValue().get(k).getName());
                    }
                }
                j.putStringArrayListExtra("names", names);
                startActivityForResult(j, 2);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, MapActivity.class);
                if (items.getValue() != null) {
                    locations.clear();
                    names.clear();
                    for (int l = 0; l < items.getValue().size(); l++) {
                        locations.add(new LatLng(items.getValue().get(l).getLat(), items.getValue().get(l).getLng()));
                        names.add(items.getValue().get(l).getName());
                    }
                }
                k.putParcelableArrayListExtra("locations", locations);
                k.putStringArrayListExtra("names", names);
                startActivity(k);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // create
            if (resultCode == RESULT_OK) {
                // itemViewModel.insert((Item) data.getSerializableExtra("create"));
                Item item = new Item(0, data.getBooleanExtra("bool", false), data.getStringArrayExtra("create")[0], data.getStringArrayExtra("create")[1], data.getStringArrayExtra("create")[2], data.getStringArrayExtra("create")[3], data.getStringArrayExtra("create")[4], data.getDoubleArrayExtra("place")[0], data.getDoubleArrayExtra("place")[1]);
                itemViewModel.insert(item);
            }
        } else if (requestCode == 2) { // pass data to remove activity
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("pos", 0);
                Intent removeIntent = new Intent(MainActivity.this, RemoveActivity.class);
                removeIntent.putExtra("item", items.getValue().get(pos));
                removeIntent.putExtra("pos", pos);
                startActivityForResult(removeIntent, 3);
            }
        } else if (requestCode == 3) { // delete
            if (resultCode == RESULT_OK) {
                itemViewModel.delete(items.getValue().get(data.getIntExtra("pos", 0)));
            }
        }
    }
}