package com.example.clicker;

public class AllRes {
    protected Slave slave= new Slave();
    public AllRes() {
    }
    protected int balance=0;
    protected int gather=0;
    protected int[] market=new int[7];
    protected float volume=0.5f;
    public static class Slave{
        int usableSlave=0;
        int AllSlave=0;
    }


}
