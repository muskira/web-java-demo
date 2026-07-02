package br.com.senac.servlet;

import br.com.senac.model.Curso;
import br.com.senac.dao.CursoDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;

@WebServlet("/cursos/*")

public class CursoServlet extends HttpServlet {

    private final CursoDAO dao = new CursoDAO();

    private final Gson gson = new Gson();

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        response.setContentType("application/json");

        response.setCharacterEncoding("UTF-8");

        try {

            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {

                List<Curso> cursos = dao.listarTodos();

                response.getWriter().print(gson.toJson(cursos));

            } else {

                int id = Integer.parseInt(pathInfo.substring(1));

                Curso curso = dao.buscarPorId(id);

                if (curso == null) {

                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    return;

                }

                response.getWriter().print(gson.toJson(curso));

            }

        } catch (SQLException e) {

            response.sendError(500, e.getMessage());

        }

    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        try {

            BufferedReader reader = request.getReader();

            Curso curso = gson.fromJson(reader, Curso.class);

            dao.inserir(curso);

            response.setStatus(HttpServletResponse.SC_CREATED);

            response.getWriter().print(gson.toJson(curso));

        } catch (SQLException e) {

            response.sendError(500, e.getMessage());

        }

    }

    @Override

    protected void doPut(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getPathInfo().substring(1));

            Curso curso = gson.fromJson(request.getReader(), Curso.class);

            curso.setId(id);

            boolean atualizado = dao.atualizar(curso);

            if (!atualizado) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                return;

            }

            response.getWriter().print(gson.toJson(curso));

        } catch (SQLException e) {

            response.sendError(500, e.getMessage());

        }

    }

    @Override

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getPathInfo().substring(1));

            boolean excluido = dao.excluir(id);

            if (!excluido) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                return;

            }

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (SQLException e) {

            response.sendError(500, e.getMessage());

        }

    }

}
