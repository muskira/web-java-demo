package br.com.senac.time;

public class Time {

    private int id;
    private String Nome;

    public Time(int id, String nome) {
        this.id = id;
        Nome = nome;
    }

    public Time(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
}
