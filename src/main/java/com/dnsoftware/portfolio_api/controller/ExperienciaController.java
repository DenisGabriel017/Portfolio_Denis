package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Experiencia;
import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.repository.ExperienciaRepository;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/experiencias")
public class ExperienciaController {

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping("/{pessoaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Experiencia> criarExperiencia(
            @PathVariable Long pessoaId,
            @RequestBody Experiencia experiencia
    ){
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);

        if (pessoaOptional.isPresent()){
            experiencia.setPessoa(pessoaOptional.get());
            Experiencia experienciaSalva = experienciaRepository.save(experiencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(experienciaSalva);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pessoa/{pessoaId}")
    public List<Experiencia> buscarExperienciasPorPessoa(@PathVariable Long pessoaId){
        return experienciaRepository.findByPessoaIdOrderByDataInicioDesc(pessoaId);
    }



}
