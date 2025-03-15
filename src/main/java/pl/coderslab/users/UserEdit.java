package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.coderslab.Entity.User;
import pl.coderslab.Entity.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        UserDao userDao = new UserDao();
        User read = new User();
        read = userDao.read(id);

        req.setAttribute("userInfo", read);
        getServletContext().getRequestDispatcher("/users/edit.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int id = Integer.parseInt(req.getParameter("id"));

        User userToUpdate = new User();
        UserDao userDao = new UserDao();
        userToUpdate = userDao.read(id);

        boolean isEmailExists = userDao.isEmailExists(email);
        if (isEmailExists && !(userToUpdate.getEmail().equals(email))) {
            req.setAttribute("message", "User with this email already exists");
            getServletContext().getRequestDispatcher("/users/edit.jsp")
                    .forward(req, resp);
            return;
        }

        if (!(password.isBlank()) && password != null) {
            userToUpdate.setPassword(password);
        }

        userToUpdate.setEmail(email);
        userToUpdate.setUsername(username);
        userToUpdate.setId(id);
        userDao.update(userToUpdate);

        resp.sendRedirect("/user/list");
    }
}
