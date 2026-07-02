package br.com.senac.dao;

import br.com.senac.model.Aluno;

import java.sql.*;

import java.util.ArrayList;

import java.util.List;

public class AlunoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/atividade";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    // Listar todos os alunos

    public List<Aluno> listarTodos() throws SQLException {

        List<Aluno> alunos = new ArrayList<>();

        String sql = "SELECT * FROM aluno";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement stmt = conn.prepareStatement(sql);

             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Aluno aluno = new Aluno();

                aluno.setId(rs.getInt("id"));

                aluno.setNome(rs.getString("nome"));

                aluno.setIdade(rs.getInt("idade"));

                aluno.setCursoId(rs.getInt("curso_id"));

                alunos.add(aluno);

            }

        }

        return alunos;

    }

    // Listar alunos por curso

    public List<Aluno> listarPorCurso(int cursoId) throws SQLException {

        List<Aluno> alunos = new ArrayList<>();

        String sql = "SELECT * FROM aluno WHERE curso_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cursoId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Aluno aluno = new Aluno();

                    aluno.setId(rs.getInt("id"));

                    aluno.setNome(rs.getString("nome"));

                    aluno.setIdade(rs.getInt("idade"));

                    aluno.setCursoId(rs.getInt("curso_id"));

                    alunos.add(aluno);

                }

            }

        }

        return alunos;

    }

    // Buscar aluno por ID

    public Aluno buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Aluno aluno = new Aluno();

                    aluno.setId(rs.getInt("id"));

                    aluno.setNome(rs.getString("nome"));

                    aluno.setIdade(rs.getInt("idade"));

                    aluno.setCursoId(rs.getInt("curso_id"));

                    return aluno;

                }

            }

        }

        return null;

    }

    // Inserir aluno

    public Aluno inserir(Aluno aluno) throws SQLException {

        String sql = "INSERT INTO aluno(nome, idade, curso_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, aluno.getNome());

            stmt.setInt(2, aluno.getIdade());

            stmt.setInt(3, aluno.getCursoId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {

                    aluno.setId(rs.getInt(1));

                }

            }

        }

        return aluno;

    }

    // Atualizar aluno

    public boolean atualizar(Aluno aluno) throws SQLException {

        String sql = "UPDATE aluno SET nome = ?, idade = ?, curso_id = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());

            stmt.setInt(2, aluno.getIdade());

            stmt.setInt(3, aluno.getCursoId());

            stmt.setInt(4, aluno.getId());

            return stmt.executeUpdate() > 0;

        }

    }

    // Excluir aluno

    public boolean excluir(int id) throws SQLException {

        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        }

    }

}
