package com.example.lostandfoundapp.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.Serializable;
import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    ItemRepository itemRepository;
    LiveData<List<Item>> items;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        items = itemRepository.getItems();
    }

    public LiveData<List<Item>> getItems() {
        return items;
    }

    public void insert(Item item) {
        itemRepository.insert(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
