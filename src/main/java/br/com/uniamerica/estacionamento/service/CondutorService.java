package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CondutorService {
    @Autowired
    public CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public Condutor findById(final Long id){
        return this.condutorRepository.findById(id).orElse(null);
    }

    public List<Condutor> findAll(){
        return this.condutorRepository.findAll();
    }

    public List<Condutor> findByAtivo(){
        return this.condutorRepository.findByAtivoIsTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Condutor condutor, Boolean... editado){
        //Arrumar bug com put, ou colocar Setter no id e adicionar id pelo código
        Assert.isTrue(condutor.getCpf().length() == 14, "CPF inválido!");
        Assert.isTrue(condutor.getCpf().matches("[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}"), "Formato do CPF inválido!");
        Assert.isTrue(condutor.getTelefone().length() == 14, "Telefone inválido!");
        Assert.isTrue(condutor.getTelefone().matches("([0-9]{2}) 9[0-9]{4}-[0-9]{4}"), "Formato do telefone inválido!");

        if(editado == null) {
            Condutor condutorDatabase = this.condutorRepository.findByCpf(condutor.getCpf());
            Assert.isNull(condutorDatabase, "Condutor já cadastrado!");
        }
        else{
            if(!condutor.getTempoPago().equals(Duration.of(0, ChronoUnit.HOURS))){
                Configuracao configuracao = this.configuracaoRepository.findById(1L).orElse(null);
                Assert.notNull(configuracao, "Configuração não encontrada!");

//                Long times = condutor.getTempoPago().dividedBy(configuracao.getTempoParaDesconto());
//                condutor.setTempoDesconto(Duration.of(configuracao.getTempoDesconto().multipliedBy(times)), ChronoUnit.HOURS);
            }
        }

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Condutor condutor){
        Condutor condutorDatabase = findById(id);
        Assert.notNull(condutorDatabase, "Condutor não encontrado!");
        Assert.isTrue(condutorDatabase.getId().equals(condutor.getId()), "Condutores não conferem!");

        cadastrar(condutor, true);
    }

    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){
        Condutor condutor = findById(id);
        Assert.notNull(condutor, "Condutor não encontrado!");

        final List<Movimentacao> movimentacoes = this.movimentacaoRepository.findAll();
        for(Movimentacao movimentacao : movimentacoes){
            if(condutor.equals(movimentacao.getCondutor())){
                condutor.setAtivo(false);
                this.condutorRepository.save(condutor);
                return "Condutor está inativo!";
            }
        }

        this.condutorRepository.delete(condutor);
        return "Condutor excluido com sucesso!";
    }
}
