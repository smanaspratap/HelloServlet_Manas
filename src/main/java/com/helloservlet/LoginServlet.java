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
                @WebInitParam(name = "password", value = "BridgeLabz")
        }
)
public class LoginServlet extends HttpServlet {

    private boolean isValidName(String name) {
        if (name == null || name.length() < 3) return false;
        return Character.isUpperCase(name.charAt(0));
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
