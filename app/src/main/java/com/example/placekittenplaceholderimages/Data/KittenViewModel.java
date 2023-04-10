package com.example.placekittenplaceholderimages.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class KittenViewModel extends ViewModel {

    public MutableLiveData<ArrayList<KittenItem>>kittenItems = new MutableLiveData<>();

    public MutableLiveData<KittenItem> selectedKittenItem = new MutableLiveData< >();





}
