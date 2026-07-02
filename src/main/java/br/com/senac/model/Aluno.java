package br.com.senac.model;

public class Aluno {

    private int id;

    private String nome;

    private int idade;

    private int cursoId;

    public Aluno() {

    }

    public Aluno(int id, String nome, int idade, int cursoId) {

        this.id = id;

        this.nome = nome;

        this.idade = idade;

        this.cursoId = cursoId;

    }

    public int getId() {

        return id;

    }

    public void setId(int id) {

        this.id = id;

    }

    public String getNome() {

        return nome;

    }

    public void setNome(String nome) {

        this.nome = nome;

    }

    public int getIdade() {

        return idade;

    }

    public void setIdade(int idade) {

        this.idade = idade;

    }

    public int getCursoId() {

        return cursoId;

    }

    public void setCursoId(int cursoId) {

        this.cursoId = cursoId;

    }

}

