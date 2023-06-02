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
    private static Form form;

    /**
     * Sets the form instance variable.
     * @param form - the active form.
     */
    protected static void setForm(Form form) {
        FormInputHandler.form = form;
    }

    /**
     * Reads the input of the fields and returns a Car object based on the input.
     * @return the Car object based on the input of the form.
     */
    protected static Car getFieldsCar() {
        return new Car(getString("Model"), getInt("Årgang"), getDouble("Pris"));
    }

    /**
     * Reads the input of the fields and returns a Customer object based on the input.
     * @return the Customer object based on the input of the form.
     */
    protected static Customer getFieldsCustomer() {
        return new Customer(getString("Fornavn"), getString("Efternavn"), getString("Telefonnummer"), getString("Email"), getString("Adresse"), getString("CPR"));
    }
    
    /**
     * Reads the input of the fields and returns an Employee object based on the input.
     * @return the Employee object based on the input of the form.
     */
    protected static Employee getFieldsEmployee() {
        return new Employee(getString("Fornavn"), getString("Efternavn"), getString("Telefon nr."), getString("Email"), getString("Kodeord"), getDouble("Udlånsgrænse"));
    }
    
    /**
     * Reads the input of the fields and returns a Loan object based on the input.
     * @return the Loan object based on the input of the form.
     */
    protected static Loan getFieldsLoan() {
        Car car = getEntityFromComboBox("Bil");
        Customer customer = getEntityFromComboBox("CPR & Kunde");
        Employee employee = getEntityFromComboBox("Medarbejder");
        Loan loan = new Loan(car.getId(), customer.getId(), employee.getId(), getDouble("Lånets størrelse"), getDouble("Udbetaling"), getDouble("Rente"), getSelectedDate("Start dato DD/MM/ÅÅÅÅ"), getSelectedDate("Slut dato DD/MM/ÅÅÅÅ"), new LoanStatus(3));
        System.out.println("Get selected date" + getSelectedDate("Start dato DD/MM/ÅÅÅÅ"));
        System.out.println("Get selected date" + getSelectedDate("Slut dato DD/MM/ÅÅÅÅ"));
        return loan;
    }
    
    /**
     * Casts the Control of the fieldmap into a TextField, then returns the value.
     * @param key - the String/Header of the TextField.
     * @return the String value of the input.
     */
    protected static String getString(String key) {
        return getTextField(key).getText();
    }

    /**
     * Calls getString() and converts into an int.
     * @see #getString(String)
     * @param key - the String/Header of the TextField.
     * @return the int value of the input.
     */
    protected static int getInt(String key) {
        return Integer.valueOf(getString(key));
    }
    
    /**
     * Calls getString() and converts into a double.
     * @see #getString(String)
     * @param key - the String/Header of the TextField.
     * @return the double value of the input, with ","s converted to "."s.
     */
    protected static double getDouble(String key) {
        String rawValue = getString(key);
        String formattedValue = rawValue.replace(",", ".");
        return Double.valueOf(formattedValue);
    }

    /**
     * Casts the Control into a DatePicker. Then takes the DatePicker and converts it value into an instant,
     * which is then returned as a Date.
     * @param key - the String/Header og the DatePicker.
     * @return the Date of DatePicker.
     */
    protected static Date getSelectedDate(String key) {
        DatePicker datePicker = getDatePicker(key);
        System.out.println("DatePicker " + datePicker);
        LocalDate localDate = datePicker.getValue();
        System.out.println("Local Date " + localDate);
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        System.out.println("Instant " + instant);
        System.out.println("Date from instant " + Date.from(instant));
        return Date.from(instant);
    }

    /**
     * Casts the Control into an AutoCompleteComboBox of generic type <E>.
     * Returns the selected item as <E>
     * @param <E> - the entity to be gotten.
     * @param key - the String/Header of the ComboBox / dropdown.
     * @return the selected element of type <E>.
     */
    protected static <E> E getEntityFromComboBox(String key) {
        AutoCompleteComboBox<E> acb = getAutoCompleteComboBox(key);
        return acb.getSelectedItem();
    }

    /**
     * Iterates over the ArrayList of properties, and sets each field in the hashmap to the property.
     * @param car - a Car object, whose propperties will fill the form.
     */
    protected static void setFieldsCar(Car car) {
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
     * @param customer - a Customer object, whose propperties will fill the form.
     */
    protected static void setFieldsCustomer(Customer customer) {
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
     * @param employee - an Employee object, whose propperties will fill the form.
     */
    protected static void setFieldsEmployee(Employee employee) {
        ArrayList<String> input = employee.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }
    
    /**
     * @param car - a Car object, whose propperties will fill the form.
     * @param customer - a Customer object, whose propperties will fill the form.
     * @param employee - an Employee object, whose propperties will fill the form.
     * @param loan - a Loan object, whose propperties will fill the form.
     */
    protected static void setFieldsLoan(Car car, Customer customer, Employee employee, Loan loan) {
        setChoiceBox("Bil", car.toString());
        setChoiceBox("CPR & Kunde", customer.toString());
        setChoiceBox("Medarbejder", employee.toString());
        setDatePicker("Start dato DD/MM/ÅÅÅÅ", String.valueOf(loan.getStartDate()));
        setDatePicker("Slut dato DD/MM/ÅÅÅÅ", String.valueOf(loan.getEndDate()));
    }
    
    /**
     * @param key - the String/Header of the TextField.
     * @param text - the text to be set.
     */
    protected static void setText(String key, String text) {
        getTextField(key).setText(text);
    }

    /**
     * @param key - the String/Header of the ComboBox/dropdown.
     * @param choice - the String matching the choice. Use toString().
     * @see #toString()
     * @see #getAutoCompleteComboBox(String)
     */
    protected static void setChoiceBox(String key, String choice) {
        getAutoCompleteComboBox(key).getSelectionModel().select(choice);
    }

    /**
     * Takes the a String parameter, since it gets stored as yyyy/MM/dd, which is then converted to dd/MM/yyyy, and set as the value in the DatePicker.
     * @param key - the String/Header of the DatePicker.
     * @param date - the Date converted to a String.
     */
    protected static void setDatePicker(String key, String date) {
        System.out.println("date argument" + date);
        DatePicker datePicker = getDatePicker(key);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            @Override //Date -> String. Called when setting the date
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override // Date <- String
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
     * @param key - the String/Header of the TextField.
     * @return the matching TextField.
     */
    protected static TextField getTextField(String key) {
        return (TextField) form.getFieldMap().get(key);
    }

    /**
     * Finds the Control in the hashmap, and casts it into an AutoCompleteComboBox of generic type <E>.
     * @param key - the String/Header of the AutoCompleteComboBox.
     * @return the matching AutoCompleteComboBox.
     */
    protected static <E> AutoCompleteComboBox<E> getAutoCompleteComboBox(String key) {
        return (AutoCompleteComboBox<E>) form.getFieldMap().get(key);
    }

    /**
     * Finds the Control in the hashmap, and casts it into a DatePicker.
     * @param key - the String/Header of the DatePicker.
     * @return the matching DatePicker.
     */
    protected static DatePicker getDatePicker(String key) {
        return (DatePicker) form.getFieldMap().get(key);
    }
}
