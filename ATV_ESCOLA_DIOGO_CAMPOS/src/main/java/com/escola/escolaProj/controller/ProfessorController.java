package com.escola.escolaProj.controller;

import com.escola.escolaProj.Entity.Professor;
import com.escola.escolaProj.dto.ProfessorDTO;
import com.escola.escolaProj.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> getAll(@RequestParam(required = false) String nome){
        if (nome != null && !nome.isEmpty()){
            return professorService.getAllProfessorByNome(nome); // busca com base no nome
        }
        return professorService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getById(@PathVariable long id){
        Optional<ProfessorDTO> professorDTOOptional = professorService.getById(id);
        if (professorDTOOptional.isPresent()){
            return ResponseEntity.ok(professorDTOOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> create(@RequestBody ProfessorDTO professorDTO){
        ProfessorDTO professorDTOSave = professorService.createProfessor(professorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorDTOSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> uptade(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO){
        Optional<ProfessorDTO> professorDTOOptional = professorService.updateProfessor(id, professorDTO);
        if(professorDTOOptional.isPresent()){
            return ResponseEntity.ok(professorDTOOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (professorService.delete(id)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
}


}
