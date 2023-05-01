package dk.api.bank.developertools;

import dk.api.bank.InterestRate;

/**
 * Test tool for developers working the InterestRate class.
 * The test tool can supply mock instances of InterestRate
 * with predictable interest rates.
 *
 * @see #newInterestRateMock(double)
 * @see InterestRate
 */
public class InterestRateTestTool {

    /**
     * @param interestRate specifies the desired interest rate
     * @return A mock instance of InterestRate that responds with a fixed interest rate
     */
    public static InterestRate newInterestRateMock(double interestRate) {
        return new InterestRateMock(interestRate);
    }

    private static class InterestRateMock extends InterestRate {

        private double interestRate;

        public InterestRateMock(double interestRate) {
            this.interestRate = interestRate;
        }

        @Override
        public double todaysRate() {
            return interestRate;
        }

    }

}
