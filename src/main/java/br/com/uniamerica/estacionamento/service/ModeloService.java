package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.swing.text.html.Option;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
//    @Autowired
//    private VeiculoService veiculoService;

    public Modelo findById(final Long id){
        return this.modeloRepository.findById(id).orElse(null);
    }

    public List<Modelo> findAll(){
        return this.modeloRepository.findAll();
    }

    public List<Modelo> findByAtivo(){
        return this.modeloRepository.findByAtivoIsTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Modelo modelo){
        //Arrumar bug com put, ou colocar Setter no id e adicionar id pelo código
        Marca marca = this.marcaRepository.findById(modelo.getMarca().getId()).orElse(null);
        Assert.notNull(marca, "Marca não está cadastrada!");
        if(!marca.isAtivo()){
            marca.setAtivo(true);
            this.marcaRepository.save(marca);
        }

        Modelo modeloDatabase = this.modeloRepository.findByModelo(modelo.getModelo());
        Assert.isNull(modeloDatabase, "Modelo já cadastrado!");
        Assert.isTrue(!(modelo.getModelo().length() < 3), "Nome de modelo inválido!");

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Modelo modelo){
        Modelo modeloDatabase = findById(id);
        Assert.notNull(modeloDatabase, "Modelo não encontrado!");
        Assert.isTrue(modeloDatabase.getId().equals(modelo.getId()), "Modelos não conferem!");

        cadastrar(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){
        Modelo modelo = findById(id);
        Assert.notNull(modelo, "Modelo não encontrado!");

        final List<Veiculo> veiculos = this.veiculoRepository.findAll();    //Trocar pelo veiculoService depois
        for(Veiculo veiculo : veiculos){
            if(modelo.equals(veiculo.getModelo())){
                modelo.setAtivo(false);
                this.modeloRepository.save(modelo);
                return "Modelo está inativo!";
            }
        }

        this.modeloRepository.delete(modelo);
        return "Modelo excluido com sucesso!";
    }
}
