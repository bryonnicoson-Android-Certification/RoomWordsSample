package com.bryonnicoson.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * This class holds the implementation code for the methods that interact with the database.
 * Using a repository allows us to group the implementation methods together, and allows the
 * WordViewModel to be a clean interface between the rest of the app and the database.
 *
 * For insert, update, delete, and longer-running queries, you must run the database
 * interaction methods in the background.  Typically, all you need to do to implement a database
 * method is to call it on the data access object (DAO), in the background if applicable.
 */

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll() {
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    public void deleteWord(Word word) {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    /**
     * Insert a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Delete all words from database.
     */
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {

        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     * Delete a single word from the database.
     */
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}