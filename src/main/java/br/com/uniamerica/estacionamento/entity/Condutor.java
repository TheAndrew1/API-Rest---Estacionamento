package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "condutores", schema = "public")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, unique = true, length = 15)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_pago")
    private Duration tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private Duration tempoDesconto;

    @PrePersist
    private void preSave(){
        this.tempoPago = Duration.of(0, ChronoUnit.MINUTES);
        this.tempoDesconto = Duration.of(0, ChronoUnit.MINUTES);
    }
}
