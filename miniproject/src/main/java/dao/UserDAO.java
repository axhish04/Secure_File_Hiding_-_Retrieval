package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String e = rs.getString(1);
            if (e.equals(email))
                return true;
        }
        return false;
    }

    public static int saveUser(User user) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into users (name, email, password) values (?, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        return ps.executeUpdate();
    }

    public static User getUserByEmail(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from users where email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            String password = rs.getString("password");
            return new User(name, email, password);
        }
        return null;
    }
}
