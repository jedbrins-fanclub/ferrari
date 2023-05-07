package dk.eamv.ferrari.scenes.car;

import dk.eamv.ferrari.database.Database;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class CarModel {
    public static Car read(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Car WHERE id = " + Integer.toString(id));

        try {
            if (rs.next()) {
                return new Car(id, rs.getString("model"), rs.getInt("year"), rs.getDouble("price"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Car> readAll() {
        ArrayList<Car> cars = new ArrayList<Car>();

        try (ResultSet rs = Database.query("SELECT * FROM dbo.car")) {
            while (rs.next()) {
                cars.add(new Car(
                    rs.getInt("id"), rs.getString("model"),
                    rs.getInt("year"), rs.getDouble("price")
                ));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return cars;
    }

    public static void update(int id, Car car) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("""
                UPDATE dbo.Car
                SET model = ?, year = ?, price = ?
                WHERE id = ?;
            """);

            statement.setString(1, car.getModel());
            statement.setInt(2, car.getYear());
            statement.setDouble(3, car.getPrice());
            statement.setInt(4, car.getId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean delete(int id) {
        return Database.execute("DELETE FROM dbo.Car WHERE id = " + Integer.toString(id));
    }
}
