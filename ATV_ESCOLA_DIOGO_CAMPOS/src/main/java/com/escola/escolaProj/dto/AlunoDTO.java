package com.escola.escolaProj.dto;

import com.escola.escolaProj.Entity.Aluno;
import com.escola.escolaProj.Entity.Turma;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.io.Serializable;

public class AlunoDTO implements Serializable {
    private Long idAluno;
    private String nome;
    private String cpf;
    @JsonIgnore
    private Turma turma;

    public AlunoDTO() {
    }

    public AlunoDTO(Long idAluno, String nome, String cpf, Turma turma) {
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

    public Aluno toAluno (){
        return new Aluno(
                this.idAluno,
                this.nome,
                this.cpf,
                this.turma
        );
    }

    public AlunoDTO fromAluno(Aluno aluno){
        return new AlunoDTO(
                aluno.getIdAluno(),
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getTurma()
        );
    }


}
