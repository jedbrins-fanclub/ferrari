package dk.eamv.ferrari.scenes.loan;

import java.sql.Date;

public class Loan {

    private int id;
    private int car_id;
    private int customer_id;
    private int employee_id;
    private double loanSize;
    private double downPayment;
    private double interestRate;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private LoanStatus status;

    public Loan(int id, int car_id, int customer_id, int employee_id, double loanSize, double downPayment, double interestRate, Date startDate, Date endDate, LoanStatus status) {
        this.id = id;
        this.car_id = car_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.loanSize = loanSize;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public double getLoanSize() {
        return loanSize;
    }

    public void setLoanSize(double loanSize) {
        this.loanSize = loanSize;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
