package com.merlin.bright.roomwithaview.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by coryb on 2/2/2018.
 */

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel( Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>>getAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }

    public void delete(Word word){ mRepository.delete(word);}
}
