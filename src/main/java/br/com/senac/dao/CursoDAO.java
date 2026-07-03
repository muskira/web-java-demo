package br.com.senac.dao;

import br.com.senac.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/atividade";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Listar todos os cursos
    public List<Curso> listarTodos() throws SQLException {

        List<Curso> cursos = new ArrayList<>();

        String sql = "SELECT * FROM curso";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){

        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Curso curso = new Curso();

                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));

                cursos.add(curso);
            }
        }

        return cursos;
    }

    // Buscar curso por ID
    public Curso buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM curso WHERE id = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){

        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Curso curso = new Curso();

                    curso.setId(rs.getInt("id"));
                    curso.setNome(rs.getString("nome"));
                    curso.setCargaHoraria(rs.getInt("carga_horaria"));

                    return curso;
                }
            }
        }

        return null;
    }

    // Inserir curso
    public Curso inserir(Curso curso) throws SQLException {

        String sql = "INSERT INTO curso(nome,carga_horaria) VALUES (?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){

        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                }
            }
        }

        return curso;
    }

    // Atualizar curso
    public boolean atualizar(Curso curso) throws SQLException {

        String sql = "UPDATE curso SET nome=?, carga_horaria=? WHERE id=?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){

        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getCargaHoraria());
            stmt.setInt(3, curso.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Excluir curso
    public boolean excluir(int id) throws SQLException {

        String sql = "DELETE FROM curso WHERE id=?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){

        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        }
    }

}