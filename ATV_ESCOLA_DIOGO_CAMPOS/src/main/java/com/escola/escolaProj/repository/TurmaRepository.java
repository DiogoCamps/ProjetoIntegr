package com.escola.escolaProj.repository;

import com.escola.escolaProj.Entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {


    List<Turma> getAllByNome(String nome);
}
