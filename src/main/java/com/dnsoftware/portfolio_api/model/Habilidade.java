package com.dnsoftware.portfolio_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "habilidade")
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String nivel;
    private String icone;

    @ManyToMany
    private Set<Projeto> projetos = new HashSet<>();


}
