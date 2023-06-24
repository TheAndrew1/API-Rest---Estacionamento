package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
//@Audited
@Table(name = "modelos", schema = "public")
//@AuditTable(value = "condutores_audit", schema = "audit")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca", nullable = false)
    private Marca marca;
    @Getter @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;
}