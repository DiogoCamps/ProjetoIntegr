package com.escola.escolaProj.service;

import com.escola.escolaProj.Entity.Aluno;
import com.escola.escolaProj.Entity.Turma;
import com.escola.escolaProj.dto.TurmaDTO;
import com.escola.escolaProj.repository.AlunoRepository;
import com.escola.escolaProj.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    public TurmaRepository turmaRepository;

    @Autowired
    public AlunoRepository alunoRepository;

    public List<Turma> getAllTurmas() {
        return turmaRepository.findAll();
    }

    public List<Turma> getAllTurmasByNome(String nome) {
        return turmaRepository.getAllByNome(nome);
    }
    //busca pelo id
    public Optional<TurmaDTO> getById(Long id){
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if(turmaOptional.isPresent()){
            TurmaDTO turmaDTO = new TurmaDTO();
            return Optional.of(turmaDTO.fromTurma(turmaOptional.get()));
        }else {
            return Optional.empty();
        }
    }

    //cria uma turma
    public TurmaDTO createTurma(TurmaDTO turmaDTO){
        Turma turma = turmaDTO.toTurma();
        turma = turmaRepository.save(turma);
        return turmaDTO.fromTurma(turma);
    }

    //atualiza os dados da turma menos os alunos
    public Optional<TurmaDTO> updateTurma(Long id, TurmaDTO turmaDTO) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if(turmaOptional.isPresent()){
            Turma turma = turmaOptional.get();
            turma.setSigla(turmaDTO.getSigla());
            turma.setNumeroSala(turmaDTO.getNumeroSala());
            turma.setNome(turmaDTO.getNome());
            turma.setProfessor(turmaDTO.getProfessor());

            turma = turmaRepository.save(turma);

            return Optional.of(turmaDTO.fromTurma(turma));
        }else {
            return Optional.empty();
        }
    }
    //add aluno
    public boolean addAlunoTurma(Long id, Long idAluno){
        //busca a turma e verifica se ela existe
        Optional<Turma> optionalTurma = turmaRepository.findById(id);
        if (!optionalTurma.isPresent()){
            return false;
        }

        //busca o aluno e verifica se ele existe
        Optional<Aluno> optionalAluno = alunoRepository.findById(idAluno);
        if (!optionalAluno.isPresent()){
            return false;
        }
        //instancia as entidades Turma e Aluno
        Turma turma = optionalTurma.get();
        Aluno aluno = optionalAluno.get();

        //atualiza a entidade aluno com a nova turma
        aluno.setTurma(turma);
        //salva no banco de dados
        alunoRepository.save(aluno);
        return true;
    }

    //remover aluno a turma
    public boolean removeAlunoTurma(Long id, Long idAluno){
        //busca o aluno e verifica se ele existe
        Optional<Aluno> optionalAluno = alunoRepository.findById(idAluno);
        if (!optionalAluno.isPresent()){
            return false;
        }

        Aluno aluno = optionalAluno.get();

        //verifica se o aluno tem uma turma
        //verifica se a turma que esta no aluno é realmente a turma que deseja remover
        if (aluno.getTurma() != null && aluno.getTurma().getId().equals(id)){
            aluno.setTurma(null);
            alunoRepository.save(aluno);
            return true;
        }
        return false;
    }

    public  boolean delete(Long id){
        if(turmaRepository.existsById(id)){
            turmaRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

}
