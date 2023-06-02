package dk.api.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import dk.api.bank.developertools.InterestRateTestTool;

// Made by: Benjamin
public class InterestRateTest {
    @Test
    public void testRate() {
        InterestRate interest = InterestRateTestTool.newInterestRateMock(3.0);
        assertEquals(interest.todaysRate(), 3.0);
        assertNotEquals(interest.todaysRate(), 2.9);
    }
}
