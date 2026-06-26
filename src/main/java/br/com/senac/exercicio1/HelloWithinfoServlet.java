package br.com.senac.exercicio1;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/info")
public class HelloWithinfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h1>Java Web</h1>");

        LocalDate now = LocalDate.now();
        resp.getWriter().println("<h1>Data Atual: " + now + " </h1>");
        String javaVersion = System.getProperty("java.version");
        resp.getWriter().println("<h1>Versão java: " + javaVersion + "</h1>");
    }
}