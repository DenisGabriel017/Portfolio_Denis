package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.model.Projeto;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import com.dnsoftware.portfolio_api.repository.ProjetoRepository;
import com.dnsoftware.portfolio_api.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ProjetoService projetoService;



    @PostMapping("/{pessoaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Projeto> criarProjeto(
            @PathVariable Long pessoaId,
            @RequestBody Projeto projeto
    ){
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);

        if (pessoaOptional.isPresent()){
            Pessoa pessoa = pessoaOptional.get();
            projeto.setPessoa(pessoa);

            Projeto projetoSalvo = projetoRepository.save(projeto);
            return ResponseEntity.status(HttpStatus.CREATED).body(projetoSalvo);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public List<Projeto> buscarTodosProjetos(){
        return projetoRepository.findAll();
    }

    @GetMapping("/pessoa/{pessoaId}")
    public List<Projeto> buscarProjetosPorPessoa(@PathVariable Long pessoaId){
        return projetoRepository.findByPessoaId(pessoaId);
    }

    @PostMapping("/{projetoId}/habilidades/{habilidadeId}")
    public ResponseEntity<Projeto> adicionarHabilidade(
            @PathVariable Long projetoId,
            @PathVariable Long habilidadeId){

        Optional<Projeto> projetoAtualizado = projetoService.adicionarHabilidadeAoProjeto(projetoId, habilidadeId);

        if (projetoAtualizado.isPresent()){
            return ResponseEntity.ok(projetoAtualizado.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }



}
