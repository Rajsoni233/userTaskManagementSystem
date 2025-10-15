 package dbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
public class GetConnection {
    public static Connection getConnect(){
        Connection con = null;
        try{
                Class.forName(GetDetails.DRIVER_CLASS);
                con = DriverManager.getConnection(GetDetails.URL,GetDetails.USER,GetDetails.PASSWORD);
                if(con!=null)
                        System.out.println("Connection established successfully");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Exception : "+e);
        }
        return con;
    }
}