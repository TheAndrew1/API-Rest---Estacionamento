package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
//    public List<Condutor> findByPlaca(final String nome);
//
//    @Query("from Condutor where nome like :nome")
//    public List<Condutor> findByPlacaLike(@Param("nome") final String nome);
//
//    @Query(value = "select * from condutores where nome like :nome", nativeQuery = true)
//    public List<Condutor> findByPlacaLikeNative(@Param("nome") final String nome);
}