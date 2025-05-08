package com.escola.escolaProj.controller;

import com.escola.escolaProj.Entity.Turma;
import com.escola.escolaProj.dto.TurmaDTO;
import com.escola.escolaProj.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Define que esta classe é um controlador REST.
@RequestMapping("/turma") // Define o caminho base das requisições para a controller ("/turma").
public class TurmaController {

    @Autowired // Injeta automaticamente a dependência do TurmaService.
    private TurmaService turmaService; // Responsável pela lógica de negócio.

    @GetMapping // Mapeia requisições HTTP GET.
    public List<Turma> getAll(@RequestParam(required = false) String nome){ //  // Lista todas as turmas ou busca por nome, se fornecido como parâmetro.
        if (nome != null && !nome.isEmpty()){ // Verifica se o parâmetro "nome" foi informado e não está null.
            return turmaService.getAllTurmasByNome(nome); // Retorna a lista de turmas que correspondem com o nome especificado.
        }
        return turmaService.getAllTurmas(); // Retorna todas as turmas se nenhum nome foi fornecido como parâmetro.
    }

    @GetMapping("/{id}") // Mapeia requisições GET com um ID na URL.
    public ResponseEntity<TurmaDTO> getById(@PathVariable Long id){ // Busca uma turma específica pelo ID.
        Optional<TurmaDTO> turmaDTOOptional = turmaService.getById(id); // Chama a service para buscar a turma pelo ID, retornando um Optional.
        if (turmaDTOOptional.isPresent()){ // Verifica se a turma foi encontrada.
            return ResponseEntity.ok(turmaDTOOptional.get()); // Retorna OK com o objeto TurmaDTO.
        } else {
            return ResponseEntity.notFound().build(); // Retorna Not Found se a turma não existir.
        }
    }

    @PostMapping // Mapeia requisições POST.
    public ResponseEntity<TurmaDTO> create(@RequestBody TurmaDTO turmaDTO){ // Cria uma nova turma a partir dos dados enviados no corpo da requisição.
        TurmaDTO turmaDTOsave = turmaService.createTurma(turmaDTO); // Chama a service para salvar a nova turma.
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaDTOsave); // Retorna Created com a turma criada.
    }

    @PutMapping("/{id}") // Mapeia requisições PUT com um ID na URL.
    public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO){ // Atualiza uma turma com base no ID.
        Optional<TurmaDTO> turmaDTOOptional = turmaService.updateTurma(id, turmaDTO); // Chama a service para atualizar a turma especificada.
        if (turmaDTOOptional.isPresent()){ // Verifica se a atualização foi bem-sucedida.
            return ResponseEntity.ok(turmaDTOOptional.get()); // Retorna OK com os dados atualizados.
        } else {
            return ResponseEntity.notFound().build(); // Retorna Not Found se a turma não existir.
        }
    }

    @PutMapping("/{id}/aluno-add/{idAluno}") // Mapeia requisição PUT para adicionar um aluno a uma turma.
    public ResponseEntity<String> addAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){ // Recebe o ID da turma e do aluno.
        if (turmaService.addAlunoTurma(id, idAluno)){ // Tenta adicionar o aluno na turma através da service.
            return ResponseEntity.ok("Aluno adicionado com sucesso"); // Retorna OK com mensagem de sucesso.
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno ou Turma não encontrado"); // Retorna com mensagem de erro.
        }
    }

    @PutMapping("/{id}/aluno-remove/{idAluno}") // Mapeia requisição PUT para remover um aluno de uma turma.
    public ResponseEntity<String> removeAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){ // Recebe o ID da turma e do aluno.
        if (turmaService.removeAlunoTurma(id, idAluno)){ // Tenta remover o aluno da turma.
            return ResponseEntity.ok("Aluno removido da turma com sucesso"); // Retorna OK se a remoção foi bem-sucedida.
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao remover aluno da turma"); // Retorna com mensagem de erro.
        }
    }

    @DeleteMapping("/{id}") // Mapeia requisição DELETE.
    public ResponseEntity<Void> delete(@PathVariable Long id){ // Recebe o ID da turma a ser deletada.
        if (turmaService.delete(id)){ // Chama a service para deletar a turma.
            return ResponseEntity.noContent().build(); // Retorna No Content se a exclusão foi bem-sucedida.
        } else {
            return ResponseEntity.notFound().build(); // Retorna Not Found se a turma não foi encontrada.
        }
    }
}