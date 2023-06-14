package dk.eamv.ferrari.sharedcomponents.forms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.employee.Employee;
import javafx.scene.control.TextField;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;


public class LoanSizeTest {
    private static Employee employee = mock(Employee.class);

    @BeforeAll
    public static void init() {
        mockStatic(FormInputHandler.class);
        when(employee.getMaxLoan()).thenReturn(1000000.0);
    }

    //ratio of downpayment to car price
    @Test
    public void lessThanHalf_ShouldBe_true() {
        double downPayment = 1499999;
        double carPrice = 3000000;

        boolean lessThanHalf = FormBinder.downpaymentLessThanHalf(downPayment, carPrice);

        assertThat(lessThanHalf).isTrue();
    }

    @Test
    public void half_ShouldBe_false() {
        double downPayment = 1500000;
        double carPrice = 3000000;

        boolean lessThanHalf = FormBinder.downpaymentLessThanHalf(downPayment, carPrice);

        assertThat(lessThanHalf).isFalse();
    }

    @Test
    public void moreThanHalf_ShouldBe_false() {
        double downPayment = 1500001;
        double carPrice = 3000000;

        boolean lessThanHalf = FormBinder.downpaymentLessThanHalf(downPayment, carPrice);

        assertThat(lessThanHalf).isFalse();
    }

    //wether the loan size exceeds the sellers loan size limits.
    @Test
    public void underLimit_ShouldBe_false() {
        when(FormInputHandler.getDouble("Lånets størrelse")).thenReturn(999999.0);
        
        boolean exceedsLimit = FormBinder.loanExceedsLimit(employee);

        assertThat(exceedsLimit).isFalse();
    }

    @Test
    public void onLimit_ShouldBe_false() {
        when(FormInputHandler.getDouble("Lånets størrelse")).thenReturn(1000000.0);
        
        boolean exceedsLimit = FormBinder.loanExceedsLimit(employee);

        assertThat(exceedsLimit).isFalse();
    }

    @Test
    public void overLimit_ShouldBe_true() {
        when(FormInputHandler.getDouble("Lånets størrelse")).thenReturn(1000001.0);
        
        boolean exceedsLimit = FormBinder.loanExceedsLimit(employee);

        assertThat(exceedsLimit).isTrue();
    }

    //verify that loansize gets calculated correctly
    @Test
    public void testCalculateLoanSize() {
        Car car = mock(Car.class);
        when(FormInputHandler.getEntityFromComboBox("Bil")).thenReturn(car);
        when(car.getPrice()).thenReturn(1000000.0);

        when(FormInputHandler.getTextField("Udbetaling")).thenReturn(new TextField("l"));
        when(FormInputHandler.getDouble("Udbetaling")).thenReturn(500000.0);

        double loanSize = Double.valueOf(FormBinder.calculateLoanSize());
        assertThat(loanSize).isEqualTo(500000);
    }
}
