package pl.coderslab.admins;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.coderslab.Entity.Admin;
import pl.coderslab.Entity.AdminDao;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/admin/register")
public class AdminRegister extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/register.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String confirmedPassword = req.getParameter("confirmPassword").trim();

        AdminDao adminDao = new AdminDao();

        if (!password.equals(confirmedPassword)) {
            req.setAttribute("message", "Passwords should not differ");
            getServletContext().getRequestDispatcher("/users/register.jsp")
                    .forward(req, resp);
            return;
        }
        else if (email.isBlank() || username.isBlank() || password.isBlank() || confirmedPassword.isBlank()) {
            req.setAttribute("message", "Fill all fields");
            getServletContext().getRequestDispatcher("/users/register.jsp")
                    .forward(req, resp);
            return;
        }
        else if (!validatePassword(password)){
            req.setAttribute("message", "Passwords must be at least 8 length and contain one big letter and one digit");
            getServletContext().getRequestDispatcher("/users/register.jsp")
                    .forward(req, resp);
            return;
        }

        boolean isEmailExists = adminDao.isEmailExist(email);
        if (isEmailExists) {
            req.setAttribute("message", "Admin with this email already exists");
            getServletContext().getRequestDispatcher("/users/register.jsp")
                    .forward(req, resp);
            return;
        }

        Admin adminToRegister = new Admin();
        adminToRegister.setEmail(email);
        adminToRegister.setUsername(username);
        adminToRegister.setPassword(password);

        adminDao.create(adminToRegister);

        Admin admin = adminDao.findByEmail(email);

        HttpSession session = req.getSession();
        session.setAttribute("admin", admin);

        resp.sendRedirect(req.getContextPath() + "/user/list");



    }

    private static boolean validatePassword(String password) {
        String passwordValidation = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return Pattern.matches(passwordValidation, password);
    }
}
