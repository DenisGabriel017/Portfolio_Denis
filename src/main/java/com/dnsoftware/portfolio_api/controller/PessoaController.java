package com.dnsoftware.portfolio_api.controller;


import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criarPessoa (@RequestBody Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    @GetMapping
    public List<Pessoa> buscarTodasPessoas(){
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPessoaPorID(@PathVariable Long id){
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isPresent()){
            return ResponseEntity.ok(pessoa.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
