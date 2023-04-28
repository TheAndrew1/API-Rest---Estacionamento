package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    @Query(value = "select * from modelos where ativo=true", nativeQuery = true)
    public List<Modelo> findByAtivo();
}