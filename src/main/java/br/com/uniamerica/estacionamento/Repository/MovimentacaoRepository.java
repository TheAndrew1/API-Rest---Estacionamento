package br.com.uniamerica.estacionamento.Repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
//    public List<Movimentacao> findByEntrada(final LocalDate entrada);
//
//    @Query("from Movimentacao where entrada like :entrada")
//    public List<Movimentacao> findByEntradaLike(@Param("entrada") final LocalDate entrada);
//
    @Query(value = "select * from movimentacoes where saida<>null", nativeQuery = true)
    public List<Movimentacao> findByAberto();
}