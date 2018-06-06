package ru.javaops.masterjava.xml.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import ru.javaops.masterjava.xml.LoadXML;

@WebServlet("/users")

public class ShowUsersServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    req.setAttribute("users", LoadXML.getUsers());
    getServletContext().getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, res);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.sendRedirect("/");
  }

}
