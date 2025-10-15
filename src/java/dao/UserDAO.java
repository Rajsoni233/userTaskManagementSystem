          package dao;

import dto.UserDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dbConfig.GetConnection;

public class UserDAO {
    
    // ✅ Register new user
    public int registerUser(UserDTO user) {
        int rows = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "INSERT INTO userlogin(username,email,password,address,approved,blocked) VALUES (?,?,?,?,0,0)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAddress());
            rows = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    // ✅ Check user login
    public UserDTO checkUserLogin(String email, String password) {
        UserDTO user = null;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "SELECT * FROM userlogin WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setApproved(rs.getInt("approved"));
                user.setBlocked(rs.getInt("blocked"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

 


    // ✅ Approve user
    public int approveUser(int userId) {
        int rows = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "UPDATE userlogin SET approved=1 WHERE userId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rows = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    // ✅ Block user
    public int blockUser(int userId) {
        int rows = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "UPDATE userlogin SET blocked=1 WHERE userId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rows = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    // ✅ Delete user (if needed)
    public int deleteUser(int userId) {
        int rows = 0;
        try (Connection con = GetConnection.getConnect()) {
            String sql = "DELETE FROM userlogin WHERE userId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rows = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
    
    public List<UserDTO> getAllUsersList() {
    List<UserDTO> list = new ArrayList<>();

    String sql = "SELECT userId, username, email, address, approved, blocked FROM userlogin";

    try (Connection con = GetConnection.getConnect();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            UserDTO u = new UserDTO();
            u.setUserId(rs.getInt("userId"));
            u.setUsername(rs.getString("username"));
            u.setEmail(rs.getString("email"));
            u.setAddress(rs.getString("address"));
            u.setApproved(rs.getInt("approved"));
            u.setBlocked(rs.getInt("blocked"));

            list.add(u);
        }

    } catch (Exception e) {
        e.printStackTrace(); // TODO: Better to use a logger
    }

    return list;
}


}
