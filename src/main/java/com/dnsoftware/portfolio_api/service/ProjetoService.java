package com.dnsoftware.portfolio_api.service;

import com.dnsoftware.portfolio_api.model.Habilidade;
import com.dnsoftware.portfolio_api.model.Projeto;
import com.dnsoftware.portfolio_api.repository.HabilidadeRepository;
import com.dnsoftware.portfolio_api.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    public Optional<Projeto> adicionarHabilidadeAoProjeto(Long projetoId, Long habilidadeId){
        Optional<Projeto> projetoOpt = projetoRepository.findById(projetoId);
        Optional<Habilidade> habilidadeOpt = habilidadeRepository.findById(habilidadeId);

        if (projetoOpt.isPresent() && habilidadeOpt.isPresent()){
            Projeto projeto = projetoOpt.get();
            Habilidade habilidade = habilidadeOpt.get();

            projeto.getHabilidades().add(habilidade);
            return Optional.of(projetoRepository.save(projeto));
        }
        return Optional.empty();
    }
}
