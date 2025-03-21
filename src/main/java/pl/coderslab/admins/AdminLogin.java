package pl.coderslab.admins;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.Entity.Admin;
import pl.coderslab.Entity.AdminDao;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/login")
public class AdminLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/login.jsp")
                .forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (email.isBlank() || password.isBlank()) {
            req.setAttribute("message", "Fill all fields");
            getServletContext().getRequestDispatcher("/users/login.jsp")
                    .forward(req, resp);
            return;
        }

        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.findByEmail(email);

        if (admin != null && BCrypt.checkpw(password, admin.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("admin", admin);
            resp.sendRedirect(req.getContextPath() + "/user/list");
            return;
        }

        req.setAttribute("message", "Wrong email or password");
        getServletContext().getRequestDispatcher("/users/login.jsp")
                .forward(req, resp);

    }
}
