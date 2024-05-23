package com.example.clicker;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.Random;

public class AllResRepository {
    private static AllResRepository instance;
    private final Save save;
    private final AllRes res;
    private final Context context;
    private final Random random=new Random();
    private final AllRes.Slave slave;
    private final int[] ResearchCost;
    /**LIVE_DATA*/
    private final MutableLiveData<Integer> balanceLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> gatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> potatoLiveData = new MutableLiveData<>();
    private final MutableLiveData<AllRes.Slave> slaveLiveData = new MutableLiveData<>();
    private final MutableLiveData<Float> volumeLiveData = new MutableLiveData<>();
    private final MutableLiveData<int[]> marketLiveData = new MutableLiveData<>();
    private final MutableLiveData<int[]> researchLiveData = new MutableLiveData<>();


    public static AllResRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AllResRepository(context);
        }
        return instance;
    }
    private AllResRepository(Context context) {
        this.context=context;
        ResearchCost=context.getResources().getIntArray(R.array.ResearchCost);
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
    public int[] getResearch(){return res.research;}
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
    public MutableLiveData<int[]> getResearchLiveData() {
        return researchLiveData;
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
    public void incrBalanceClick(){
        double chance= res.market[1]*0.01;
        double randomd=random.nextDouble();
        if (randomd<=chance){
            res.balance+=(1+ res.market[0])*2;
            balanceLiveData.postValue(res.balance);
        }else{
            res.balance+=1+res.market[0];
            balanceLiveData.postValue(res.balance);
        }
    }
    public void incrBalanceGather(){
        res.balance+=res.gather;
        res.gather=0;
        balanceLiveData.setValue(res.balance);
        gatherLiveData.setValue(res.gather);
    }
    public void incrBalanceMarketPlace(){
        int decr=slave.usableSlave[2];
            if (decrPotato(decr)){
                res.gather+=(10+(res.market[6])*5)*decr;
                gatherLiveData.postValue(res.gather);
            }
    }
    public boolean decrBalance(int decr){
        if (res.balance>=decr){
            res.balance-=decr;
            balanceLiveData.setValue(res.balance);
            return true;
        }
        return false;
    }

    /**GATHER*/
    public void setGather(int gather) {
        res.gather = gather;
        gatherLiveData.postValue(res.gather);
    }
    public void incrGatherPlant(){
        int incrGather;
        if (res.research[4]==1){
            double chance= res.market[1]*0.01;
            double randomd=random.nextDouble();
            incrGather=5+(res.market[4])*15*(slave.usableSlave[0]/10+1);
            if (res.research[9]==1) incrGather*=2;
            if (chance>=randomd)incrGather*=2;
        }else {
            incrGather=5+(res.market[4])*15*(slave.usableSlave[0]/10+1);
            if (res.research[9]==1) incrGather*=2;
        }
        res.gather+=incrGather;
        gatherLiveData.postValue(res.gather);
    }



    /**MARKET*/
    public void setMarket(int[] market) {
        res.market = market;
        marketLiveData.postValue(res.market);
    }
    public boolean incrCountBuy(int position) {
        int[] cost = context.getResources().getIntArray(R.array.MarketCost);
        if (res.balance>=(cost[position]* (res.market[position]/10+1))) {
            if (position==1){
                if (res.market[1]<50){
                    decrBalance(cost[position]* (res.market[position]/10+1));
                    incrMarket(position);
                    return true;
                }
            }else if (position==2){
                slave.AllSlave++;
                decrBalance(cost[position]*(res.market[position]/10+1));
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
        potatoLiveData.postValue(res.potato);
    }
    public void incrPotato(){
        int potatoAdd=(1+res.market[3]+res.market[4]);
        if (res.research[7]==1){
            potatoAdd=(1+res.market[3]*2+res.market[4]);
        }
        if (res.research[3]==1 &&
                res.potato+potatoAdd<(res.market[5]*1000+slave.usableSlave[1]*500)){
            res.potato+=potatoAdd;
            potatoLiveData.postValue(res.potato);
        }else if (res.research[3]==0 &&
                res.potato+potatoAdd<(res.market[5]*1000+slave.usableSlave[1]*250) ) {
            res.potato+=potatoAdd;
            potatoLiveData.postValue(res.potato);
        }
    }
    public void MaxPotato(){
        if (res.research[3]==1 &&
                res.potato>(res.market[5]*1000+slave.usableSlave[1]*500)){
            res.potato=res.market[5]*1000+slave.usableSlave[1]*500;
            potatoLiveData.setValue(res.potato);
        } else if (res.research[3]==0 &&
                res.potato>(res.market[5]*1000+slave.usableSlave[1]*250) ) {
            res.potato=res.market[5]*1000+slave.usableSlave[1]*250;
            potatoLiveData.setValue(res.potato);
        }
    }
    public boolean decrPotato(int decr){
        if (res.potato>=decr){
            res.potato-=decr;
            potatoLiveData.setValue(res.potato);
            return true;
        }
        return false;
    }

    /**RESEARCH*/
    public void setResearch(int[] research){
        res.research=research;
        researchLiveData.postValue(research);
    }
    private void ResearchPos(int position){
        res.research[position]=1;
        researchLiveData.setValue(res.research);
    }
    public boolean getResearched(int position){
        return res.research[position] == 1;
    }
    public boolean TryResearch(int position){
        switch (position){
            case 0:
            case 1:
            case 2:
                if (res.research[position]<1){
                    if (decrBalance(ResearchCost[position])){
                        ResearchPos(position);
                        return true;
                }

            }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                if (res.research[position]<1){
                    if (decrPotato(ResearchCost[position])){
                        ResearchPos(position);
                        return true;
                    }
                }

        }
        return false;
    }
}
