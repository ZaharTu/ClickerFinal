package com.example.clicker;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.Random;

public class AllResRepository {
    private static AllResRepository instance;
    private final Save save;
    private final AllRes res;
    private final Context context;
    private final Random random=new Random();
    private final AllRes.Neighbor neighbor;
    private final int[] ResearchCost;
    public long timeBefore;
    /**LIVE_DATA*/
    private final MutableLiveData<Integer> balanceLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> gatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> potatoLiveData = new MutableLiveData<>();
    private final MutableLiveData<AllRes.Neighbor> neighborLiveData = new MutableLiveData<>();
    private final MutableLiveData<Float> volumeAllLiveData = new MutableLiveData<>();
    private final MutableLiveData<Float> volumeBackLiveData = new MutableLiveData<>();
    private final MutableLiveData<int[]> marketLiveData = new MutableLiveData<>();
    private final MutableLiveData<boolean[]> researchLiveData = new MutableLiveData<>();


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
        neighbor =res.neighbor;
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
    public AllRes.Neighbor getNeighbor() {
        return neighbor;
    }
    public int[] getMarket() {
        return res.market;
    }
    public int[] getUsableNeighbor() {
        return neighbor.usableNeighbor;
    }
    public float getVolumeAll() {
        return res.volumeAll;
    }
    public float getVolumeBack() {
        return res.volumeBack;
    }
    public boolean[] getResearch(){return res.research;}
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
    public MutableLiveData<AllRes.Neighbor> getNeighborLiveData() {
        return neighborLiveData;
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


    /**VOLUMEALL*/
    public void setVolumeAll(float volume) {
        res.volumeAll = volume;
        volumeAllLiveData.postValue(res.volumeAll);
    }
    /**VOLUMEALL*/
    public void setVolumeBack(float volume) {
        res.volumeBack = volume;
        volumeBackLiveData.postValue(res.volumeBack);
    }
    /**BALANCE*/
    public void setBalance(int balance) {
        res.balance = balance;
        balanceLiveData.postValue(res.balance);
        Log.d("aboba",""+balance);
    }
    public int[] incrBalanceTime(){
        long time = System.currentTimeMillis()-timeBefore;
        int timeabsence = (int) (time/60000);
        int incrBalance=0;
        if (neighbor.usableNeighbor[0]>0) incrBalance=(5 + res.market[4] * 15 * (neighbor.usableNeighbor[0] / 10 + 1))*timeabsence;
        return new int[]{timeabsence, incrBalance};
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
        int decr= neighbor.usableNeighbor[2];
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
    public void incrGatherPlant() {
        int incrGather = 5 + res.market[4] * 15 * (neighbor.usableNeighbor[0] / 10 + 1);
        if (res.research[4]) {
            double chance = res.market[1] * 0.01;
            double randomd = random.nextDouble();
            if (chance >= randomd) {
                incrGather *= 2;
            }
        }

        if (res.research[9]) {
            incrGather *= 2;
        }

        res.gather += incrGather;
        gatherLiveData.postValue(res.gather);
    }




    /**MARKET*/
    public void setMarket(int[] market) {
        res.market = market;
        marketLiveData.postValue(res.market);
    }
    public boolean incrCountBuy(int position) {
        int[] cost = context.getResources().getIntArray(R.array.MarketCost);
        int currentCost = cost[position] * (res.market[position] / 10 + 1);
        if (res.balance >= currentCost) {
            switch (position) {
                case 1:
                    if (res.market[1] < 50) {
                        decrBalance(currentCost);
                        incrMarket(position);
                        return true;
                    }
                    break;
                case 2:
                    neighbor.AllNeighbor++;
                    decrBalance(currentCost);
                    incrMarket(position);
                    neighborLiveData.setValue(neighbor);
                    return true;
                default:
                    decrBalance(currentCost);
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
        neighbor.usableNeighbor = usableSlave;
        neighbor.AllNeighbor =res.market[2];
        neighborLiveData.postValue(neighbor);
    }
    public boolean incrSlavesPos(int position){
        int slaveuse = 0;
        for (int i = 0; i < neighbor.usableNeighbor.length; i++) {
            slaveuse+= neighbor.usableNeighbor[i];
        }
        if (slaveuse<res.market[2]){
            neighbor.usableNeighbor[position]++;
            neighborLiveData.setValue(neighbor);
            return true;
        }
        return false;
    }
    public boolean decrSlavesPos(int position){
        if (neighbor.usableNeighbor[position]>0){
            neighbor.usableNeighbor[position]--;
            neighborLiveData.setValue(neighbor);
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
        int potatoAdd = 1 + res.market[3] + res.market[4];
        if (res.research[7]) {
            potatoAdd = 1 + res.market[3] * 2 + res.market[4];
        }

        int maxPotato = res.market[5] * 1000 + neighbor.usableNeighbor[1] * (res.research[4] ? 500 : 250);
        res.potato = Math.min(res.potato + potatoAdd, maxPotato);

        potatoLiveData.postValue(res.potato);
    }
    public void MaxPotato() {
        if (res.research[2]) {
            int maxPotato = res.market[5] * 1000 + neighbor.usableNeighbor[1] * (res.research[4] ? 500 : 250);
            if (res.potato > maxPotato) {
                res.potato = maxPotato;
                potatoLiveData.setValue(res.potato);
            }
        }
    }
    public boolean decrPotato(int decr){
        if (res.potato>=decr){
            res.potato-=decr;
            potatoLiveData.postValue(res.potato);
            return true;
        }
        return false;
    }

    /**RESEARCH*/
    public void setResearch(boolean[] research){
        res.research=research;
        researchLiveData.postValue(research);
    }
    private void ResearchPos(int position){
        res.research[position]=true;
        researchLiveData.setValue(res.research);
    }
    public boolean getResearched(int position){
        return res.research[position];
    }
    public boolean TryResearch(int position) {
        if (!res.research[position]) {
            boolean success = false;

            if (position <= 2) {
                success = decrBalance(ResearchCost[position]);
            } else if (position <= 9) {
                success = decrPotato(ResearchCost[position]);
            }
            if (success) {
                ResearchPos(position);
                return true;
            }
        }
        return false;
    }

}
