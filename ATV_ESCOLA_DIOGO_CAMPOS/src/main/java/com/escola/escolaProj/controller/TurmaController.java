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

@RestController // mostra que a classe controller será a espinha dorsal gerenciada pelo Spring
@RequestMapping("/turma") // determina qual método HTTP será utilizado e qual sua URL, no caso, turma será a URL
public class TurmaController {

    @Autowired // Anotação para que seja possível utilizar as instâncias do Repository
    private TurmaService turmaService; // Aqui está a regra de negócios

//    Essa anotação serve para buscar todas as turmas cadastradas, ou seja, ela consegue listar todas as turmas que existem
//    no sistema. Por exemplo, imagine que o diretor de uma rede quer receber um relatório com todas as turmas do seu banco de dados.
//    Em vez de procurar uma por uma, ele pode usar o @GetMapping, que vai retornar a lista completa de todas as turmas de uma vez só.
    @GetMapping
    public List<Turma> getAll(@RequestParam(required = false) String nome){ // o get pegará os elementos para realizar as operações, no caso será o All,
        // que significa que ele buscará tudo que contém nele, que no caso é um List<Turma>, e no caso seu @ResquestParam será false, pois não estamos
        // requisitando nenhum parametro com isso retornará tudo, mas se o parametro nome for passado, o retorno será a turma com aquele mesmo nome
        if (nome != null && !nome.isEmpty()){
            return turmaService.getAllTurmasByNome(nome); // busca com base no nome
        }
        return  turmaService.getAllTurmas(); // caso o nome não seja especificado, retornará tudo no List
    }

    //Essa anotação é usada para localizar uma turma específica pelo seu ID. Por exemplo:
    //Sou professor em uma escola com 100 turmas. Se eu fosse procurar uma por uma, levaria bastante tempo.
    // Então, uso o @GetMapping("/{id}") junto com a rota do ID.
    //Assim, o processo fica mais rápido: basta eu saber o ID da turma que quero!
    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> getById(@PathVariable Long id){ // o get pegará os elementos para realizar as operações, no caso será o Id,
        // que significa que ele retornará as informações contidas no ID, especificado, pegará os elementos @PathVariable utilizado quando o valor da variável
        // é passado diretamente na URL, no caso, é o ID
        Optional<TurmaDTO> turmaDTOOptional = turmaService.getById(id); // ele vai retornar os dados da turma através do ID dela. Já o Optional<> vai encapsular o retorno dos métodos
        if (turmaDTOOptional.isPresent()){ // te retornará verdadeiro se o resultado não for null
            return ResponseEntity.ok(turmaDTOOptional.get()); // retorna ok, se a turma for encontrada
        }else { // se não
            return  ResponseEntity.notFound().build(); // retorna notFound se a turma não for encontrado
        }
    }

//    Essa anotação é usada para criar uma turma. Por exemplo:
//    Depois das férias de junho, a escola abriu novas turmas.
//    Com a anotação @PostMapping, posso criar essas novas turmas.
    @PostMapping
    public ResponseEntity<TurmaDTO> create(@RequestBody TurmaDTO turmaDTO){// o ResponseEntity, vai controlar de maneira inteligente o que será respondido na requisição
        // @RequestBody é utilizada quando é necessário requisitar um corpo de argumentos em HTTP no caso toda a turmaDTO, será necessário, menos o ID
        // por ser auto-incremento
        TurmaDTO turmaDTOsave = turmaService.createTurma(turmaDTO); // cria a turma
        return  ResponseEntity.status(HttpStatus.CREATED).body(turmaDTOsave); // Caso a requisição for feita de maneira correta
        // te retorna um status de 201 que é CREATED
    }

    // Essa anotação é utilizada para atualizar uma turma, basta ela existir
    // Nela se utiliza a rota do ID para que seja possível encontrar a turma que o docente, ou quem quer que seja
    // Possa atualizar a turma!
    @PutMapping("/{id}")
    public  ResponseEntity<TurmaDTO> update(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO){// o ResponseEntity, vai controlar de maneira inteligente
        // o que será respondido na requisição
        // @RequestBody é utilizada quando é necessário requisitar um corpo de argumentos em HTTP no caso toda a turmaDTO
        // @PathVariable é utilizado quando o valor da variável é passado diretamente na URL, no caso, é o ID
        Optional<TurmaDTO> turmaDTOOptional = turmaService.updateTurma(id, turmaDTO); // ele vai retornar os dados da turma através do ID dela, com isso
        // é possível atualizar passando os parâmetros da TurmaDTO
        // Já o Optional<> vai encapsular o retorno dos métodos
        if (turmaDTOOptional.isPresent()){// te retornará verdadeiro se o resultado não for null
            return ResponseEntity.ok(turmaDTOOptional.get()); // retorna ok, se a turma for encontrada
        }else { // se não
            return ResponseEntity.notFound().build(); // retorna notFound se a turma não for encontrado
        }
    }
    // adiciona um aluno a turma
    @PutMapping("/{id}/aluno-add/{idAluno}") // é necessário passar o id da turma em que o aluno será inserido, pois a turma será atualizada
    public ResponseEntity<String> addAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){ // o ResponseEntity, vai controlar de maneira inteligente
        // o que será respondido na requisição
        // @PathVariable é utilizado quando o valor da variável é passado diretamente na URL, no caso, é o ID e o IdAluno
        if (turmaService.addAlunoTurma(id, idAluno)){ // adiciona o aluno
        return ResponseEntity.ok("Aluno adicionado com sucesso"); // retorna ok, caso a requisição deu certo
        }else { // se não
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno ou Professor não encontrado"); // retorna NOT FOUND caso a requisição falhe
        }
    }

    //remove um aluno
    @PutMapping("/{id}/aluno-remove/{idAluno}")// é necessário passar o id da turma e do aluno
    public ResponseEntity<String> removeAlunoTurma(@PathVariable Long id, @PathVariable Long idAluno){ // o ResponseEntity, vai controlar de maneira inteligente
        // o que será respondido na requisição
        // @PathVariable é utilizado quando o valor da variável é passado diretamente na URL, no caso, é o ID e o IdAluno
        if (turmaService.removeAlunoTurma(id, idAluno)){ // remove o aluno
            return ResponseEntity.ok("Aluno removido da turma com sucesso");// retorna ok se o aluno foi encontrado e removido
        }else { // se não
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao remover aluno da turma"); // retorna NOT FOUND se o aluno não foi encontrado e removido
        }
    }

//Deleta uma turma existente
    @DeleteMapping("/{id}") // é necessário o ID da turma
    public ResponseEntity<Void> delete(@PathVariable Long id){// o ResponseEntity, vai controlar de maneira inteligente
        // o que será respondido na requisição
        if (turmaService.delete(id)){ // deleta o aluno
            return ResponseEntity.noContent().build(); // indica que a solicitação foi bem sucedida
        }else { // se não
            return ResponseEntity.notFound().build(); // indica que a solicitação não foi bem sucedida
        }
    }
   }

