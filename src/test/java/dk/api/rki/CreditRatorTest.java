package dk.api.rki;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Made by: Benjamin
public class CreditRatorTest {
    @Test
    public void testCreditRating() {
        Rating a = CreditRator.i().rate("1234560003");
        Rating b = CreditRator.i().rate("1234560004");
        Rating c = CreditRator.i().rate("1234560005");
        Rating d = CreditRator.i().rate("1234560006");

        assertEquals(a, Rating.A);
        assertEquals(b, Rating.B);
        assertEquals(c, Rating.C);
        assertEquals(d, Rating.D);
    }
}
