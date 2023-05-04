package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.database.Database;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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

    public static void update(int id, Customer customer) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Customer
                SET
                    first_name = ?, last_name = ?,
                    phone_number = ?, email = ?,
                    address = ?, cpr = ?
                WHERE id = ?;
            """);

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCpr());
            statement.setInt(7, customer.getId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean delete(int id) {
        return Database.execute("DELETE FROM dbo.Customer WHERE id = " + Integer.toString(id));
    }
}
