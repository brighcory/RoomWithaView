package com.merlin.bright.roomwithaview.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by coryb on 2/2/2018.
 */

public class WordRepository {
    private WordDAO mWordDAO;
    private LiveData<List<Word>> mAllWords;
    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDAO = db.wordDAO();
        mAllWords = mWordDAO.getAllWords();
    }


    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insert(final Word word){
        new insertAsyncTask(mWordDAO).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDAO mAsyncTaskDAO;

        insertAsyncTask(WordDAO dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    public void delete(final Word word){
        new deleteAsyncTask(mWordDAO).execute(word);
    }

    private class deleteAsyncTask extends AsyncTask<Word, Void, Void > {
        WordDAO mWordDAO;
        public deleteAsyncTask(WordDAO wordDAO) {
            mWordDAO = wordDAO;
        }

        @Override
        protected Void doInBackground(Word... params) {
            mWordDAO.delete(params[0]);
            return null;
        }
    }
}
