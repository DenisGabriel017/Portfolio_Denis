package com.dnsoftware.portfolio_api.repository;

import com.dnsoftware.portfolio_api.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    List<Imagem> findByProjetoIdOrderByOrdemAsc(Long projetoId);
}
