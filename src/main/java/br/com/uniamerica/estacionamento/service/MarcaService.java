package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MarcaService {
    @Autowired
    public MarcaRepository marcaRepository;
    @Autowired
    public ModeloRepository modeloRepository;

    public Marca findById(final Long id){
        return this.marcaRepository.findById(id).orElse(null);
    }

    public List<Marca> findAll(){
        return this.marcaRepository.findAll();
    }

    public List<Marca> findByAtivo(){
        return this.marcaRepository.findByAtivoIsTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Marca marca){
        //Arrumar bug com put, ou colocar Setter no id e adicionar id pelo código
        Marca marcaDatabase = this.marcaRepository.findByMarca(marca.getMarca());
        Assert.isNull(marcaDatabase, "Marca já cadastrada!");
        Assert.isTrue(!(marca.getMarca().length() < 3), "Nome de marca inválido!");

        this.marcaRepository.save(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Marca marca){
        Marca marcaDatabase = findById(id);
        Assert.notNull(marcaDatabase, "Marca não encontrado!");
        Assert.isTrue(marcaDatabase.getId().equals(marca.getId()), "Marcas não conferem!");

        cadastrar(marca);
    }

    @Transactional(rollbackFor = Exception.class)
    public String excluir(final Long id){
        Marca marca = findById(id);
        Assert.notNull(marca, "Marca não encontrado!");

        final List<Modelo> modelos = this.modeloRepository.findAll();
        for(Modelo modelo : modelos){
            if(marca.equals(modelo.getMarca())){
                marca.setAtivo(false);
                this.marcaRepository.save(marca);
                return "Marca está inativa!";
            }
        }

        this.marcaRepository.delete(marca);
        return "Marca excluida com sucesso!";
    }
}
