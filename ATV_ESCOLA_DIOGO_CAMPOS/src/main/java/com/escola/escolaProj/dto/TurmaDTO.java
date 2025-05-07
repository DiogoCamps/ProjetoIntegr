package com.escola.escolaProj.dto;

import com.escola.escolaProj.Entity.Aluno;
import com.escola.escolaProj.Entity.Professor;
import com.escola.escolaProj.Entity.Aluno;
import com.escola.escolaProj.Entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaDTO implements Serializable {
    private Long id;
    private String sigla;
    private Integer numeroSala;
    private String nome;
    private Professor professor;
    private List<Aluno> alunos;

    public Turma toTurma(){
        return new Turma(
                this.id,
                this.sigla,
                this.numeroSala,
                this.nome,
                this.professor,
                this.alunos
        );
    }
    public TurmaDTO fromTurma(Turma turma){
      return new TurmaDTO(
              turma.getId(),
              turma.getSigla(),
              turma.getNumeroSala(),
              turma.getNome(),
              turma.getProfessor(),
              turma.getAlunos()
      );
  }

}
