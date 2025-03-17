package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.coderslab.Entity.User;
import pl.coderslab.Entity.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet("/user/add")
public class UserAdd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/users/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();

        String email = req.getParameter("email").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();

        boolean isEmailExists = userDao.isEmailExists(email);


        if(email == null || email.isBlank() || username == null
                || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("message","Fill all fields");
            getServletContext().getRequestDispatcher("/users/add.jsp").forward(req, resp);
            return;
        } else if(isEmailExists) {
            req.setAttribute("message","User with this email already exists");
            getServletContext().getRequestDispatcher("/users/add.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        userDao.create(user);

        resp.sendRedirect(req.getContextPath() + "/user/list");

    }
}
