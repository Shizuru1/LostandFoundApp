package com.example.lostandfoundapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    ItemDAO itemDAO;
    LiveData<List<Item>> itemList;

    public ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        itemDAO = db.itemDAO();
        itemList = itemDAO.getItems();
    }

    public LiveData<List<Item>> getItems() {
        return itemList;
    }

    public void insert(Item item) {
        ItemRoomDatabase.databaseWriterExecutor.execute(() -> {
            itemDAO.insert(item);
        });
    }

    public void delete(Item item) {
        ItemRoomDatabase.databaseWriterExecutor.execute(() -> {
            itemDAO.delete(item);
        });
    }
}
