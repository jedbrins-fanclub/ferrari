package dk.eamv.ferrari.car;

import dk.eamv.ferrari.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarModel {
    public static Car getCarFromID(int id) {
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
}
