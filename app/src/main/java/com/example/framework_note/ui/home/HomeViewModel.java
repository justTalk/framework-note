package com.example.framework_note.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<String>> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    public void addFakerData(){
        List<String> all = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            all.add("hhhhhhh");
        }
        mText.setValue(all);
    }

    public LiveData<List<String>> getText() {
        return mText;
    }
}