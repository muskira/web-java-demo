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
            if (pathInfo == null || pathInfo.equals("/")){
                List<Time> times = dao.listarTodos();
                resp.getWriter().println(gson.toJson(times));
            }

        }catch (Exception e){

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try{
            Time time = lerCorpoJson(req, Time.class);
            Time criado = dao.inserir(time);
            resp.setStatus(201);
            resp.getWriter().println(gson.toJson(criado));

        }catch (Exception e){
            System.out.println("Vasco");
        }
    }

    private <T> T lerCorpoJson(HttpServletRequest req, Class<T> classe) throws IOException{
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bf = req.getReader()){
            String linha;
            while ((linha = bf.readLine()) != null){
                sb.append(linha);
            }

        }
        return gson.fromJson(sb.toString(), classe);
    }
}
