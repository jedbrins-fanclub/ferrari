package dk.eamv.ferrari.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.employee.Employee;
import dk.eamv.ferrari.employee.EmployeeModel;

/**
 * Lavet af:
 * Tjekket af:
 * Modificeret af:
 */
public class LoginModel {
    public static Employee authenticate(String email, String password) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT id FROM dbo.Employee WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return EmployeeModel.getFromID(rs.getInt("id"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}