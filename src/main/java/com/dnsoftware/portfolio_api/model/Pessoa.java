package com.dnsoftware.portfolio_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String resumo;
    private String linkLinkedin;
    private String LinkGithub;

    @OneToMany(mappedBy = "pessoa",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Experiencia> experiencias = new HashSet<>();

    @OneToMany(mappedBy = "pessoa",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Projeto> projetos =  new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "pessoa_habilidade",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Habilidade> habilidades = new HashSet<>();

    public void adicionarExperiencia(Experiencia experiencia){
        experiencias.add(experiencia);
        experiencia.setPessoa(this);
    }
}
