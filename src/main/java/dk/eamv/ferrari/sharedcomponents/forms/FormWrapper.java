package dk.eamv.ferrari.sharedcomponents.forms;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import dk.api.rki.CreditRator;
import dk.api.rki.Rating;
import dk.api.bank.InterestRate;
import dk.eamv.ferrari.scenes.car.Car;
import dk.eamv.ferrari.scenes.car.CarController;
import dk.eamv.ferrari.scenes.car.CarModel;
import dk.eamv.ferrari.scenes.customer.Customer;
import dk.eamv.ferrari.scenes.customer.CustomerController;
import dk.eamv.ferrari.scenes.customer.CustomerModel;
import dk.eamv.ferrari.scenes.employee.Employee;
import dk.eamv.ferrari.scenes.employee.EmployeeController;
import dk.eamv.ferrari.scenes.employee.EmployeeModel;
import dk.eamv.ferrari.scenes.loan.Loan;
import dk.eamv.ferrari.scenes.loan.LoanController;
import dk.eamv.ferrari.scenes.loan.LoanModel;
import dk.eamv.ferrari.scenes.loan.LoanStatus;
import dk.eamv.ferrari.sharedcomponents.nodes.AutoCompleteComboBox;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javafx.application.Platform;

public final class FormWrapper {
    /*
     * Lavet af: Christian
     * Wraps the entire form into a dialog box, then adds buttons and mouselisteners to those buttons.
     * Checks if all fields are full, then ok button runs the query into the database.
     */

    private static Dialog<Object> dialog;
    private static Form form;
    private static Label errorLabel = new Label();
    private static Button buttonOK = new Button("OK");
    private static Button buttonCancel = new Button("Fortryd");
    private static Rating creditRating = null;
    private static double interestRate;

    public static Dialog<Object> getDialog() {
        return dialog;
    }

    protected static void wrapCreate(Form form, CRUDType type) {
        setDialog(form);
        setCreateMouseListener(type, form, dialog);
        if (type == CRUDType.LOAN) {
            checkRate(form);
        }
    }

