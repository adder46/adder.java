package com.github.adder46.libadder;

public class HalfAdder {
    private int sum;
    private int carryOut;

    public HalfAdder() {
        this.sum = 0;
        this.carryOut = 0;
    }

    public int getCarryOut() {
        return carryOut;
    }

    public int getSum() {
        return sum;
    }

    public void add(int a, int b) {
        sum = a ^ b;
        carryOut = a & b;
    }
}
