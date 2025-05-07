package com.escola.escolaProj.repository;

import com.escola.escolaProj.Entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findAllByCpf(String cpf);
}
