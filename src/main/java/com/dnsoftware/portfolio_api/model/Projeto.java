package com.dnsoftware.portfolio_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projeto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pessoa", "imagens", "habilidades"})
@EqualsAndHashCode(exclude = {"pessoa", "imagens", "habilidades"})
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Lob
    private String descricao;
    private LocalDate dataConclusao;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens;

    private String linkRepositorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "projeto_habilidade",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id"
            ))
    private Set<Habilidade> habilidades = new HashSet<>();
}
