package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
//    public List<Marca> findByMarca(final String marca);
//
//    @Query("from Marca where marca like :marca")
//    public List<Marca> findByMarcaLike(@Param("marca") final String marca);
//
//    @Query(value = "select * from marcas where marca like :marca", nativeQuery = true)
//    public List<Marca> findByMarcaLikeNative(@Param("marca") final String marca);
}