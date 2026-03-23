package com.helloservlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        description = "Login Servlet",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name = "user", value = "Manas"),
                @WebInitParam(name = "password", value = "BridgeLabz@1")
        }
)
public class LoginServlet extends HttpServlet {

    private boolean isValidName(String name) {
        if (name == null || name.length() < 3) return false;
        return Character.isUpperCase(name.charAt(0));
    }

    private String validatePassword(String pwd) {
        if (pwd == null || pwd.length() < 8)
            return "Password must be at least 8 characters.";

        boolean hasUpper = false, hasDigit = false;
        int specialCount = 0;
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?";

        for (char c : pwd.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.indexOf(c) >= 0) specialCount++;
        }

        if (!hasUpper) return "Password must have at least 1 uppercase letter.";
        if (!hasDigit) return "Password must have at least 1 numeric digit.";
        if (specialCount != 1) return "Password must have exactly 1 special character.";

        return null; // valid
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        PrintWriter out = response.getWriter();

        if (!isValidName(user)) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            out.println("<font color=red>Invalid Name: Must start with a capital letter and have at least 3 characters.</font>");
            rd.include(request, response);
            return;
        }

        String pwdError = validatePassword(pwd);
        if (pwdError != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            out.println("<font color=red>" + pwdError + "</font>");
            rd.include(request, response);
            return;
        }

        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");

        if (userID.equals(user) && password.equals(pwd)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            out.println("<font color=red>Either username or password is wrong.</font>");
            rd.include(request, response);
        }
    }
}
