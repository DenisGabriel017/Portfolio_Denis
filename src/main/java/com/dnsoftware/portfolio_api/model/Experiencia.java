package com.dnsoftware.portfolio_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;

@Entity
@Table(name = "experiencia")
@Data
@ToString(exclude = {"pessoa"})
@EqualsAndHashCode(exclude = {"pessoa"})
@NoArgsConstructor
@AllArgsConstructor
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String empresaInstituicao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Lob
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    public String exibirPeriodo(){
        String fim = (dataFim == null) ? "Atual" : dataFim.toString();
        return dataInicio.toString()+ " - " + fim;
    }

}
