package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class VeiculoService {
    @Autowired
    public VeiculoRepository veiculoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public Veiculo findById(final Long id){
        return this.veiculoRepository.findById(id).orElse(null);
    }

    public Veiculo findByPlaca(final String placa){ return this.veiculoRepository.findByPlaca(placa); }

    public List<Veiculo> findAll(){
        return this.veiculoRepository.findAll();
    }

    public List<Veiculo> findByAtivo(){
        return this.veiculoRepository.findByAtivoIsTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo, Boolean... editado){
        Assert.isTrue(veiculo.getPlaca().length() == 7 || veiculo.getPlaca().length() == 8, "Placa inválida!");
        if(veiculo.getPlaca().length() == 8){
            Assert.isTrue(veiculo.getPlaca().matches("[a-zA-Z]{3}-[0-9]{4}"), "Formato da placa inválido!");
        }
        if(veiculo.getPlaca().length() == 7){
            Assert.isTrue(veiculo.getPlaca().matches("[a-zA-Z]{3}[0-9]{1}[a-zA-Z]{1}[0-9]{2}"), "Formato da placa inválido!");
        }

        veiculo.setPlaca(veiculo.getPlaca().toUpperCase());

        if(editado.length == 0){
            Veiculo veiculoDatabase = this.veiculoRepository.findByPlaca(veiculo.getPlaca());
            Assert.isNull(veiculoDatabase, "Veiculo já cadastrado!");
        }

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Veiculo veiculo){
        Veiculo veiculoDatabase = findById(id);
        Assert.notNull(veiculoDatabase, "Veiculo não encontrado!");
        Assert.isTrue(veiculoDatabase.getId().equals(veiculo.getId()), "Veiculos não conferem!");

        veiculoDatabase = this.veiculoRepository.findByPlaca(veiculo.getPlaca());
        if(veiculoDatabase != null){
            Assert.isTrue(veiculoDatabase.getId().equals(veiculo.getId()), "Placa já cadastrada!");
        }

        cadastrar(veiculo, true);
    }

    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){
        Veiculo veiculo = findById(id);
        Assert.notNull(veiculo, "Veiculo não encontrado!");

        final List<Movimentacao> movimentacoes = this.movimentacaoRepository.findAll();
        for(Movimentacao movimentacao : movimentacoes){
            if(veiculo.equals(movimentacao.getVeiculo())){
                veiculo.setAtivo(false);
                this.veiculoRepository.save(veiculo);
                return "Veiculo está inativo!";
            }
        }

        this.veiculoRepository.delete(veiculo);
        return "Veiculo excluido com sucesso!";
    }
}
