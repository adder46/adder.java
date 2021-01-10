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
            /*  We are extracting the bits in reverse, by shifting right `i` positions,
             *  where 0 <= i < 8. In the beginning, i=0, so we are not shifting anything.
             *  However, each time through the loop, the number of positions (`i`) we are
             *  shifting right increases by 1 until it reaches 7. This means that the
             *  previously extracted bit is discarded, and the bit we want to extract next
             *  comes to its place, i.e., to the least significant position. By Boolean ANDing
             *  the resulting number with 1 (mask), the individual bit is effectively extracted
             *  from the number - masking out the rest.
             */
            int bit1 = (a >> i) & 1;
            int bit2 = (b >> i) & 1;
            adders[i].add(bit1, bit2);
            try {
                // pass the carry bit if there is an adder following the current one
                adders[i + 1].setCarryIn(adders[i].getCarryOut());
            } catch (ArrayIndexOutOfBoundsException e) {
                if (adders[adders.length - 1].getCarryOut() == 1) {
                    /*  having no adders that come after the current one,
                     *  means that current adder is the last one, and if
                     *  the carry out for that adder is 1, 8-bit ripple carry
                     *  adder overflowed.
                     */
                    throw new AdditionOverflowException("Overflow.");
                }
            }
        }
        return getResult();
    }

    private int getResult() {
        /*  Since the adding process was in reverse,
         *  when building the result, the adders are
         *  reversed so that the adder holding the MSB
         *  comes first. We start from 0, and pack the bits
         *  back into the result, by shifting left by 1
         *  each time through the loop and ORing with the sum.
         */
        int result = 0;
        for (int i = adders.length - 1; i >= 0; i--) {
            result <<= 1;
            result |= adders[i].getSum();
        }
        return result;
    }
}
