package dk.eamv.ferrari.scenes.customer;

import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.scenes.loan.LoanModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

// Made by: Benjamin
public final class CustomerModel {
    // Private constructor to disallow instantiation
    private CustomerModel() {}

    /**
     * Creates a new customer in the database.
     * @param customer an Customer object containing all the customer data
     */
    public static void create(Customer customer) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                "INSERT INTO dbo.Customer VALUES (?, ?, ?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCpr());
            statement.setInt(7, customer.getStatus().toInt());

            int row = statement.executeUpdate();
            assert row != 0: "Unable to insert Customer into database";

            // Set the Customer ID to the newly inserted row
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Read a customer from the database, based on the id.
     * @param id the id of the customer to get from the database
     * @return Customer containing the database row information
     */
    public static Customer read(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Customer WHERE id = " + id);

        try {
            if (rs.next()) {
                return new Customer(
                    id, rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("phone_number"), rs.getString("email"),
                    rs.getString("address"), rs.getString("cpr"),
                    CustomerStatus.valueOf(rs.getInt("status"))
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Get all customers from the database.
     * @return ArrayList of all the Customers in the database
     */
    public static ArrayList<Customer> readAll() {
        ArrayList<Customer> customers = new ArrayList<Customer>();

        try (ResultSet rs = Database.query("SELECT * FROM dbo.Customer WHERE status = " + CustomerStatus.ACTIVE.toInt())) {
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

    /**
     * Update a customer in the database based on the id.
     * @param customer the customer information to update with
     */
    public static void update(Customer customer) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Customer
                SET
                    first_name = ?, last_name = ?,
                    phone_number = ?, email = ?,
                    address = ?, cpr = ?, status = ?
                WHERE id = ?;
            """);

            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getCpr());
            statement.setInt(7, customer.getStatus().toInt());
            statement.setInt(8, customer.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Delete customer from the database based on the id.
     * @param id the id of the customer to delete from the database
     */
    public static void delete(int id) {
        if (!LoanModel.checkCustomerID(id)) {
            Database.execute("DELETE FROM Customer WHERE id = " + id);
            return;
        }

        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Customer
                SET status = ?
                WHERE id = ?;
            """);

            statement.setInt(1, CustomerStatus.DELETED.toInt());
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
