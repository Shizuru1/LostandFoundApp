package com.example.lostandfoundapp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemRoomDatabase extends RoomDatabase {

    public abstract ItemDAO itemDAO();
    private static volatile ItemRoomDatabase INSTANCE;
    static final int THREADS = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(4);

    public static ItemRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ItemRoomDatabase.class, "item_database")
                            .addCallback(databaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriterExecutor.execute(() -> {
                ItemDAO itemDAO = INSTANCE.itemDAO();

                Item item = new Item(0, false, "Name", "0400000000", "Description", "1/1/2001", "Location");
                itemDAO.insert(item);
            });
        }
    };
}
