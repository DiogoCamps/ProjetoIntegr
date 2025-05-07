package com.escola.escolaProj.Entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Aluno implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    private String nome;
    @Column(unique = true)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "turma_id", referencedColumnName = "id")
    private Turma turma;

    public Aluno() {

    }

    public Aluno(Long idAluno, String nome, String cpf, Turma turma) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.cpf = cpf;
        this.turma = turma;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }



}
