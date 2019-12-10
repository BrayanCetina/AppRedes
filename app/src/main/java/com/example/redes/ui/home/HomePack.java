package com.example.redes.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomePack extends ViewModel {

    private MutableLiveData<String> mTex;

    public HomePack(){
        mTex = new MutableLiveData<>();
        mTex.setValue("");
    }
    public LiveData<String> getText(){return mTex;}
}
