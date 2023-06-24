package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    public List<Modelo> findByAtivoIsTrue();
    public Modelo findByNome(String nome);
    @Query(value = "select * from modelos where ativo=true", nativeQuery = true)
    public List<Modelo> findByAtivo();
}