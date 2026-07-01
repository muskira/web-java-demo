package br.com.senac.time;

import br.com.senac.banco.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO = Data Access Object
public class TimeDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/aula_java";
    private static final String USER = "root";
    private static final String PASS = "root";

    public TimeDAO() {
    }

    private Connection conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public Time inserir(Time time) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO time (nome) VALUES (?) ";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, time.getNome());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    time.setId(rs.getInt(1));
                }
            }
        }
        return time;
    }

    public Time buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM time WHERE id = ?";

        Time time = null;
        try (Connection connection = conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    time = new Time(rs.getInt("id"), rs.getString("nome"));
                }
            }
        }
        return time;
    }

    public List<Time> listarTodos() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM time";
        List<Time> times = new ArrayList<>();
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                times.add(new Time(rs.getInt("id"), rs.getString("nome")));
            }
        }
        return times;
    }

    public boolean excluirPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM time WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }
    public boolean atualizar(Time time) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE time SET nome = ?";

        try(Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, time.getNome());
            stmt.setInt(2, time.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        }
    }
}


