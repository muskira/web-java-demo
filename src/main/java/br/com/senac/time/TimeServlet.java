package br.com.senac.time;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/time/*")
public class TimeServlet extends HttpServlet {

    private final TimeDAO dao = new TimeDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Time> times = dao.listarTodos();
                resp.getWriter().print(gson.toJson(times));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Time time = dao.buscarPorId(id);
                if (time != null) {
                    resp.getWriter().print(gson.toJson(time));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            Erro erro = lancarErro("Erro ao excluir", e);
            resp.setStatus(500);
            resp.getWriter().print(gson.toJson(erro));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            Time time = lerCorpoJson(req, Time.class);

            Time criado = dao.inserir(time);
            resp.setStatus(201); // Criado com sucesso
            resp.getWriter().print(gson.toJson(criado));
        } catch (Exception e) {
            Erro erro = lancarErro("Erro ao excluir", e);
            resp.setStatus(500);
            resp.getWriter().print(gson.toJson(erro));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("applicattion/json");

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo != null && pathInfo.length() > 1) {
                int id = Integer.parseInt(pathInfo.substring(1));
                boolean excluiu = dao.excluirPorId(id);
                if (excluiu) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            Erro erro = lancarErro("Erro ao excluir", e);
            resp.setStatus(500);
            resp.getWriter().print(gson.toJson(erro));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            Time time = lerCorpoJson(req, Time.class);
            boolean atualizou = dao.atualizar(time);
            if (atualizou) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            Erro erro = lancarErro("Erro ao excluir", e);
            resp.setStatus(500);
            resp.getWriter().print(gson.toJson(erro));
        }
    }

    private <T> T lerCorpoJson(HttpServletRequest req, Class<T> classe) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bf = req.getReader()) {
            String linha;
            while ((linha = bf.readLine()) != null) {
                sb.append(linha);
            }
        }
        return gson.fromJson(sb.toString(), classe);
    }

    private Erro lancarErro(String msg, Exception e) {
        return new Erro(msg, e.getMessage());
    }
}
