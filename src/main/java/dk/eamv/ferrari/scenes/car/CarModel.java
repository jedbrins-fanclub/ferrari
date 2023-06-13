package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.scenes.loan.LoanModel;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


// Made by: Benjamin
public final class CarModel {
    // Private constructor to disallow instantiation
    private CarModel() {}

    /**
     * Read a car from the database, based on the id.
     * @param car the id of the car to get from the database
     */
    public static void create(Car car) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                "INSERT INTO dbo.Car VALUES (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, car.getModel());
            statement.setInt(2, car.getYear());
            statement.setDouble(3, car.getPrice());
            statement.setInt(4, car.getStatus().toInt());

            int row = statement.executeUpdate();
            assert row != 0: "Unable to insert Car into database";

            // Set the Car ID to the newly inserted row
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    
    public static Car read(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Car WHERE id = " + id);

        try {
            if (rs.next()) {
                return new Car(id, rs.getString("model"), rs.getInt("year"), rs.getDouble("price"), CarStatus.valueOf(rs.getInt("status")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Get all cars from the database.
     * @return ArrayList of all the cars in the database
     */
    public static ArrayList<Car> readAll() {
        ArrayList<Car> cars = new ArrayList<Car>();

        try (ResultSet rs = Database.query("SELECT * FROM dbo.Car")) {
            while (rs.next()) {
                cars.add(new Car(
                    rs.getInt("id"), rs.getString("model"), rs.getInt("year"), rs.getDouble("price"), CarStatus.valueOf(rs.getInt("status"))
                ));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return cars;
    }

    /**
     * Update a car in the database based on the id.
     * @param car the car information to update with
     */
    public static void update(Car car) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Car
                SET model = ?, year = ?, price = ?, status = ?
                WHERE id = ?;
            """);

            statement.setString(1, car.getModel());
            statement.setInt(2, car.getYear());
            statement.setDouble(3, car.getPrice());
            statement.setInt(4, car.getStatus().toInt());
            statement.setInt(5, car.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Delete car from the database based on the id.
     * @param id the id of the car to delete from the database
     */
    public static void delete(int id) {
        if (!LoanModel.checkCarID(id)) {
            Database.execute("DELETE FROM Car WHERE id = " + id);
            return;
        }

        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Car
                SET status = ?
                WHERE id = ?;
            """);

            statement.setInt(1, CarStatus.DELETED.toInt());
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
