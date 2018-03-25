package com.bryonnicoson.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * The WordViewModel provides the interface between the UI and the data layer of the app,
 * represented by the repository.
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    // Using LiveData and caching what getAllWords() returns has benefits:
    // - can put observer on the data (instead of polling) and only update UI when data changes
    // - repository is completely separated from the UI through the ViewModel
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }

}
