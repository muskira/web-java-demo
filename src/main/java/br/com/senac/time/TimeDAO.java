package br.com.senac.time;

import br.com.senac.banco.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeDAO {

    private static final String URL="jdbc:mysql://localhost:3306/aula-java";
    private static final String USER = "root";
    private static final String PASS = "root";


    private Connection conectar() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER,PASS);

    }

    public Time inserir(Time time) throws SQLException, ClassNotFoundException{
        String sql = "INSERT INTO time (nome) VALUES (?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, time.getNome());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()){
                    time.setId(rs.getInt(1));
                }

            }
        }
        return time;

    }
    public List<Time> listarTodos() throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM time";
        List<Time> times = new ArrayList<>();
        try (Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while (rs.next()){
                times.add(new Time(rs.getInt("id"), rs.getString("nome")));
            }

        }
        return times;
    }
}