    protected static void wrapUpdate(Form form, Car car) {
        setDialog(form);
        setFieldsCar(form, car);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Car newCar = getFieldsCar(form, dialog); //create new object based on updated fields.
                newCar.setId(car.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                CarModel.update(newCar); //update in DB  
                
                //update in TableView
                ObservableList<Car> cars = CarController.getCars();
                int index = cars.indexOf(car);
                cars.remove(index);
                cars.add(index, newCar);   
            } 
        });
    }

    protected static void wrapUpdate(Form form, Employee employee) {
        setDialog(form);
        setFieldsEmployee(form, employee);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Employee newEmployee = getFieldsEmployee(form, dialog); //create new object based on updated fields.
                newEmployee.setId(employee.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                EmployeeModel.update(newEmployee); //update in DB  
                
                //update in TableView
                ObservableList<Employee> employees = EmployeeController.getEmployees();
                int index = employees.indexOf(employee);
                employees.remove(index);
                employees.add(index, newEmployee);   
            } 
        });
    }

    protected static void wrapUpdate(Form form, Loan loan) {
        setDialog(form);
        Car car = CarModel.read(loan.getCar_id());
        Customer customer = CustomerModel.read(loan.getCustomer_id());
        Employee employee = EmployeeModel.read(loan.getEmployee_id());
        setFieldsLoan(form, car, customer, employee, loan);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Loan newLoan = getFieldsLoan(form); //create new object based on updated fields.
                newLoan.setId(loan.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                LoanModel.update(newLoan); //update in DB   

                //update in TableView 
                ObservableList<Loan> loans = LoanController.getLoans();
                int index = loans.indexOf(loan);
                loans.remove(index);
                loans.add(index, newLoan); 
            }
        });
    }

    protected static void wrapUpdate(Form form, Customer customer) {
        setDialog(form);
        setFieldsCustomer(form, customer);
        buttonOK.setOnMouseClicked(e -> {
            if (form.verifyHasFilledFields()) {
                Customer newCustomer = getFieldsCustomer(form); //create new object based on updated fields.
                newCustomer.setId(customer.getId()); //set the new objects id to the old, so that .update() targets correct ID in DB.
                CustomerModel.update(newCustomer); //update in DB   

                //update in TableView 
                ObservableList<Customer> customers = CustomerController.getCustomers();
                int index = customers.indexOf(customer);
                customers.remove(index);
                customers.add(index, newCustomer); 
            }
        });
    }

    private static void setDialog(Form form) {
        dialog = new Dialog<>();

        // Close the dialog when pressing X
        // https://stackoverflow.com/a/36262208
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        interestRate = 0.0;

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add("dialog.css");
        dialogPane.getStyleClass().add("dialog");
        errorLabel.setVisible(false);
        errorLabel.setPadding(new Insets(0, 0, 0, 100));
        errorLabel.getStyleClass().add("errorLabel");
        buttonCancel.setOnMouseClicked(e -> {
            dialog.setResult(true);
            dialog.close();
        });
        HBox buttons = new HBox(buttonCancel, buttonOK, form.getForwardBoss(), errorLabel);
        buttons.setSpacing(25);
        VBox vBox = new VBox(form.getGridPane(), buttons);
        vBox.setSpacing(50);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);
    }

    private static void showCreditRatingError() {
        errorLabel.setText("Kunde har kreditværdighed D");
        errorLabel.setVisible(true);
    }

    private static void checkRKI(Form form) {
        new Thread(() -> {
            Customer customer = getFromComboBox(form, "CPR & Kunde");
            if (customer == null) {
                return;
            }

            Window window = dialog.getDialogPane().getScene().getWindow();
            EventHandler<WindowEvent> prev = window.getOnCloseRequest();
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                window.setOnCloseRequest(event -> {});

                errorLabel.setText("Finder kreditværdighed for kunde");
                errorLabel.setVisible(true);
            });

            String cpr = customer.getCpr();
            creditRating = CreditRator.i().rate(cpr);

            Platform.runLater(() -> {
                calculateInterestRate(form);
                if (creditRating.equals(Rating.D)) {
                    showCreditRatingError();
                } else {
                    errorLabel.setVisible(false);
                }

                window.setOnCloseRequest(prev);
                buttonOK.setDisable(false);
            });
        }).start();
    }

    private static void checkRate(Form form) {
        new Thread(() -> {
            Window window = dialog.getDialogPane().getScene().getWindow();
            EventHandler<WindowEvent> prev = window.getOnCloseRequest();
            Platform.runLater(() -> {
                buttonOK.setDisable(true);
                window.setOnCloseRequest(event -> {});

                errorLabel.setText("Finder dagens rente");
                errorLabel.setVisible(true);
            });

            interestRate = InterestRate.i().todaysRate();
            Platform.runLater(() -> {
                errorLabel.setVisible(false);
                window.setOnCloseRequest(prev);
                buttonOK.setDisable(false);
            });
        }).start();
    }

    private static void setCreateMouseListener(CRUDType type, Form form, Dialog dialog) {
        switch (type) {
            case LOAN:
                bindLoanSize(form);
                bindFieldsCar(form);
                bindDatepickers(form);
                bindFieldsCustomer(form);
                bindFieldsEmployee(form);

                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }
                    
                    if (creditRating.equals(Rating.D)) {
                        showCreditRatingError();
                        return;
                    }

                    Customer customer = getFromComboBox(form, "CPR & Kunde");
                    if (CreditRator.i().rate(customer.getCpr()).equals(Rating.D)) {
                        displayErrorMessage("Kunden har kreditværdighed D");
                        return;
                    }

                    if (Double.valueOf(calculateLoanSize(form)) < 0) {
                        displayErrorMessage("Lånets størrelse kan ikke være mindre end det udbetalte beløb");
                        return;
                    }

                    Employee employee = getFromComboBox(form, "Medarbejder");
                    if (employee.getMaxLoan() < getDouble(form, "Lånets størrelse")) {
                        displayErrorMessage("Lånets størrelse overskrider medarbejderens beføjelser.");
                        form.getForwardBoss().setVisible(true);
                        return;
                    }
                    
                    Loan loan = getFieldsLoan(form);
                    LoanController.getLoans().add(loan);
                    LoanModel.create(loan);
                    dialog.close();
                });
                break;

            case CUSTOMER:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");getErrorLabel().setText("Mangler input i markerede felter");
                        return;
                    }
                
                    Customer customer = getFieldsCustomer(form);
                    CustomerController.getCustomers().add(customer);
                    CustomerModel.create(customer);
                });
                break;

            case CAR:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }
                    
                    Car car = getFieldsCar(form, dialog);
                    CarController.getCars().add(car);
                    CarModel.create(car);
                });
                break;

            case EMPLOYEE:
                buttonOK.setOnMouseClicked(e -> {
                    if (!form.verifyHasFilledFields()) {
                        displayErrorMessage("Mangler input i de markerede felter");
                        return;
                    }
                    
                    Employee employee = getFieldsEmployee(form, dialog);
                    EmployeeController.getEmployees().add(employee);
                    EmployeeModel.create(employee);
                });
                break;
            
            default:
                break;
        }
    }

    private static void setFieldsCar(Form form, Car car) {
        ArrayList<String> input = car.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }


    private static Car getFieldsCar(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        Car car = new Car(getString(form, "Model"), getInt(form, "Årgang"), getDouble(form, "Pris"));

        return car;
    }

    private static void setFieldsCustomer(Form form, Customer customer) {
        ArrayList<String> input = customer.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }

    private static Customer getFieldsCustomer(Form form) {
        dialog.setResult(true);
        dialog.close();
        Customer customer = new Customer(getString(form, "Fornavn"), getString(form, "Efternavn"), getString(form, "Telefonnummer"), getString(form, "Email"), getString(form, "Adresse"), getString(form, "CPR"));

        return customer;
    }
    
    private static Loan getFieldsLoan(Form form) {
        dialog.setResult(true);
        dialog.close();
        Car car = getFromComboBox(form, "Bil");
        Customer customer = getFromComboBox(form, "CPR & Kunde");
        Employee employee = getFromComboBox(form, "Medarbejder");
        Loan loan = new Loan(car.getId(), customer.getId(), employee.getId(), getDouble(form, "Lånets størrelse"), getDouble(form, "Udbetaling"), getDouble(form, "Rente"), getSelectedDate(form, "Start dato DD/MM/ÅÅÅÅ"), getSelectedDate(form, "Slut dato DD/MM/ÅÅÅÅ"), new LoanStatus(3));
        return loan;
    }

    private static void setFieldsLoan(Form form, Car car, Customer customer, Employee employee, Loan loan) {
        setChoice(form, "Bil", car.toString());
        setFieldsLoanCar(form, car);
        setChoice(form, "CPR & Kunde", customer.toString());
        setFieldsLoanCustomer(form, customer);
        setChoice(form, "Medarbejder", employee.toString());
        setFieldsLoanEmployee(form, employee);
        setFieldsLoanDownpayment(form, loan);
        setFieldsMiscLoan(form, loan);
        setDate(form, "Start dato DD/MM/ÅÅÅÅ", String.valueOf(loan.getStartDate()));
        setDate(form, "Slut dato DD/MM/ÅÅÅÅ", String.valueOf(loan.getEndDate()));
    }

    private static Employee getFieldsEmployee(Form form, Dialog dialog) {
        dialog.setResult(true);
        dialog.close();
        Employee employee = new Employee(getString(form, "Fornavn"), getString(form, "Efternavn"), getString(form, "Telefon nr."), getString(form, "Email"), getString(form, "Kodeord"), getDouble(form, "Udlånsgrænse"));
        return employee;
    }
    
    private static void setFieldsEmployee(Form form, Employee employee) {
        ArrayList<String> input = employee.getPropperties();
        HashMap<String, Control> fieldMap = form.getFieldMap();

        int counter = 0;

        for (Control field : fieldMap.values()) {
            ((TextField) field).setText(input.get(counter));
            counter++;
        }
    }

    private static void bindFieldsCar(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        AutoCompleteComboBox<Car> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Bil");
        comboBox.setOnAction(e -> {
            Car car = getFromComboBox(form, "Bil");
            if (car != null) {
                setFieldsLoanCar(form, car);
                loanSize.setText(calculateLoanSize(form));
                calculateInterestRate(form);
            }
        });
    }
    
    private static void setFieldsLoanCar(Form form, Car car) {
        setText(form, "Model", car.getModel());
        setText(form, "Årgang", String.valueOf(car.getYear()));
        setText(form, "Pris", String.valueOf(car.getPrice()));
        setText(form, "Stelnummer", String.valueOf(car.getId()));
    }
    
    private static void bindFieldsCustomer(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("CPR & Kunde");
        comboBox.setOnAction(e -> {
            Customer customer = getFromComboBox(form, "CPR & Kunde");
            if (customer != null) {
                checkRKI(form);
                setFieldsCustomer(form, customer);
            }
        });
    }

    private static void setFieldsLoanCustomer(Form form, Customer customer) {
        setText(form, "Kundens Fornavn", customer.getFirstName());
        setText(form, "Kundens Efternavn", customer.getLastName());
        setText(form, "Kundens CPR", customer.getCpr());
        setText(form, "Kundens Telefon nr.", customer.getPhoneNumber());
        setText(form, "Kundens Adresse", customer.getAddress());
        setText(form, "Kundens Email", customer.getEmail());
    }

    private static void bindFieldsEmployee(Form form) {
        AutoCompleteComboBox<Customer> comboBox = (AutoCompleteComboBox) form.getFieldMap().get("Medarbejder");
        comboBox.setOnAction(e -> {
            Employee employee = getFromComboBox(form, "Medarbejder");
            if (employee != null) {
                setFieldsLoanEmployee(form, employee);
            }
        });
    }

    private static void setFieldsLoanEmployee(Form form, Employee employee) {
        setText(form, "Medarbejderens Fornavn", employee.getFirstName());
        setText(form, "Medarbejderens Efternavn", employee.getLastName());
        setText(form, "Medarbejderens ID", String.valueOf(employee.getId()));
        setText(form, "Medarbejderens Telefon nr.", employee.getPhoneNumber());
        setText(form, "Medarbejderens Email", employee.getEmail());
    }

    private static void setFieldsMiscLoan(Form form, Loan loan) {
        getTextField(form, "Rente").setText(String.valueOf(loan.getInterestRate()));
        getTextField(form, "Lånets størrelse").setText(String.valueOf(loan.getLoanSize()));
        getTextField(form, "Udbetaling").setText(String.valueOf(loan.getDownPayment()));
    }

    private static void setFieldsLoanDownpayment(Form form, Loan loan) {
        TextField textField = ((TextField) form.getFieldMap().get("Udbetaling"));
        textField.setText(String.valueOf(loan.getDownPayment()));
    }

    private static TextField getTextField(Form form, String key) {
        return ((TextField) form.getFieldMap().get(key));
    }
    
    private static void bindLoanSize(Form form) {
        TextField loanSize = (TextField) form.getFieldMap().get("Lånets størrelse");
        TextField downPayment = (TextField) form.getFieldMap().get("Udbetaling");
        downPayment.setOnKeyTyped(e -> {
            loanSize.setText(calculateLoanSize(form));
            calculateInterestRate(form);
        });
    }
    
    private static String calculateLoanSize(Form form) {
        double price = 0;
        double downpayment = 0;
        
        Car car = getFromComboBox(form, "Bil");
        if (car != null) {
            price = car.getPrice();
        } 

        TextField textField = ((TextField) form.getFieldMap().get("Udbetaling"));
        if (!textField.getText().isEmpty()) {
            downpayment = Double.valueOf(textField.getText());
        } 

        return String.valueOf(price - downpayment);
    }

    private static void calculateInterestRate(Form form) {
        double totalInterestRate = 0.0;
        
        totalInterestRate += interestRate; //add banks rate

        if (creditRating != null) {
            switch (creditRating) { //add based on creditscore
                case A:
                    totalInterestRate += 1;
                    break;

                case B:
                    totalInterestRate += 2;
                    break;

                case C:
                    totalInterestRate += 3;
                    break;

                default:
                    break;
            }
        }
        
        TextField downpaymentField = (TextField) form.getFieldMap().get("Udbetaling");
        double downpayment = 0;
        if (!downpaymentField.getText().isEmpty()) {
            downpayment = Double.valueOf(downpaymentField.getText());
        }

        Car selectedCar = (Car) getFromComboBox(form, "Bil");
        double carPrice = 0;
        if (selectedCar != null) {
            carPrice = selectedCar.getPrice();
        }
        
        if (!downpaymentField.getText().isEmpty() && selectedCar != null) {
            if (downpayment / carPrice < 0.5) { //add 1% if loansize > 50%
                totalInterestRate += 1;
            }
        }

        DatePicker start = (DatePicker) form.getFieldMap().get("Start dato DD/MM/ÅÅÅÅ");
        DatePicker end = (DatePicker) form.getFieldMap().get("Slut dato DD/MM/ÅÅÅÅ");

        if (start.getValue() != null && end.getValue() != null) {
            if (calculateDaysBetween(start, end) > 3 * 365) { //add 1% if loan period > 3 years.
                totalInterestRate += 1;
            }
        }

        TextField interestField = (TextField) form.getFieldMap().get("Rente");
        interestField.setText(String.format("%.2f", totalInterestRate));
    }   

    private static void bindDatepickers(Form form) {
        DatePicker starDatePicker = ((DatePicker) form.getFieldMap().get("Start dato DD/MM/ÅÅÅÅ"));
        starDatePicker.setOnAction(e -> calculateInterestRate(form));
        DatePicker endDatePicker = ((DatePicker) form.getFieldMap().get("Slut dato DD/MM/ÅÅÅÅ"));
        endDatePicker.setOnAction(e -> calculateInterestRate(form));
    }

    private static int calculateDaysBetween(DatePicker start, DatePicker end) {
        LocalDate startDate = start.getValue();
        LocalDate endDate = end.getValue();
        Period period = Period.between(startDate, endDate);
        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();

        double totalDays = days + months * 30.5 + years * 365;
        
        return (int) totalDays;
    }

    private static <E> E getFromComboBox(Form form, String key) {
        AutoCompleteComboBox<E> acb = ((AutoCompleteComboBox) form.getFieldMap().get(key));
        return acb.getSelectedItem();   
    }
    
    private static void setText(Form form, String key, String text) {
        TextField textField = (TextField) form.getFieldMap().get(key);
        textField.setText(text);
    }

    private static void setChoice(Form form, String key, String choice) {
        AutoCompleteComboBox<?> comboBox = (AutoCompleteComboBox) form.getFieldMap().get(key);
        comboBox.getSelectionModel().select(choice);
    }

    private static void setDate(Form form, String key, String date) {
        DatePicker datePicker = ((DatePicker) form.getFieldMap().get(key));
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

    private static String getString(Form form, String key) {
        return ((TextField)form.getFieldMap().get(key)).getText();
    }

    private static int getInt(Form form, String key) {
        return Integer.valueOf(getString(form, key));
    }

    private static double getDouble(Form form, String key) {
        String raw = getString(form, key);
        String formatted = raw.replace(",", ".");
        return Double.valueOf(formatted);
    }

    private static Date getSelectedDate(Form form, String key) {
        DatePicker datePicker = ((DatePicker) form.getFieldMap().get(key));
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    protected static Label getErrorLabel() {
        return errorLabel;
    }

    private static void displayErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
