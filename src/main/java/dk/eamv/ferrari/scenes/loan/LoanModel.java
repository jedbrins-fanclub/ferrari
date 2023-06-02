package dk.eamv.ferrari.scenes.loan;

import dk.eamv.ferrari.database.Database;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

// Made by: Benjamin
public final class LoanModel {
    // Private constructor to disallow instantiation
    private LoanModel() {}

    /**
     * Creates a loan in the database based on the loan.
     * @param loan Loan containing all the data to add to database
     */
    public static void create(Loan loan) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                String.format("""
                    INSERT INTO dbo.Loan
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """)
            );

            statement.setInt(1, loan.getCar_id());
            statement.setInt(2, loan.getCustomer_id());
            statement.setInt(3, loan.getEmployee_id());
            statement.setDouble(4, loan.getLoanSize());
            statement.setDouble(5, loan.getDownPayment());
            statement.setDouble(6, loan.getInterestRate());
            statement.setDate(7, convertDate(loan.getStartDate()));
            statement.setDate(8, convertDate(loan.getEndDate()));
            statement.setInt(9, loan.getStatus().getStatusNumber());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reads a loan from the database based on the id.
     * @param id the id to read from the database
     * @return a Loan containing all the row data
     */
    public static Loan read(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Loan WHERE id = " + id);

        try {
            if (rs.next()) {
                return new Loan(
                    id, rs.getInt("car_id"), rs.getInt("customer_id"), rs.getInt("employee_id"),
                    rs.getDouble("loan_size"), rs.getDouble("down_payment"), rs.getDouble("interest_rate"),
                    rs.getDate("start_date"), rs.getDate("end_date"), new LoanStatus(rs.getInt("status"))
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Reads all rows from the database.
     * @return an ArrayList of all loans in the database
     */
    public static ArrayList<Loan> readAll() {
        ArrayList<Loan> loans = new ArrayList<Loan>();

        try (ResultSet rs = Database.query("SELECT * FROM dbo.Loan")) {
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("id"), rs.getInt("car_id"), rs.getInt("customer_id"), rs.getInt("employee_id"),
                    rs.getDouble("loan_size"), rs.getDouble("down_payment"), rs.getDouble("interest_rate"),
                    rs.getDate("start_date"), rs.getDate("end_date"), new LoanStatus(rs.getInt("status"))
                ));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return loans;
    }

    /**
     * Update loan in the database based on the id.
     * @param loan the new loan information
     */
    public static void update(Loan loan) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Loan
                SET
                    car_id = ?, customer_id = ?, employee_id = ?,
                    loan_size = ?, down_payment = ?, interest_rate = ?,
                    start_date = ?, end_date = ?, status = ?
                WHERE id = ?;
            """);

            statement.setInt(1, loan.getCar_id());
            statement.setInt(2, loan.getCustomer_id());
            statement.setInt(3, loan.getEmployee_id());
            statement.setDouble(4, loan.getLoanSize());
            statement.setDouble(5, loan.getDownPayment());
            statement.setDouble(6, loan.getInterestRate());
            statement.setDate(7, convertDate(loan.getStartDate()));
            statement.setDate(8, convertDate(loan.getEndDate()));
            statement.setInt(9, loan.getStatus().getStatusNumber());
            statement.setInt(10, loan.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Delete a loan from the database, based on the id.
     * @param id the id of the Loan to delete
     * @return a boolean if the delete request was successful
     */
    public static boolean delete(int id) {
        return Database.execute("DELETE FROM dbo.Loan WHERE id = " + id);
    }

    private static java.sql.Date convertDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.util.Date revertDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }
}
