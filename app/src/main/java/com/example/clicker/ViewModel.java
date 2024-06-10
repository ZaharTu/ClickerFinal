package com.example.clicker;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<Integer> balanceLiveData;
    private final MutableLiveData<Integer> gatherLiveData;
    private final MutableLiveData<Integer> potatoLiveData;
    private final MutableLiveData<AllRes.Neighbor> slaveLiveData;
    private final MutableLiveData<Float> volumeLiveData;
    private final MutableLiveData<int[]> marketLiveData;
    private final MutableLiveData<int[]> researchLiveData;
    public ViewModel(Context context) {
        AllResRepository repository = AllResRepository.getInstance(context);
        potatoLiveData= repository.getPotatoLiveData();
        balanceLiveData= repository.getBalanceLiveData();
        gatherLiveData= repository.getGatherLiveData();
        slaveLiveData= repository.getNeighborLiveData();
        volumeLiveData= repository.getVolumeLiveData();
        marketLiveData= repository.getMarketLiveData();
        researchLiveData= repository.getResearchLiveData();
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
    public MutableLiveData<AllRes.Neighbor> getSlaveLiveData() {
        return slaveLiveData;
    }
    public MutableLiveData<Float> getVolumeLiveData() {
        return volumeLiveData;
    }
    public MutableLiveData<int[]> getMarketLiveData() {
        return marketLiveData;
    }
    public MutableLiveData<int[]> getResearchLiveData() {
        return researchLiveData;
    }
}