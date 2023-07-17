package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    public List<Condutor> findByAtivoIsTrue();
//
//    @Query("from Condutor where nome like :nome")
//    public List<Condutor> findByPlacaLike(@Param("nome") final String nome);
//
    public Condutor findByCpf(String cpf);

    public Condutor findByTelefone(String telefone);

    @Query(value = "select * from condutores where ativo=true", nativeQuery = true)
    public List<Condutor> findByAtivo();
}