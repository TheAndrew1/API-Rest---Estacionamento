package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

public class VeiculoService {
    @Autowired
    public VeiculoRepository veiculoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public Veiculo findById(final Long id){
        return this.veiculoRepository.findById(id).orElse(null);
    }

    public List<Veiculo> findAll(){
        return this.veiculoRepository.findAll();
    }

    public List<Veiculo> findByAtivo(){
        return this.veiculoRepository.findByAtivoIsTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){
        //Arrumar bug com put, ou colocar Setter no id e adicionar id pelo código
        //Assert.isTrue(!(veiculo.getMarca().length() < 3), "Nome de veiculo inválido!"); Validar placa
        Veiculo veiculoDatabase = this.veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isNull(veiculoDatabase, "Veiculo já cadastrada!");

        this.veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Veiculo veiculo){
        Veiculo veiculoDatabase = findById(id);
        Assert.notNull(veiculoDatabase, "Veiculo não encontrado!");
        Assert.isTrue(veiculoDatabase.getId().equals(veiculo.getId()), "Veiculos não conferem!");

        cadastrar(veiculo);
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
