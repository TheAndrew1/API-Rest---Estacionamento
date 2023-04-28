package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDate entrada;

    @Getter @Setter
    @Column(name = "saida")
    private LocalDate saida;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column(name = "tempo")
    private LocalTime tempo;

    @Getter @Setter
    @Column(name = "tempo_multao")
    private LocalTime tempoMulta;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "veiculo", nullable = false)
    private Veiculo veiculo;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "condutor", nullable = false)
    private Condutor condutor;

    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Getter @Setter
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;
}
