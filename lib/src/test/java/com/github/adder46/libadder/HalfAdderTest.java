package com.github.adder46.libadder;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class HalfAdderTest {
    @Test
    @Parameters({"a", "b", "expectedSum", "expectedCarryOut"})
    public void testAdd(int a, int b, int expectedSum, int expectedCarryOut) {
        HalfAdder halfAdder = new HalfAdder();
        halfAdder.add(a, b);
        assertEquals(halfAdder.getSum(), expectedSum);
        assertEquals(halfAdder.getCarryOut(), expectedCarryOut);
    }
}
