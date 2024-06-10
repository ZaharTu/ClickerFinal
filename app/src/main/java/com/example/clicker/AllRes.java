package com.example.clicker;

public class AllRes {
    protected Neighbor neighbor = new Neighbor();
    public AllRes() {
    }
    protected int balance=0;
    protected int gather=0;
    protected int[] market=new int[7];
    protected float volume=0.5f;
    protected int potato=0;
    protected int[] research=new int[10];
    public static class Neighbor {
        int[] usableNeighbor =new int[3];
        int AllNeighbor =0;
        public int UsableNeighborGet(){
            int slaves = 0;
            for (int j : usableNeighbor) {
                slaves += j;
            }
            return slaves;
        }
    }


}
