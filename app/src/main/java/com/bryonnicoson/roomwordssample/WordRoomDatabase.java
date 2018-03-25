package com.bryonnicoson.roomwordssample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by bryon on 3/23/18.
 */

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    // define DAOs
    public abstract WordDao wordDao();

    private static WordRoomDatabase INSTANCE;

    // create singleton
    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            // todo: implement non-destructive migration
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this project, clear database every time it is created or opened
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // to keep data through app restarts, comment out the following line
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"abstemious", "facetious", "argle-bargle"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {

            // if database is empty, create initial list of words
            if (mDao.getAnyWord().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }
}
