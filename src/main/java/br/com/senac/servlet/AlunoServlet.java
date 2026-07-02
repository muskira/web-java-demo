package br.com.senac.servlet;

import br.com.senac.model.Aluno;
import br.com.senac.dao.AlunoDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/alunos/*")
public class AlunoServlet extends HttpServlet {

    private final AlunoDAO dao = new AlunoDAO();
    private final Gson gson = new Gson();

    // LISTAR
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String cursoIdParam = request.getParameter("cursoId");
            String pathInfo = request.getPathInfo();

            // FILTRO POR CURSO (RELACIONAMENTO 1:N)
            if (cursoIdParam != null) {

                int cursoId = Integer.parseInt(cursoIdParam);

                List<Aluno> alunos = dao.listarPorCurso(cursoId);

                response.getWriter().print(gson.toJson(alunos));
                return;
            }

            // BUSCAR POR ID
            if (pathInfo != null && !pathInfo.equals("/")) {

                int id = Integer.parseInt(pathInfo.substring(1));

                Aluno aluno = dao.buscarPorId(id);

                if (aluno == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                response.getWriter().print(gson.toJson(aluno));
                return;
            }

            // LISTAR TODOS
            List<Aluno> alunos = dao.listarTodos();
            response.getWriter().print(gson.toJson(alunos));

        } catch (SQLException e) {
            response.sendError(500, e.getMessage());
        }
    }

    // CRIAR
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            Aluno aluno = gson.fromJson(request.getReader(), Aluno.class);

            dao.inserir(aluno);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().print(gson.toJson(aluno));

        } catch (SQLException e) {
            response.sendError(500, e.getMessage());
        }
    }

    // ATUALIZAR
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            String pathInfo = request.getPathInfo();

            int id = Integer.parseInt(pathInfo.substring(1));

            Aluno aluno = gson.fromJson(request.getReader(), Aluno.class);
            aluno.setId(id);

            boolean atualizado = dao.atualizar(aluno);

            if (!atualizado) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.getWriter().print(gson.toJson(aluno));

        } catch (SQLException e) {
            response.sendError(500, e.getMessage());
        }
    }

    // DELETAR
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String pathInfo = request.getPathInfo();

            int id = Integer.parseInt(pathInfo.substring(1));

            boolean deletado = dao.excluir(id);

            if (!deletado) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (SQLException e) {
            response.sendError(500, e.getMessage());
        }
    }
}