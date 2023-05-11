package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

//    else{     //atualizar tempo do condutor
//        if(!condutor.getTempoPago().equals(Duration.of(0, ChronoUnit.HOURS))){
//            Configuracao configuracao = this.configuracaoRepository.findById(1L).orElse(null);
//            Assert.notNull(configuracao, "Configuração não encontrada!");
//
//            Long times = condutor.getTempoPago().dividedBy(configuracao.getTempoParaDesconto());
//            condutor.setTempoDesconto(Duration.of(configuracao.getTempoDesconto().multipliedBy(times)), ChronoUnit.HOURS);
//        }
//    }

    public Movimentacao findById(final Long id){
        return this.movimentacaoRepository.findById(id).orElse(null);
    }

    public List<Movimentacao> findAll(){
        return this.movimentacaoRepository.findAll();
    }

    public List<Movimentacao> findByAberto(){
        return this.movimentacaoRepository.findBySaidaIsNull();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao, Boolean... editado){
        if(editado.length != 0) {
            movimentacao.setSaida(LocalDateTime.now());
            movimentacao.setTempo(Duration.between(movimentacao.getEntrada(), movimentacao.getSaida()));
        }

        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Movimentacao movimentacao){
        Movimentacao movimentacaoDatabase = findById(id);
        Assert.notNull(movimentacaoDatabase, "Movimentação não encontrada!");
        Assert.isTrue(movimentacaoDatabase.getId().equals(movimentacao.getId()), "Movimentações não conferem!");

        cadastrar(movimentacao, true);
    }

    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){
        Movimentacao movimentacao = findById(id);
        Assert.notNull(movimentacao, "Movimentação não encontrada!");

        movimentacao.setAtivo(false);
        this.movimentacaoRepository.save(movimentacao);
        return "Movimentação está inativa!";
    }
}
