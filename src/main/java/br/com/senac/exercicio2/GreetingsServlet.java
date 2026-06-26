package br.com.senac.exercicio2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/saudacao")
public class GreetingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        String name = req.getParameter("name");
        if (name == null) {
            resp.getWriter().println("<h1>Visitante</h1>");
        } else if (name.length() < 2) {
            resp.getWriter().println("<h1 style=\"color:red\">Nome deve ser pelo 2 carcteres</h1>");
        }else {
            resp.getWriter().println("<h1>Olá, " + name + "</h1>");
        }

    }
}
