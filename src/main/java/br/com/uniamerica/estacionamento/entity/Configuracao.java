package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Table(name = "configuracoes", schema = "public")
public class Configuracao  extends AbstractEntity{
    @Getter @Setter
    @Column(name = "vagas_van", nullable = false)
    private int vagasVan;

    @Getter @Setter
    @Column(name = "vagas_carro", nullable = false)
    private int vagasCarro;

    @Getter @Setter
    @Column(name = "vagas_moto", nullable = false)
    private int vagasMoto;

    @Getter @Setter
    @Column(name = "horario_fecha", nullable = false)
    private LocalTime horarioFecha;

    @Getter @Setter
    @Column(name = "valor_hora", nullable = false)
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor_multa_hora", nullable = false)
    private BigDecimal valorMultaHora;

    @Getter @Setter
    @Column(name = "tempo_para_desconto", nullable = false)
    private Duration tempoParaDesconto;

    @Getter @Setter
    @Column(name = "tempo_desconto", nullable = false)
    private Duration tempoDesconto;

    @Getter @Setter
    @Column(name = "desconto", nullable = false)
    private boolean desconto;
}
