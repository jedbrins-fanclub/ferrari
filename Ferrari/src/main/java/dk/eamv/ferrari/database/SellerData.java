package dk.eamv.ferrari.database;

import dk.eamv.ferrari.database.Database;
import dk.eamv.ferrari.seller.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerData {
    public static Seller getSellerFromID(int id) {
        ResultSet rs = Database.query("SELECT * FROM dbo.Employee WHERE id = " + Integer.toString(id));

        try {
            if (rs.next()) {
                return new Seller(
                    id, rs.getString("first_name"), rs.getString("last_name"), 
                    rs.getString("phone_number"), rs.getString("email"), 
                    rs.getString("password"), rs.getDouble("max_loan"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static boolean isAdmin(Seller seller) {
        return seller.getMaxLoan() < 0;
    }
}
