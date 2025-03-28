package pl.coderslab.Entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String CREATE = "INSERT INTO users(email, username, password) VALUES(?,?,?);";
    private static final String READ = "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE = "UPDATE users SET email = ?,username = ?, password = ? WHERE ID = ?;";
    private static final String FIND_ALL = "SELECT * FROM users;";
    private static final String DELETE = "DELETE FROM users WHERE id = ?;";
    private static final String EMAIL_EXISTS = "SELECT COUNT(*) FROM users WHERE email = ?;";


    private static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getUsername());
                ps.setString(3, hashPassword(user.getPassword()));
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public User read(int id) {
        User user = new User();
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(READ)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("User with id " + id + " not found");
                        return null;
                    }
                    user.setId(rs.getInt(1));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }


    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getUsername());
                if (user.getPassword().startsWith("$2a")){
                    ps.setString(3, user.getPassword());
                } else {
                    ps.setString(3, hashPassword(user.getPassword()));
                }
                ps.setInt(4, user.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(DELETE)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(FIND_ALL)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        users.add(new User(rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("username"),
                                rs.getString("password")
                        ));
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public boolean isEmailExists(String email) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(EMAIL_EXISTS)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if(rs.next()){
                        return  rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
