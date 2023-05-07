package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CondutorService {
    @Autowired
    public CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

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
    public void cadastrar(final Condutor condutor){
        //Arrumar bug com put, ou colocar Setter no id e adicionar id pelo código
//        Assert.isTrue(veiculo.getPlaca().length() == 7 || veiculo.getPlaca().length() == 8, "Placa inválida!");
//        if(veiculo.getPlaca().length() == 8){
//            Assert.isTrue(veiculo.getPlaca().matches("[a-zA-Z]{3}-[0-9]{4}"), "Formato da placa inválido!");
//        }
//        if(veiculo.getPlaca().length() == 7){
//            Assert.isTrue(veiculo.getPlaca().matches("[a-zA-Z]{3}[0-9]{1}[a-zA-Z]{1}[0-9]{2}"), "Formato da placa inválido!");
//        }
//        veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
        Condutor condutorDatabase = this.condutorRepository.findByCpf(condutor.getCpf());
        Assert.isNull(condutorDatabase, "Condutor já cadastrado!");

        this.condutorRepository.save(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Condutor condutor){
        Condutor condutorDatabase = findById(id);
        Assert.notNull(condutorDatabase, "Condutor não encontrado!");
        Assert.isTrue(condutorDatabase.getId().equals(condutor.getId()), "Condutores não conferem!");

        cadastrar(condutor);
    }

    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){
        Condutor condutor = findById(id);
        Assert.notNull(condutor, "Condutor não encontrado!");

        final List<Movimentacao> movimentacoes = this.movimentacaoRepository.findAll();
        for(Movimentacao movimentacao : movimentacoes){
            if(condutor.equals(movimentacao.getVeiculo())){
                condutor.setAtivo(false);
                this.condutorRepository.save(condutor);
                return "Condutor está inativo!";
            }
        }

        this.condutorRepository.delete(condutor);
        return "Condutor excluido com sucesso!";
    }
}
