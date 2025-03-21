package pl.coderslab.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.coderslab.Entity.Admin;
import pl.coderslab.Entity.User;
import pl.coderslab.Entity.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;

@WebServlet("/user/delete")
public class UserDelete extends HttpServlet {

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
        int id = Integer.parseInt(req.getParameter("id"));

        UserDao userDao = new UserDao();
        userDao.delete(id);

        getServletContext().getRequestDispatcher("/user/list")
                .forward(req, resp);

    }
}
