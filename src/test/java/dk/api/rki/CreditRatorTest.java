package dk.api.rki;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Made by: Benjamin
public class CreditRatorTest {
    @Test
    public void testCreditRatingA() throws InterruptedException {
        Thread a = runInThread("1234560003", Rating.A);
        Thread b = runInThread("1234560004", Rating.B);
        Thread c = runInThread("1234560005", Rating.C);
        Thread d = runInThread("1234560006", Rating.D);

        a.start(); b.start(); c.start(); d.start();
        a.join(); b.join(); c.join(); d.join();
    }

    public Thread runInThread(String cpr, Rating expected) {
        Thread thread = new Thread(() -> {
            Rating rating = CreditRator.i().rate(cpr);
            assertEquals(rating, expected);
        });
        return thread;
    }
}
