package dk.eamv.ferrari.employee;

import dk.eamv.ferrari.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {
    public static Employee getFromID(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Employee WHERE id = " + Integer.toString(id));

        try {
            if (rs.next()) {
                return new Employee(
                    id, rs.getString("first_name"), rs.getString("last_name"), 
                    rs.getString("phone_number"), rs.getString("email"), 
                    rs.getString("password"), rs.getDouble("max_loan"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    // Returns null on failure
}