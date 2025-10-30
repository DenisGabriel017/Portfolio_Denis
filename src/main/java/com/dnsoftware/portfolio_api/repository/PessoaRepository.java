package com.dnsoftware.portfolio_api.repository;

import com.dnsoftware.portfolio_api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>  {
    Pessoa findByEmail(String email);
    Pessoa findByNomeIgnoreCase(String nome);
}
