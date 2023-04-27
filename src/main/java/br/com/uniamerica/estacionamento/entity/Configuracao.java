package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracoes", schema = "public")
public class Configuracao  extends AbstractEntity{
    private int vagasVan;
    private int vagasCarro;
    private int vagasMoto;
    private BigDecimal valorMulta;
    private boolean desconto;
}
