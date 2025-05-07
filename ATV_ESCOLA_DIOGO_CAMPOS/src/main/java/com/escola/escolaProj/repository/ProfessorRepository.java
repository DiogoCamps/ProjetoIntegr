package com.escola.escolaProj.repository;

import com.escola.escolaProj.Entity.Professor;
import com.escola.escolaProj.Entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByNome(String nome);
}
