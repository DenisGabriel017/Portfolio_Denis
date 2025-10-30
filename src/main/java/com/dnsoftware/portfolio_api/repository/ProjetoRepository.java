package com.dnsoftware.portfolio_api.repository;

import com.dnsoftware.portfolio_api.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByPessoaId(Long pessoaId);
}
