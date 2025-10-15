package dao;

import dbConfig.GetConnection;
import dto.AdminDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AdminDAO")
public class AdminDAO {
    public int loginAdmin(String email, String password) {
        int i = 0;
        try {
            Connection con = GetConnection.getConnect();
            String query = "select * from adminlogin where email=? and password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                i = 1;
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
        return i;
    }

}






