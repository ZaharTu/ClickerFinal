package com.example.clicker;

public class AllRes {
    protected Slave slave= new Slave();
    public AllRes() {
    }
    protected int balance=0;
    protected int gather=0;
    protected int[] market=new int[7];
    protected float volume=0.5f;
    protected int potato=0;
    protected int[] research=new int[10];
    public static class Slave{
        int[] usableSlave=new int[3];
        int AllSlave=0;
        public int UsableSlaveGet(){
            int slaves = 0;
            for (int i = 0; i < usableSlave.length; i++) {
                slaves+=usableSlave[i];
            }
            return slaves;
        }
    }


}
