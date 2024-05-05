package com.example.clicker;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.Random;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<Resours> liveDataResourses = new MutableLiveData<>();
    private static Context mcontext;
    private static ViewModel ModelInstanse = null;
    private Resours resours = new Resours();
    private final Random random=new Random();
    private Save save;
    public static ViewModel newInstance(Context context) {
        mcontext=context;
        if (ModelInstanse==null){
            ModelInstanse=new ViewModel();
        }
        return ModelInstanse;
    }
    public void LoadOfSave(){
        save = new Save(mcontext);
        setBalanceRes(save.getBalanceRes());
    }
    public void SaveToSave(){
        save.saveBalanceRes(resours);
    }
    public void setBalanceRes(Resours resours){
        if (resours !=null){
            this.resours = resours;
        }
    }
    public MutableLiveData<Resours> getLiveDataResourses() {
        liveDataResourses.setValue(resours);
        return liveDataResourses;
    }
    public static class Resours {
        private int balance=0;
        private int gather=0;
        private int[] market=new int[7];
        private int usableSlave=0;
        private float volume=0.5f;
        public int getBalance() {
            return balance;
        }
        public int getGather() {
            return gather;
        }
        public int getMarketPos(int position) {
            return market[position];
        }
        public int[] getMarket() {
            return market;
        }
        public int getUsableSlave() {
            return usableSlave;
        }
        public float getVolume() {
            return volume;
        }
        public void setVolume(float volume) {
            this.volume = volume;
        }
        public void setBalance(int balance) {
            this.balance = balance;
        }
        public void setGather(int gather) {
            this.gather = gather;
        }
        public void setMarket(int[] market) {
            this.market = market;
        }
        public void setUsableSlave(int usableSlave) {
            this.usableSlave = usableSlave;
        }
    }
    public boolean incrSlavesPos(){
        if (resours.getUsableSlave()< resours.getMarketPos(2)){
            resours.usableSlave++;
            liveDataResourses.setValue(resours);
            return true;
        }
        return false;
    }
    public boolean decrSlavesPos(){
        if (resours.getUsableSlave()>0){
            resours.usableSlave--;
            liveDataResourses.setValue(resours);
            return true;
        }
        return false;
    }
    public void setVolumeRes(float volume) {
        resours.volume= volume;
        liveDataResourses.setValue(resours);
    }
    public boolean incrCountBuy(int position) {
        int[] cost = mcontext.getResources().getIntArray(R.array.MarketCost);
        if (resours.balance>=cost[position]) {
            if (position==1){
                if (resours.getMarketPos(1)<50){
                    decrBalance(cost[position]);
                    incrMarket(position);
                    return true;
                }
            }else {
                decrBalance(cost[position]);
                incrMarket(position);
                return true;
            }
        }
        return false;
    }
    public void incrBalanceClick(){
        double chance;
        double randomd=random.nextDouble();
        if (resours.getMarketPos(1)>50){
            chance=0.5;
        }
        else{
            chance= resours.getMarketPos(1)*0.01;
        }
        if (randomd<=chance){
            resours.balance+=(1+ resours.getMarketPos(0))*2;
        }else{
            resours.balance+=1000+ resours.getMarketPos(0);
        }
        liveDataResourses.setValue(resours);
    }
    public void incrGatherPlant(){
        resours.gather+=10*(resours.getMarketPos(4)+1);
        liveDataResourses.postValue(resours);
    }
    public void incrBalanceGather(){
        resours.balance+= resours.gather;
        resours.gather=0;
        liveDataResourses.setValue(resours);
    }
    public void decrBalance(int decr){
        if (resours.balance>=decr){
            resours.balance-=decr;
        }
    }
    public void incrMarket(int position){
        resours.market[position]++;
        liveDataResourses.setValue(resours);
    }
}