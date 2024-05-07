package com.example.clicker;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<Integer> balanceLiveData;
    private final MutableLiveData<Integer> gatherLiveData;
    private final MutableLiveData<Integer> potatoLiveData;
    private final MutableLiveData<AllRes.Slave> slaveLiveData;
    private final MutableLiveData<Float> volumeLiveData;
    private final MutableLiveData<int[]> marketLiveData;
    public ViewModel(Context context) {
        AllResRepository repository = AllResRepository.getInstance(context);
        potatoLiveData=repository.getPotatoLiveData();
        balanceLiveData= repository.getBalanceLiveData();
        gatherLiveData= repository.getGatherLiveData();
        slaveLiveData= repository.getSlaveLiveData();
        volumeLiveData= repository.getVolumeLiveData();
        marketLiveData= repository.getMarketLiveData();
    }

    public MutableLiveData<Integer> getPotatoLiveData() {
        return potatoLiveData;
    }
    public MutableLiveData<Integer> getBalanceLiveData() {
        return balanceLiveData;
    }
    public MutableLiveData<Integer> getGatherLiveData() {
        return gatherLiveData;
    }
    public MutableLiveData<AllRes.Slave> getSlaveLiveData() {
        return slaveLiveData;
    }
    public MutableLiveData<Float> getVolumeLiveData() {
        return volumeLiveData;
    }
    public MutableLiveData<int[]> getMarketLiveData() {
        return marketLiveData;
    }
}