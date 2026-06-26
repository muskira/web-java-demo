package br.com.senac.exercicio3;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/calculadora")
public class CalculatorServlet extends HttpServlet {
    private Object resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String n1 = req.getParameter("n1");
        String n2 = req.getParameter("n2");
        String operacao = req.getParameter("operacao");

        resp.setContentType("text/html;charset=UTF-8");
        double resultado;
        switch (operacao) {
            case "mult":
                resultado = Double.parseDouble(n1) * Double.parseDouble(n2);
                resp.getWriter().println("<h1>" + resultado + "</h1>");
                break;
            case "div":
                double divisor = Double.parseDouble(n2);
                if (divisor == 0){
                    resp.getWriter().println("<h1>Não pode dividir por zero!!! </h1>");
                }else {
                    resultado = Double.parseDouble(n1) / divisor;
                    resp.getWriter().println("<h1>" + resultado + "</h1>");

                }
                break;
            case "soma":
                resultado = Double.parseDouble(n1) + Double.parseDouble(n2);
                resp.getWriter().println("<h1>" + resultado + "</h1>");
                break;
            case "sub":
                resultado = Double.parseDouble(n1) - Double.parseDouble(n2);
                resp.getWriter().println("<h1>" + resultado + "</h1>");
                break;
            default:
                resp.getWriter().println("<h1>Operação desconhecida</h1>");
        }
    }
}

