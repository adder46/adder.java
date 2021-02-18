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
            /* The bits are extracted from the narrow end by shifting right `i` positions,
             * where 0 <= i < adders.length. On first iteration, i == 0, meaning that nothing is shifted.
             * However, each time through the loop, the number of shifted positions (`i`)
             * increases by 1 until it reaches adders.length - 1. As a result, previously extracted bit is
             * discarded and the bit we want to extract next comes to its place, i.e., becomes LSB.
             * By bitwise ANDing the resulting number with 1 (mask), the individual bit at the end
             * is effectively extracted from the number - masking out the rest.
             */
            int bit1 = (a >> i) & 1;
            int bit2 = (b >> i) & 1;
            adders[i].add(bit1, bit2);
            try {
                // Pass the carry bit to the next adder if the current adder is not the last one.
                adders[i + 1].setCarryIn(adders[i].getCarryOut());
            } catch (ArrayIndexOutOfBoundsException e) {
                if (adders[adders.length - 1].getCarryOut() == 1) {
                    /* Having no adders that come after the current one,
                     * means that current adder is the last one. Furthermore,
                     * if the carry out for that adder is 1, we have an overflow.
                     */
                    throw new AdditionOverflowException("Overflow.");
                }
            }
        }
        return getResult();
    }

    private int getResult() {
        /* Since the addition was done starting from the narrow end,
         * when building the result, the adders are reversed so that
         * the adder holding the MSB comes first. The bits are packed
         * back into the result by shifting left by 1 each time through
         * the loop and ORing the result with the sum of an individual adder.
         */
        int result = 0;
        for (int i = adders.length - 1; i >= 0; i--) {
            result <<= 1;
            result |= adders[i].getSum();
        }
        return result;
    }
}
