package com.dnsoftware.portfolio_api.service;

import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public Optional<Pessoa> buscarPerfilCompleto(Long pessoaId){
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById((pessoaId));
        return pessoaOpt;
    }
}
