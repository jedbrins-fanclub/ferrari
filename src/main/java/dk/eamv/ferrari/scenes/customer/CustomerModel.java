package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {
    public static Customer getFromID(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Customer WHERE id = " + Integer.toString(id));

        try {
            if (rs.next()) {
                return new Customer(
                    id, rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("phone_number"), rs.getString("email"),
                    rs.getString("address"), rs.getString("cpr")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
