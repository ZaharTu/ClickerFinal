package com.example.clicker;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.Random;

public class AllResRepository {
    private static AllResRepository instance;
    private final Save save;
    private AllRes res;
    private Context context;
    private final Random random=new Random();
    private AllRes.Slave slave;

    /**LIVE_DATA*/
    private final MutableLiveData<Integer> balanceLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> gatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> potatoLiveData = new MutableLiveData<>();
    private final MutableLiveData<AllRes.Slave> slaveLiveData = new MutableLiveData<>();
    private final MutableLiveData<Float> volumeLiveData = new MutableLiveData<>();
    private final MutableLiveData<int[]> marketLiveData = new MutableLiveData<>();


    public static AllResRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AllResRepository(context);
        }
        return instance;
    }
    private AllResRepository(Context context) {
        this.context=context;
        res=new AllRes();
        slave=res.slave;
        save=new Save(context,this);
    }
    public void LoadOfSave(){
        save.getBalanceRes();
    }
    public void SaveToSave(){save.saveBalanceRes();}

    /**GETTERS*/
    public int getBalance() {
        return res.balance;
    }
    public int getGather() {
        return res.gather;
    }
    public int getPotato() {
        return res.potato;
    }
    public AllRes.Slave getSlave() {
        return slave;
    }
    public int[] getMarket() {
        return res.market;
    }
    public int[] getUsableSlave() {
        return slave.usableSlave;
    }
    public float getVolume() {
        return res.volume;
    }
    public AllRes getRes() {
        return res;
    }
    public MutableLiveData<Integer> getBalanceLiveData() {
        return balanceLiveData;
    }
    public MutableLiveData<Integer> getGatherLiveData() {
        return gatherLiveData;
    }
    public MutableLiveData<Integer> getPotatoLiveData() {
        return potatoLiveData;
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


    /**VOLUME*/
    public void setVolume(float volume) {
        res.volume = volume;
        volumeLiveData.postValue(res.volume);
    }


    /**BALANCE*/
    public void setBalance(int balance) {
        res.balance = balance;
        balanceLiveData.postValue(res.balance);
    }
    public boolean incrBalanceClick(){
        double chance= res.market[1]*0.01;
        double randomd=random.nextDouble();
        if (randomd<=chance){
            res.balance+=(1+ res.market[0])*2;
            balanceLiveData.setValue(res.balance);
            return true;
        }else{
            res.balance+=1+res.market[0];
            balanceLiveData.setValue(res.balance);
            return false;
        }
    }
    public void incrBalanceGather(){
        res.balance+=res.gather;
        res.gather=0;
        balanceLiveData.setValue(res.balance);
        gatherLiveData.setValue(res.gather);
    }
    public void incrBalancePotato(){
        int decr=slave.usableSlave[2];
        if (res.potato>0){
            if ((res.potato-decr)>0){
                res.potato-=decr;
                res.gather+=(10+(res.market[6])*5)*decr;
            }else {
                res.potato=0;
                res.gather+=(10+(res.market[6])*5)*(decr-res.potato);
            }
            gatherLiveData.postValue(res.gather);
            potatoLiveData.postValue(res.potato);
        }

    }
    public void decrBalance(int decr){
        if (res.balance>=decr){
            res.balance-=decr;
            balanceLiveData.setValue(res.balance);
        }
    }

    /**GATHER*/
    public void setGather(int gather) {
        res.gather = gather;
        gatherLiveData.postValue(res.gather);
    }
    public void incrGatherPlant(){
        res.gather+=5+(res.market[4])*15;
        gatherLiveData.postValue(res.gather);
    }



    /**MARKET*/
    public void setMarket(int[] market) {
        res.market = market;
        marketLiveData.postValue(res.market);
    }
    public boolean incrCountBuy(int position) {
        int[] cost = context.getResources().getIntArray(R.array.MarketCost);
        if (res.balance>=(cost[position]*(int)(res.market[position]/10+1))) {
            if (position==1){
                if (res.market[1]<50){
                    decrBalance(cost[position]*(int)(res.market[position]/10+1));
                    incrMarket(position);
                    return true;
                }
            }else if (position==2){
                slave.AllSlave++;
                decrBalance(cost[position]*(int)(res.market[position]/10+1));
                incrMarket(position);
                slaveLiveData.setValue(slave);
                return true;
            }
            else {
                decrBalance(cost[position]*(int)(res.market[position]/10+1));
                incrMarket(position);
                return true;
            }
        }
        return false;
    }
    public void incrMarket(int position){
        res.market[position]++;
        marketLiveData.setValue(res.market);
    }



    /**SLAVE*/
    public void setUsableSlave(int[] usableSlave) {
        slave.usableSlave = usableSlave;
        slave.AllSlave=res.market[2];
        slaveLiveData.postValue(slave);
    }
    public boolean incrSlavesPos(int position){
        int slaveuse = 0;
        for (int i = 0; i < slave.usableSlave.length; i++) {
            slaveuse+=slave.usableSlave[i];
        }
        if (slaveuse<res.market[2]){
            slave.usableSlave[position]++;
            slaveLiveData.setValue(slave);
            return true;
        }
        return false;
    }
    public boolean decrSlavesPos(int position){
        if (slave.usableSlave[position]>0){
            slave.usableSlave[position]--;
            slaveLiveData.setValue(slave);
            return true;
        }
        return false;
    }



    /**POTATO*/
    public void setPotato(int potato) {
        res.potato = potato;
        gatherLiveData.postValue(res.gather);
    }
    public void incrPotato(){
        if (res.potato<(res.market[5]*1000+slave.usableSlave[1]*150)){
            res.potato+=1+res.market[3]+res.market[4];
            potatoLiveData.postValue(res.potato);
        }
    }
    public void MaxPotato(){
        if (res.potato>(res.market[5]*1000+slave.usableSlave[2]*150)){
            res.potato=res.market[5]*1000+slave.usableSlave[2]*150;
            potatoLiveData.setValue(res.potato);
        }
    }


}
