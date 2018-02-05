package com.merlin.bright.roomwithaview.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by coryb on 2/2/2018.
 */
@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    public abstract WordDAO wordDAO();

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            WordRoomDatabase.class,
                            "word_database"
                    ).addCallback(sRoomDatabaseCallBack).build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallBack =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDBAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
        private final WordDAO mDAO;

        private PopulateDBAsync(WordRoomDatabase db) {
            mDAO = db.wordDAO();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            LiveData<List<Word>> words = mDAO.getAllWords();

            if (words == null) {
                Word word = new Word("Hello");
                mDAO.insert(word);
                Word word2 = new Word("World");
                mDAO.insert(word2);
            }
            return null;
        }
    }
}
