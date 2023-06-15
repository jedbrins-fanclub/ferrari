package dk.eamv.ferrari.sharedcomponents.forms;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dk.api.rki.Rating;
import dk.eamv.ferrari.scenes.car.Car;
import javafx.application.Platform;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CalculateInterestRateTest {
    private static Car car = mock(Car.class);
    
    @BeforeAll
    public static void initOnce() {
        mockStatic(FormInputHandler.class);
        when(car.getPrice()).thenReturn(1000000.0);
    }
    
    @BeforeEach
    public void init() {
        when(FormInputHandler.getTextField("Rente")).thenReturn(new TextField());
        when(FormInputHandler.getTextField("Udbetaling")).thenReturn(new TextField(""));
        when(FormInputHandler.getEntityFromComboBox("Bil")).thenReturn(null);
        DatePicker mockDatePicker = new DatePicker(LocalDate.of(1, 1, 1));
        when(FormInputHandler.getDatePicker("Start dato DD/MM/ÅÅÅÅ")).thenReturn(mockDatePicker);
        when(FormInputHandler.getDatePicker("Slut dato DD/MM/ÅÅÅÅ")).thenReturn(mockDatePicker);
        FormBinder.setCustomersCreditScore(Rating.D);
    }
    
    //Credit score
    @Test
    public void creditscoreC_ShouldAdd_3() {
        FormBinder.setCustomersCreditScore(Rating.C);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(3);
    }

    @Test
    public void creditscoreB_ShouldAdd_2() {
        FormBinder.setCustomersCreditScore(Rating.B);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(2);
    }
    
    @Test
    public void creditscoreA_ShouldAdd_1() {
        FormBinder.setCustomersCreditScore(Rating.A);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(1);
    }

    //Banks rate
    @Test
    public void verifyBanksInterestRate() {
        double mockInterestRate = 8.5;
        FormBinder.setBanksInterestRate(mockInterestRate);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(mockInterestRate);
    }

    //Loansize to downpayment ratio
    @Test
    public void lessThanHalf_ShouldAdd_1() {
        when(FormInputHandler.getTextField("Udbetaling")).thenReturn(new TextField("499999.0"));
        when(FormInputHandler.getDouble("Udbetaling")).thenReturn(499999.0);
        when(FormInputHandler.getEntityFromComboBox("Bil")).thenReturn(car);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(1);
    }

    @Test
    public void half_ShouldAdd_0() {
        when(FormInputHandler.getTextField("Udbetaling")).thenReturn(new TextField("500000.0"));
        when(FormInputHandler.getDouble("Udbetaling")).thenReturn(500000.0);
        when(FormInputHandler.getEntityFromComboBox("Bil")).thenReturn(car);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(0);
    }
     
    @Test
    public void moreThanHalf_ShouldAdd_0() {
        when(FormInputHandler.getTextField("Udbetaling")).thenReturn(new TextField("500001.0"));
        when(FormInputHandler.getDouble("Udbetaling")).thenReturn(500001.0);
        when(FormInputHandler.getEntityFromComboBox("Bil")).thenReturn(car);

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(0);
    }

    //Dates
    @Test
    public void shorterThan3Yrs_ShouldAdd_0() {
        when(FormInputHandler.getDatePicker("Start dato DD/MM/ÅÅÅÅ")).thenReturn(new DatePicker(LocalDate.of(1, 1, 1)));
        when(FormInputHandler.getDatePicker("Slut dato DD/MM/ÅÅÅÅ")).thenReturn(new DatePicker(LocalDate.of(3, 12, 31)));

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(0);
    }
    
    @Test
    public void exactly3Yrs_ShouldAdd_0() {
        when(FormInputHandler.getDatePicker("Start dato DD/MM/ÅÅÅÅ")).thenReturn(new DatePicker(LocalDate.of(1, 1, 1)));
        when(FormInputHandler.getDatePicker("Slut dato DD/MM/ÅÅÅÅ")).thenReturn(new DatePicker(LocalDate.of(4, 1, 1)));

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(0);
    }
    
    @Test
    public void longerThan3Yrs_ShouldAdd_1() {
        when(FormInputHandler.getDatePicker("Start dato DD/MM/ÅÅÅÅ")).thenReturn(new DatePicker(LocalDate.of(1, 1, 1)));
        when(FormInputHandler.getDatePicker("Slut dato DD/MM/ÅÅÅÅ")).thenReturn(new DatePicker(LocalDate.of(4, 1, 2)));

        assertThat(FormBinder.calculateInterestRate()).isEqualTo(1);
    }
}
