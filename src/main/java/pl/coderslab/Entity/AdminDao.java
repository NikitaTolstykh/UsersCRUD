package pl.coderslab.Entity;

import jakarta.servlet.http.Cookie;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
   private static final String CREATE_ADMIN = "INSERT INTO admins(email, username, password) VALUES (?,?,?);";
   private static final String FIND_BY_EMAIL = "SELECT * FROM admins WHERE email = ? ";
   private static final String EMAIL_EXISTS = "SELECT COUNT(*) FROM admins WHERE email = ?;";

   private static String hashedPassword(String password){
       String hashedPassword =BCrypt.hashpw(password, BCrypt.gensalt());
       return hashedPassword;
   }

   public Admin create(Admin admin) {
       try(Connection conn = DbUtil.getConnection()) {
           try(PreparedStatement ps = conn.prepareStatement(CREATE_ADMIN, PreparedStatement.RETURN_GENERATED_KEYS)) {
               ps.setString(1, admin.getEmail());
               ps.setString(2, admin.getUsername());
               ps.setString(3, hashedPassword(admin.getPassword()));
               ps.executeUpdate();
               try (ResultSet rs = ps.getGeneratedKeys()) {
                   if (rs.next()) {
                       admin.setId(rs.getInt(1));
                   }
               }
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       return admin;
   }

  public Admin findByEmail(String email) {
       try(Connection conn = DbUtil.getConnection()) {
           try(PreparedStatement ps = conn.prepareStatement(FIND_BY_EMAIL)) {
               ps.setString(1, email);
               try(ResultSet rs = ps.executeQuery()) {
                   if (rs.next()){
                       Admin foundAdmin = new Admin();
                       foundAdmin.setId(rs.getInt("id"));
                       foundAdmin.setEmail(rs.getString("email"));
                       foundAdmin.setUsername(rs.getString("username"));
                       foundAdmin.setPassword(rs.getString("password"));
                       return foundAdmin;
                   }
               }
           }

       }catch (SQLException ex){
           ex.printStackTrace();
       }

       return null;
  }

   public boolean isEmailExist(String email) {
       try(Connection conn = DbUtil.getConnection()){
           try(PreparedStatement ps = conn.prepareStatement(EMAIL_EXISTS)) {
               ps.setString(1, email);
               try(ResultSet rs = ps.executeQuery()){
                   if(rs.next()){
                       return  rs.getInt(1) > 0;
                   }
               }
           }

       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       return true;
   }

}
