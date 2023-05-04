package dk.eamv.ferrari.employee;

import dk.eamv.ferrari.database.Database;

import java.util.ArrayList;
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

    public static ArrayList<Employee> getPage(int page, int amount) {
        int offset = page * amount;
        ResultSet rs = Database.query(String.format("""
            SELECT * FROM dbo.Employee 
            ORDER BY id 
            OFFSET %d ROWS
            FETCH NEXT %d ROWS ONLY;
        """, offset, amount));

        ArrayList<Employee> employees = new ArrayList<Employee>();

        try {
            while (rs.next()) {
                employees.add(new Employee(
                    rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), 
                    rs.getString("phone_number"), rs.getString("email"), 
                    rs.getString("password"), rs.getDouble("max_loan")
                ));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return employees;
    }

    // Returns null on failure
    public static Employee authenticate(String email, String password) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT id FROM dbo.Employee WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return getFromID(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}