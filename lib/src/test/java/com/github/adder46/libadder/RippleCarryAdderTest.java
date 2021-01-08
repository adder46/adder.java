package com.github.adder46.libadder;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import com.github.adder46.libadder.AdditionOverflowException;

public class RippleCarryAdderTest {
    @Test(expectedExceptions = AdditionOverflowException.class)
    public void addOverflow() throws AdditionOverflowException {
        RippleCarryAdder rippleCarryAdder = new RippleCarryAdder(8);
        rippleCarryAdder.add(128, 128);
    }

    @Test
    @Parameters({"a", "b", "expectedResult"})
    public void addClean(int a, int b, int expectedResult) throws AdditionOverflowException {
        RippleCarryAdder rippleCarryAdder = new RippleCarryAdder(8);
        int result = rippleCarryAdder.add(a, b);
        assertEquals(result, expectedResult);
    }
}
