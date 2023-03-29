package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    public List<Modelo> findByModelo(final String modelo);

    @Query("from Modelo where placa like :modelo")
    public List<Modelo> findByModeloLike(@Param("modelo") final String modelo);

    @Query(value = "select * from modelos where placa like :modelo", nativeQuery = true)
    public List<Modelo> findByModeloLikeNative(@Param("modelo") final String modelo);
}