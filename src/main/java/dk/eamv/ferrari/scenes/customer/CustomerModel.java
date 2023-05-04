package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.database.Database;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {
    public static Customer read(int id) {
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

    public static ArrayList<Customer> readAll() {
        ArrayList<Customer> customers = new ArrayList<Customer>();

        try (ResultSet rs = Database.query("SELECT * FROM dbo.Customer")) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("phone_number"), rs.getString("email"),
                    rs.getString("address"), rs.getString("cpr")
                ));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customers;
    }
}
