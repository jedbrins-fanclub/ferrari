package dk.eamv.ferrari.sharedcomponents.forms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.DatePicker;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

public class DateTest {
    
    @BeforeEach
    public void init() {
        Platform.startup(() -> {});
    }
    
    @AfterEach
    public void cleanUp() {
        Platform.exit();
    }

    @Test
    public void oneWeek_ShouldBe_7Days() {
        LocalDate endDate = LocalDate.of(1, 1, 8);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        int daysBetween = FormBinder.calculateDaysBetween(startDatePicker, endDatePicker);
        assertThat(daysBetween).isEqualTo(7);
    }

    @Test
    public void twoMonths_ShouldBe_61Days() { //30,5 days each month
        LocalDate endDate = LocalDate.of(1, 3, 1);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        int daysBetween = FormBinder.calculateDaysBetween(startDatePicker, endDatePicker);
        assertThat(daysBetween).isEqualTo(61);
    }

    @Test
    public void oneYear_ShouldBe_365Days() {
        LocalDate endDate = LocalDate.of(2, 1, 1);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        int daysBetween = FormBinder.calculateDaysBetween(startDatePicker, endDatePicker);
        assertThat(daysBetween).isEqualTo(365);
    }

    @Test
    public void lessThanThreeYears_ShouldBe_false() {
        LocalDate endDate = LocalDate.of(3, 12, 31);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        boolean isLongerThan3Yrs = FormBinder.periodIsOver3Yrs(startDatePicker, endDatePicker);

        assertThat(isLongerThan3Yrs).isFalse();
    }

    @Test
    public void threeYears_ShouldBe_false() {
        LocalDate endDate = LocalDate.of(4, 1, 1);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        boolean isLongerThan3Yrs = FormBinder.periodIsOver3Yrs(startDatePicker, endDatePicker);

        assertThat(isLongerThan3Yrs).isFalse();
    }

    @Test
    public void moreThanThreeYears_ShouldBe_true() {
        LocalDate endDate = LocalDate.of(4, 1, 2);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        boolean isLongerThan3Yrs = FormBinder.periodIsOver3Yrs(startDatePicker, endDatePicker);

        assertThat(isLongerThan3Yrs).isTrue();
    }

    @Test
    public void negative_ShouldBe_false() {
        LocalDate startDate = LocalDate.of(1, 1, 2);
        LocalDate endDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);
        DatePicker endDatePicker = new DatePicker(endDate);

        boolean isNotNegative = FormBinder.periodIsNotNegative(startDatePicker, endDatePicker);

        assertThat(isNotNegative).isFalse();
    }

    @Test
    public void positive_ShouldBe_true() {
        LocalDate endDate = LocalDate.of(1, 1, 2);
        DatePicker endDatePicker = new DatePicker(endDate);
        LocalDate startDate = LocalDate.of(1, 1, 1);
        DatePicker startDatePicker = new DatePicker(startDate);

        boolean isNotNegative = FormBinder.periodIsNotNegative(startDatePicker, endDatePicker);

        assertThat(isNotNegative).isTrue();
    }
}
