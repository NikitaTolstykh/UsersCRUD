package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.coderslab.Entity.Admin;
import pl.coderslab.Entity.User;
import pl.coderslab.Entity.UserDao;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/list")
public class UserList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Admin adminLogged = (Admin) session.getAttribute("admin");

        if (adminLogged == null) {
            req.setAttribute("message", "To use adminPanel you must log in");
            getServletContext().getRequestDispatcher("/admin/main")
                    .forward(req, resp);
            return;
        }

        UserDao userDao = new UserDao();
        List<User> userList = userDao.findAll();
        req.setAttribute("users", userList);

        getServletContext().getRequestDispatcher("/users/list.jsp")
                .forward(req, resp);

    }
}
