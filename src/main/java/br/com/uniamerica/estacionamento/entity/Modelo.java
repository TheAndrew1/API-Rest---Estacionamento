package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelo", schema = "public")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "marca", nullable = false)
    private Marca marca;
    @Getter @Setter
    @Column(name = "modelo", nullable = false, unique = true, length = 50)
    private String modelo;
}