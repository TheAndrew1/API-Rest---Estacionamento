package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Getter @Setter
    @Column(name = "saida")
    private LocalDateTime saida;

    @Getter @Setter
    @Column(name = "tempo")
    private Long tempo;

    @Getter @Setter
    @Column(name = "tempo_multa")
    private Long tempoMulta;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private Long tempoDesconto;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "veiculo", nullable = false)
    private Veiculo veiculo;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "condutor", nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "valor")
    private BigDecimal valor;

    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    @Getter @Setter
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @PrePersist
    private void preEntrada(){
        this.entrada = LocalDateTime.now();
        this.tempoMulta = 0L;
        this.tempoDesconto = 0L;
    }
}
