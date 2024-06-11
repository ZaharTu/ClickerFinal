package com.example.clicker;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<Integer> balanceLiveData;
    private final MutableLiveData<Integer> gatherLiveData;
    private final MutableLiveData<Integer> potatoLiveData;
    private final MutableLiveData<AllRes.Neighbor> slaveLiveData;
    private final MutableLiveData<Float> volumeAllLiveData;
    private final MutableLiveData<Float> volumeBackLiveData;
    private final MutableLiveData<int[]> marketLiveData;
    private final MutableLiveData<boolean[]> researchLiveData;
    public ViewModel(Context context) {
        AllResRepository repository = AllResRepository.getInstance(context);
        potatoLiveData= repository.getPotatoLiveData();
        balanceLiveData= repository.getBalanceLiveData();
        gatherLiveData= repository.getGatherLiveData();
        slaveLiveData= repository.getNeighborLiveData();
        volumeAllLiveData= repository.getVolumeAllLiveData();
        volumeBackLiveData= repository.getVolumeBackLiveData();
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
    public MutableLiveData<Float> getVolumeAllLiveData() {
        return volumeAllLiveData;
    }
    public MutableLiveData<Float> getVolumeBackLiveData() {
        return volumeBackLiveData;
    }
    public MutableLiveData<int[]> getMarketLiveData() {
        return marketLiveData;
    }
    public MutableLiveData<boolean[]> getResearchLiveData() {
        return researchLiveData;
    }
}