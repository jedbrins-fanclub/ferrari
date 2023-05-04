package dk.eamv.ferrari.loan;

import dk.eamv.ferrari.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanModel {
    public static Loan getFromID(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Loan WHERE id = " + Integer.toString(id));

        try {
            if (rs.next()) {
                return new Loan(
                    id, rs.getInt("car_id"), rs.getInt("customer_id"), rs.getInt("employee_id"),
                    rs.getDouble("loan_size"), rs.getDouble("down_payment"), rs.getDouble("interest_rate"),
                    rs.getDate("start_date"), rs.getDate("end_date"),
                    new LoanStatus(rs.getInt("status")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
