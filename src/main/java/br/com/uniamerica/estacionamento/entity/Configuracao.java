package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Entity
@Table(name = "configuracao", schema = "public")
public class Configuracao  extends AbstractEntity{
    @Getter @Setter
    @Column(name = "van")
    private int vagasVan;
    @Getter @Setter
    @Column(name = "carro")
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "moto")
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "desconto")
    private BigDecimal desconto;
}
