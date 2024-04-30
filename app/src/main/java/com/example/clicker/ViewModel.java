package com.example.clicker;


import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Random;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<ArrayList<Item>> liveDataMarket = new MutableLiveData<>();
    private final MutableLiveData<BalanceRes> liveDataBalance = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Plant>> liveDataPlants = new MutableLiveData<>();
    private ArrayList<Item> items;
    private ArrayList<Plant> plants;
    private static Context mcontext;
    private PlantArrayListSetter plantArrayListSetter;
    private static ViewModel ModelInstanse = null;
    private BalanceRes balanceRes = new BalanceRes();
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
        save.saveBalanceRes(balanceRes);
    }
    public void setBalanceRes(BalanceRes balanceRes){
        if (balanceRes!=null){
            this.balanceRes=balanceRes;
        }
    }
    public MutableLiveData<ArrayList<Item>> getLiveDataArray() {
        if (liveDataMarket.getValue() == null) {
            loadItems();
        }
        return liveDataMarket;
    }
    public MutableLiveData<BalanceRes> getLiveDataBalance() {
        liveDataBalance.setValue(balanceRes);
        return liveDataBalance;
    }
    public MutableLiveData<ArrayList<Plant>> getLiveDataPlants() {
        if (liveDataPlants.getValue() == null) {
            loadPlants();
        }
        return liveDataPlants;
    }

    private void loadPlants() {
        plantArrayListSetter=new PlantArrayListSetter(mcontext, balanceRes.getMarketPos(3));
        plants=plantArrayListSetter.getPlant_Array();
        for (int i = 0; i < balanceRes.getMarketPos(3)+1; i++) {
            plants.get(i).setSlave(balanceRes.getSlaveOnEach()[i]);
            balanceRes.usableSlave-=balanceRes.getSlaveOnEach()[i];
        }
        liveDataPlants.setValue(plants);
    }
    public static class BalanceRes {
        private int balance=0;
        private int gather=0;
        private int[] market=new int[6];
        private int usableSlave=0;
        private int[] SlaveOnEach=new int[4];
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
        public int[] getSlaveOnEach() {
            return SlaveOnEach;
        }
        public void setSlaveOnEach(int[] slaveOnEach) {
            SlaveOnEach = slaveOnEach;
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
    private void loadItems() {
        items=new ItemArrayListSetter(mcontext, balanceRes.getMarket()).getItem_Array();
        liveDataMarket.setValue(items);
    }
    public boolean incrSlavesPos(int position){
        if (balanceRes.getUsableSlave()>0&& balanceRes.usableSlave<= balanceRes.getMarketPos(2)){
            balanceRes.SlaveOnEach[position]++;
            balanceRes.usableSlave--;
            plants.get(position).IncrSlave();
            liveDataBalance.setValue(balanceRes);
            liveDataPlants.setValue(plants);
            return true;
        }
        return false;
    }
    public boolean decrSlavesPos(int position){
        if (balanceRes.getUsableSlave()< balanceRes.getMarketPos(2)&&plants.get(position).getSlave()>0){
            balanceRes.SlaveOnEach[position]--;
            balanceRes.usableSlave++;
            plants.get(position).DincrSlave();
            liveDataBalance.setValue(balanceRes);
            liveDataPlants.setValue(plants);
            return true;
        }
        return false;
    }
    public void startSlavesPos(int position){
        plants.get(position).start();
        liveDataPlants.setValue(plants);
    }
    public void setProgressBar(int position, ProgressBar progressBar){
        plants.get(position).setProgressBar(progressBar);
        liveDataPlants.setValue(plants);
    }

    public void incrCountBuy(int position) {
        int cost = items.get(position).getCost();
        if (items != null && position >= 0 && position < items.size() && balanceRes.balance>=cost) {
            switch (position){
                case 3:
                    if (balanceRes.getMarketPos(3)<3){
                        decrBalance(cost);
                        items.get(position).IncrCountBuy();
                        liveDataMarket.setValue(items);
                        incrMarket(position);
                        loadPlants();
                    }
                    break;
                case 1:
                    if (balanceRes.getMarketPos(1)<100){
                        decrBalance(cost);
                        items.get(position).IncrCountBuy();
                        liveDataMarket.setValue(items);
                        incrMarket(position);
                    }
                    break;
                case 2:
                    decrBalance(cost);
                    items.get(position).IncrCountBuy();
                    liveDataMarket.setValue(items);
                    incrMarket(position);
                    balanceRes.usableSlave++;
                    liveDataBalance.setValue(balanceRes);
                    break;
                default:
                    decrBalance(cost);
                    items.get(position).IncrCountBuy();
                    liveDataMarket.setValue(items);
                    incrMarket(position);
                    break;
            }
        }
    }
    public void incrBalanceClick(){
        double chance;
        double randomd=random.nextDouble();
        if (balanceRes.getMarketPos(1)>100){
            chance=0.5;
        }
        else{
            chance= balanceRes.getMarketPos(1)*0.005;
        }
        if (randomd<=chance){
            balanceRes.balance+=(1+ balanceRes.getMarketPos(0))*2;
        }else{
            balanceRes.balance+=1000+ balanceRes.getMarketPos(0);
        }
        liveDataBalance.setValue(balanceRes);
    }
    public void incrGatherPlant(){
        balanceRes.gather+=5*(balanceRes.getMarketPos(4)+1);
        liveDataBalance.postValue(balanceRes);
    }
    public void incrBalanceGather(){
        balanceRes.balance+= balanceRes.gather;
        balanceRes.gather=0;
        liveDataBalance.setValue(balanceRes);
    }
    public void decrBalance(int decr){
        if (balanceRes.balance>=decr){
            balanceRes.balance-=decr;
            liveDataBalance.setValue(balanceRes);
        }
    }
    public void incrMarket(int position){
        balanceRes.market[position]++;
        liveDataBalance.setValue(balanceRes);
    }


}
