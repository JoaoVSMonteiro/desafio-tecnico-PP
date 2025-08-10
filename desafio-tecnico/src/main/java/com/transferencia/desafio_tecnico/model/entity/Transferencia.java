package com.transferencia.desafio_tecnico.model.entity;

import com.transferencia.desafio_tecnico.model.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRANSFERENCIA")
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transferencia")
    @SequenceGenerator(name = "seq_transferencia",sequenceName = "seq_transferencia", allocationSize = 1)
    @Column(name = "ID_TRANSFERENCIA")
    private Long idTransacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_TRANSACAO")
    private TipoTransacao tipoTransacao;

    @ManyToOne
    @JoinColumn(name = "PAGADOR", nullable = false)
    private Usuario pagador;

    @ManyToOne
    @JoinColumn(name = "RECEBEDOR", nullable = false)
    private Usuario recebedor;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "HORARIO")
    private LocalDateTime horario;

    @Column(name = "COMENTARIO", nullable = true)
    private String comentario;

}
