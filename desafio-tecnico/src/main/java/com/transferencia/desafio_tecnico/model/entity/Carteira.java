package com.transferencia.desafio_tecnico.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CARTEIRA")
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_carteira")
    @SequenceGenerator(name = "seq_carteira", sequenceName = "seq_carteira", allocationSize = 1)
    @Column(name = "ID_CARTEIRA")
    private Long id_carteira;

    @Column(name = "VALOR", nullable = false)
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;
}
