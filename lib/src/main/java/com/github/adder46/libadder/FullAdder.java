package com.github.adder46.libadder;

public class FullAdder {
    private int sum;
    private int carryIn;
    private int carryOut;
    private HalfAdder halfAdder1;
    private HalfAdder halfAdder2;

    public FullAdder() {
        this.sum = 0;
        this.carryIn = 0;
        this.carryOut = 0;
        this.halfAdder1 = new HalfAdder();
        this.halfAdder2 = new HalfAdder();
    }

    public void setCarryIn(int carryIn) {
        this.carryIn = carryIn;
    }

    public int getSum() {
        return sum;
    }

    public int getCarryOut() {
        return carryOut;
    }

    public void add(int a, int b) {
        halfAdder1.add(a, b);
        halfAdder2.add(halfAdder1.getSum(), carryIn);
        sum = halfAdder2.getSum();
        carryOut = halfAdder1.getCarryOut() | halfAdder2.getCarryOut();
    }
}
