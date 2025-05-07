package com.escola.escolaProj.dto;

import com.escola.escolaProj.Entity.Professor;
import com.escola.escolaProj.Entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO implements Serializable {
    private Long id;
    private String nome;
    private String sobrenome;
    private List<Turma> turmas;

    public Professor toProfessor(){
        return new Professor(
                this.id,
                this.nome,
                this.sobrenome,
                this.turmas
        );
    }

    public static ProfessorDTO fromProfessor(Professor professor){
        return new ProfessorDTO(
                professor.getId(),
                professor.getNome(),
                professor.getSobrenome(),
                professor.getTurmas()
        );

    }

}
