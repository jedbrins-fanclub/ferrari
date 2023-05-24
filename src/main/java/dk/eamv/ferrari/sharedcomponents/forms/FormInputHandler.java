package dk.eamv.ferrari.sharedcomponents.forms;

import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

//Lavet af Christian

/**
 * Handles the logic of creating objects from user input, when CREATE dialogs are shown.
 * Also handles the logic of setting the input of the form, to match the selected entity to be UPDATED.
 * The getFields<Entity> methods read the input in the fields, and returns an object based on that.
 * The setFields<Entity> methods set the input in the fields, based on the object, passed in the argument.
 */

public class FormInputHandler {
    /**
     * Reads the input of the fields and returns a Car object based on the input.
     * @param form - the active form.
     * @return the Car object based on the input of the form.
     */
    protected static Car getFieldsCar(Form form) {
        return new Car(getString(form, "Model"), getInt(form, "Årgang"), getDouble(form, "Pris"));
    }

    /**
     * Reads the input of the fields and returns a Customer object based on the input.
     * @param form - the active form.
     * @return the Customer object based on the input of the form.
     */
    protected static Customer getFieldsCustomer(Form form) {
        return new Customer(getString(form, "Fornavn"), getString(form, "Efternavn"), getString(form, "Telefonnummer"), getString(form, "Email"), getString(form, "Adresse"), getString(form, "CPR"));
    }
    
    /**
     * Reads the input of the fields and returns a Employee object based on the input.
     * @param form - the active form.
     * @return the Employee object based on the input of the form.
     */
    protected static Employee getFieldsEmployee(Form form) {
        return new Employee(getString(form, "Fornavn"), getString(form, "Efternavn"), getString(form, "Telefon nr."), getString(form, "Email"), getString(form, "Kodeord"), getDouble(form, "Udlånsgrænse"));
    }
    
    /**
     * Reads the input of the fields and returns a Loan object based on the input.
     * @param form - the active form.
     * @return the Loan object based on the input of the form.
     */
    protected static Loan getFieldsLoan(Form form) {
        Car car = getEntityFromComboBox(form, "Bil");
        Customer customer = getEntityFromComboBox(form, "CPR & Kunde");
        Employee employee = getEntityFromComboBox(form, "Medarbejder");
        Loan loan = new Loan(car.getId(), customer.getId(), employee.getId(), getDouble(form, "Lånets størrelse"), getDouble(form, "Udbetaling"), getDouble(form, "Rente"), getSelectedDate(form, "Start dato DD/MM/ÅÅÅÅ"), getSelectedDate(form, "Slut dato DD/MM/ÅÅÅÅ"), new LoanStatus(3));
        return loan;
    }
    
    /**
     * Casts the Control of the fieldmap into a TextField, then returns the value.
     * @param form - the active form.
     * @param key - the String/Header of the TextField.
     * @return the String value of the input.
     */
    protected static String getString(Form form, String key) {
        return getTextField(form, key).getText();
    }

    /**
     * Calls getString() and converts into an int.
     * @see #getString(Form, String)
     * @param form - the active form.
     * @param key - the String/Header of the TextField.
     * @return the int value of the input.
     */
    protected static int getInt(Form form, String key) {
        return Integer.valueOf(getString(form, key));
    }
    
    /**
     * Calls getString() and converts into a double.
     * @see #getString(Form, String)
     * @param form - the active form.
     * @param key - the String/Header of the TextField.
     * @return the double value of the input, with ","s converted to "."s.
     */
    protected static double getDouble(Form form, String key) {
        String rawValue = getString(form, key);
        String formattedValue = rawValue.replace(",", ".");
        return Double.valueOf(formattedValue);
    }

