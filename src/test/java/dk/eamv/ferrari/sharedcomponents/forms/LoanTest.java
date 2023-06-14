package dk.eamv.ferrari.sharedcomponents.forms;

import org.junit.jupiter.api.Test;

import javafx.scene.control.DatePicker;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class LoanTest {
    @Test
    void testDaysBetween() {
        LocalDate startDate = LocalDate.of(0, 0, 0);
        DatePicker startDatePicker = new DatePicker(null);
        DatePicker endDatePicker = new DatePicker(null);

        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

        int daysBetween = FormBinder.calculateDaysBetween(null, null);
        assertThat(daysBetween).isEqualTo(0);
    }
}
