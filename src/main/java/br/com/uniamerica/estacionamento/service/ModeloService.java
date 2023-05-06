package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.swing.text.html.Option;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;

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
        Modelo modeloDatabase = this.modeloRepository.findByModelo(modelo.getModelo());
        Assert.isNull(modeloDatabase, "Modelo já cadastrado!");

        Marca marca = this.marcaRepository.findById(modelo.getMarca().getId()).orElse(null);
        Assert.notNull(marca, "Marca não está cadastrada!");
        if(!marca.isAtivo()){
            marca.setAtivo(true);
            this.marcaRepository.save(marca);
        }

        this.modeloRepository.save(modelo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Modelo modelo){
        Modelo modeloDatabase = this.modeloRepository.findByModelo(modelo.getModelo());
        Assert.isNull(modeloDatabase, "Modelo já cadastrado!");

        Marca marca = this.marcaRepository.findById(modelo.getMarca().getId()).orElse(null);
        Assert.notNull(marca, "Marca não está cadastrada!");
        if(!marca.isAtivo()){
            marca.setAtivo(true);
            this.marcaRepository.save(marca);
        }

        this.modeloRepository.save(modelo);
    }
}
