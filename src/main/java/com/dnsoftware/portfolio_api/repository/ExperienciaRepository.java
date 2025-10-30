package com.dnsoftware.portfolio_api.repository;

import com.dnsoftware.portfolio_api.model.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Long> {
    List<Experiencia> findByPessoaIdOrderByDataInicioDesc(Long pessoaId);
}
