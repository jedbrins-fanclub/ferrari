package dk.api.bank;

import java.time.LocalDate;
import java.util.Random;

/**
 * Information Expert for the variable interest rate of bank loans.
 * This class provides a (Singleton) link to the bank,
 * which can be used to access information about the bank's interest rates.
 *
 * @see #todaysRate()
 */
public class InterestRate {
    private static InterestRate instance = null;
    private static Random rnd = new Random();

    /**
     * Restricted constructor for Singleton creation.
     * Visibility is relaxed from private to protected for testing purposes.
     * The constructor should never be invoked outside of the class.
     */
    protected InterestRate() {
    }

    // returns random double between 3.0 and 9.0, based on todays date

    /**
     * @return The current (daily) interest rate
     */
    public double todaysRate() {
        randomDelay(1.0, 2.0);

        LocalDate today = LocalDate.now();

        int seed = today.getDayOfMonth() + today.getMonthValue() + today.getYear();

        return new Random(1000 * seed).nextDouble() * 6.0 + 3.0;
    }

    /**
     * @return The Singleton instance
     */
    public static InterestRate i() {
        if (instance == null)
            instance = new InterestRate();

        return instance;
    }

    // min, max in secs
    private void randomDelay(double min, double max) {
        try {
            double delaySecs = rnd.nextDouble() * (max - min) + min;

            Thread.sleep((long) (delaySecs * 1000));
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
