package com.github.adder46.libadder;

public class RippleCarryAdder {
    private FullAdder[] adders;

    public RippleCarryAdder(int length) {
        this.adders = new FullAdder[length];
        for (int i = 0; i < length; i++) {
            this.adders[i] = new FullAdder();
        }
    }

    public int add(int a, int b) throws AdditionOverflowException {
        for (int i = 0; i < adders.length; i++) {
            int bit1 = (a >> i) & 1;
            int bit2 = (b >> i) & 1;
            adders[i].add(bit1, bit2);
            try {
                adders[i + 1].setCarryIn(adders[i].getCarryOut());
            } catch (ArrayIndexOutOfBoundsException e) {
                if (adders[adders.length - 1].getCarryOut() == 1) {
                    throw new AdditionOverflowException("Overflow.");
                }
            }
        }
        return getResult();
    }

    private int getResult() {
        int result = 0;
        for (int i = adders.length - 1; i >= 0; i--) {
            result <<= 1;
            result |= adders[i].getSum();
        }
        return result;
    }
}
