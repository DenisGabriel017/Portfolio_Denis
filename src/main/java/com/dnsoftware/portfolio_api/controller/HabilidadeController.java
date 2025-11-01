package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Habilidade;
import com.dnsoftware.portfolio_api.repository.HabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/habilidade")
public class HabilidadeController {

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Habilidade criarHabilidade(@RequestBody Habilidade habilidade){
        return habilidadeRepository.save(habilidade);
    }

    @GetMapping
    public List<Habilidade> buscarTodasHabilidades(){
        return habilidadeRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletarPorID(@PathVariable Long id){
        habilidadeRepository.deleteById(id);
    }
}
