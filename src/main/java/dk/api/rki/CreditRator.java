package dk.api.rki;

import java.util.Random;

/**
 * Information Expert for credit rating.
 * This class provides a (Singleton) link to RKI,
 * which can be used to access information about individual credit scores.
 * 
 * For testing purposes, the following invalid (but working) CPR numbers
 * can be used to retrieve a specific credit rating:
 * 
 * A: 0000000000<br/>
 * B: 0000000001<br/>
 * C: 0000000002<br/>
 * D: 0000000003
 *
 * @see #rate(String)
 */
public class CreditRator {
    private static CreditRator instance = null;
    private static Random rnd = new Random();
    private static Rating[] ratingValues = Rating.values();

    /**
     * Restricted constructor for Singleton creation.
     */
    private CreditRator() {
    }

    /**
     * Provides a credit rating for specific individual.
     * The assessment may take several seconds to complete.
     *
     * @param cpr of the individual to rate
     * @return A credit rating for the specified individual
     * @see Rating
     */
    // PRE: cpr.length == 10 && cpr[i] is a digit
    public Rating rate(String cpr) {
        if (cpr.length() != 10)
            throw new NumberFormatException(exceptionMessage(cpr));

        try {
            randomDelay(2.0, 5.0);

            int rating = sumOfTheDigits(cpr) % ratingValues.length;

            return ratingValues[rating];
        } catch (Exception e) {
            throw new NumberFormatException(exceptionMessage(cpr));
        }
    }

    private String exceptionMessage(String cpr) {
        return "Illegal CPR number format: \"" + cpr + "\"";
    }

    private int sumOfTheDigits(String number) {
        int sum = 0;

        for (int i = 0; i < 10; i++)
            sum += Integer.parseInt("" + number.charAt(i));

        return sum;
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

    /**
     * @return The Singleton instance
     */
    public static CreditRator i() {
        if (instance == null)
            instance = new CreditRator();

        return instance;
    }
}
