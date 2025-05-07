package com.escola.escolaProj.service;

import com.escola.escolaProj.Entity.Professor;
import com.escola.escolaProj.dto.ProfessorDTO;
import com.escola.escolaProj.repository.ProfessorRepository;
import org.antlr.v4.runtime.ListTokenSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> getAllProfessors(){
        return professorRepository.findAll();
    }

    public List<Professor> getAllProfessorByNome(String nome){
        return professorRepository.findByNome(nome);
    }

    public Optional<ProfessorDTO> getById(Long id){
        Optional<Professor> professorOptional = professorRepository.findById(id);
        if (professorOptional.isPresent()){
            ProfessorDTO professorDTO = new ProfessorDTO();
            return Optional.of(ProfessorDTO.fromProfessor(professorOptional.get()));
        }else{
            return Optional.empty();
        }
    }
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO){
        Professor professor = professorDTO.toProfessor();
        professor = professorRepository.save(professor);
        return professorDTO.fromProfessor(professor);
    }

    public Optional<ProfessorDTO> updateProfessor(Long id, ProfessorDTO professorDTO){
        Optional<Professor> professorOptional = professorRepository.findById(id);
        if (professorOptional.isPresent()){
            Professor professor = professorOptional.get();
            professor.setNome(professorDTO.getNome());
            professor.setSobrenome(professorDTO.getSobrenome());

            professor = professorRepository.save(professor);

            return Optional.of(professorDTO.fromProfessor(professor));
        }else{
            return Optional.empty();
        }
    }

    public boolean delete(Long id){
        if(professorRepository.existsById(id)){
            professorRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