    /**
     * Casts the Control into a DatePicker. Then takes the DatePicker and converts it value into an instant,
     * which is then returned as a Date.
     * @param form - the active form.
     * @param key - the String/Header og the DatePicker.
     * @return the Date of DatePicker.
     */
    protected static Date getSelectedDate(Form form, String key) {
        DatePicker datePicker = getDatePicker(form, key);
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    /**
     * Casts the Control into an AutoCompleteComboBox of generic type <E>.
     * Returns the selected item as <E>
     * @param <E> - the entity to be gotten.
     * @param form - the active form.
     * @param key - the String/Header of the ComboBox / dropdown.
     * @return the selected element of type <E>.
     */
    protected static <E> E getEntityFromComboBox(Form form, String key) {
        AutoCompleteComboBox<E> acb = ((AutoCompleteComboBox) form.getFieldMap().get(key));
        return acb.getSelectedItem();
    }

    /**
     * Iterates over the ArrayList of properties, and sets each field in the hashmap to the property.
     * @param form - the active form.
     * @param car - a Car object, whose propperties will fill the form.
     */
    protected static void setFieldsCar(Form form, Car car) {
        ArrayList<String> input = car.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }

    /**
     * Iterates over the ArrayList of properties, and sets each field in the hashmap to the property.
     * @param form - the active form.
     * @param customer - a Customer object, whose propperties will fill the form.
     */
    protected static void setFieldsCustomer(Form form, Customer customer) {
        ArrayList<String> input = customer.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }

    /**
     * Iterates over the ArrayList of properties, and sets each field in the hashmap to the property.
     * @param form - the active form.
     * @param employee - an Employee object, whose propperties will fill the form.
     */
    protected static void setFieldsEmployee(Form form, Employee employee) {
        ArrayList<String> input = employee.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }
    
    /**
     * @param form - the active form.
     * @param car - a Car object, whose propperties will fill the form.
     * @param customer - a Customer object, whose propperties will fill the form.
     * @param employee - an Employee object, whose propperties will fill the form.
     * @param loan - a Loan object, whose propperties will fill the form.
     */
    protected static void setFieldsLoan(Form form, Car car, Customer customer, Employee employee, Loan loan) {
        setChoiceBox(form, "Bil", car.toString());
        //TODO: setFieldsLoanCar(form, car);
        setChoiceBox(form, "CPR & Kunde", customer.toString());
        //TODO: setFieldsLoanCustomer(form, customer);
        setChoiceBox(form, "Medarbejder", employee.toString());
        //TODO: setFieldsLoanEmployee(form, employee);
        //TODO: setFieldsLoanDownpayment(form, loan);
        //TODO: setFieldsMiscLoan(form, loan);
        setDatePicker(form, "Start dato DD/MM/ÅÅÅÅ", String.valueOf(loan.getStartDate()));
        setDatePicker(form, "Slut dato DD/MM/ÅÅÅÅ", String.valueOf(loan.getEndDate()));
    }
    
    /**
     * @param form - the active form.
     * @param key - the String/Header of the TextField.
     * @param text - the text to be set.
     */
    protected static void setText(Form form, String key, String text) {
        getTextField(form, key).setText(text);
    }

    /**
     * @param form - the active form.
     * @param key - the String/Header of the ComboBox/dropdown.
     * @param choice - the String matching the choice. Use toString().
     * @see #toString()
     * @see #getAutoCompleteComboBox(Form, String)
     */
    protected static void setChoiceBox(Form form, String key, String choice) {
        getAutoCompleteComboBox(form, key).getSelectionModel().select(choice);
    }

    //TODO: Add Javadoc here.
    //TODO: Understand this code better.
    protected static void setDatePicker(Form form, String key, String date) {
        DatePicker datePicker = getDatePicker(form, key);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy"; // Updated pattern
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        // Convert from "yyyy-MM-dd" to "dd/MM/yyyy"
                        LocalDate originalDate = LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String formattedDate = dateFormatter.format(originalDate);
                        return LocalDate.parse(formattedDate, dateFormatter);
                    } catch (DateTimeParseException e) {
                        // Handle the parsing exception
                        System.out.println("Error parsing date: " + string);
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });
        LocalDate localDate = datePicker.getConverter().fromString(date);
        datePicker.setValue(localDate);
    }
    
    /**
     * Finds the Control in the hashmap, and casts it into a TextField.
     * @param form - the active form.
     * @param key - the String/Header of the TextField.
     * @return the matching TextField.
     */
    protected static TextField getTextField(Form form, String key) {
        return (TextField) form.getFieldMap().get(key);
    }

    /**
     * Finds the Control in the hashmap, and casts it into an AutoCompleteComboBox of generic type <?>.
     * @param form - the active form.
     * @param key - the String/Header of the AutoCompleteComboBox.
     * @return the matching AutoCompleteComboBox.
     */
    protected static AutoCompleteComboBox<?> getAutoCompleteComboBox(Form form, String key) {
        return (AutoCompleteComboBox) form.getFieldMap().get(key);
    }

    /**
     * Finds the Control in the hashmap, and casts it into a DatePicker.
     * @param form - the active form.
     * @param key - the String/Header of the DatePicker.
     * @return the matching DatePicker.
     */
    protected static DatePicker getDatePicker(Form form, String key) {
        return (DatePicker) form.getFieldMap().get(key);
    }
}
