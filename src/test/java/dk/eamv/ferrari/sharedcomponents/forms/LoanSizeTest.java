package dk.eamv.ferrari.sharedcomponents.forms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LoanSizeTest {
    @Test
    public void lessThanHalf_ShouldBe_true() {
        double downPayment = 1499999;
        double carPrice = 3000000;

        boolean greaterThanHalf = FormBinder.downpaymentLessThanHalf(downPayment, carPrice);

        assertThat(greaterThanHalf).isTrue();
    }

    @Test
    public void half_ShouldBe_false() {
        double downPayment = 1500000;
        double carPrice = 3000000;

        boolean greaterThanHalf = FormBinder.downpaymentLessThanHalf(downPayment, carPrice);

        assertThat(greaterThanHalf).isFalse();
    }

    @Test
    public void greaterThanHalf_ShouldBe_false() {
        double downPayment = 1500001;
        double carPrice = 3000000;

        boolean greaterThanHalf = FormBinder.downpaymentLessThanHalf(downPayment, carPrice);

        assertThat(greaterThanHalf).isFalse();
    }
}
