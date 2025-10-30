package com.dnsoftware.portfolio_api.repository;

import com.dnsoftware.portfolio_api.model.Habilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabilidadeRepository extends JpaRepository<Habilidade,Long> {
    Optional<Habilidade> findByNomeIgnoreCase(String nome);
}
