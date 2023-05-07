package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class ConfiguracaoService {
    @Autowired
    public ConfiguracaoRepository configuracaoRepository;

    public Configuracao findById(final Long id){
        return this.configuracaoRepository.findById(id).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Configuracao configuracao){
        //Arrumar bug com put, ou colocar Setter no id e adicionar id pelo código
        this.configuracaoRepository.save(configuracao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(final Long id, final Configuracao configuracao){
        Configuracao configuracaoDatabase = findById(id);
        Assert.notNull(configuracaoDatabase, "Configuração não encontrado!");
        Assert.isTrue(configuracaoDatabase.getId().equals(configuracao.getId()), "Configurações não conferem!");

        cadastrar(configuracao);
    }
}
