package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    public List<Veiculo> findByAtivoIsTrue();
//
//    @Query("from Veiculo where placa like :placa")
//    public List<Veiculo> findByPlacaLike(@Param("placa") final String placa);
//
    @Query(value = "select * from veiculos where ativo=true", nativeQuery = true)
    public List<Veiculo> findByAtivo();
}