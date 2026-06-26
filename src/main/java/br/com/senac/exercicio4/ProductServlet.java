package br.com.senac.exercicio4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/produto/*")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("html/text");

        String pathinfo = req.getPathInfo();

        if (pathinfo != null && pathinfo.length() > 1){
            resp.getWriter().println("<h1>Você esta vendo os detalhes do produto: " + pathinfo.substring(1) + "</h1>");
        }else {
            resp.getWriter().println("<h1>Produto não informado</h1>");
        }
    }
}
