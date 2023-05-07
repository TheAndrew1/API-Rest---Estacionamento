package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoService.findById(id);
        return ResponseEntity.ok(configuracao);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        this.configuracaoService.cadastrar(configuracao);
        return ResponseEntity.ok("Registro cadastrado com sucesso");
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Configuracao configuracao){
        this.configuracaoService.editar(id, configuracao);
        return ResponseEntity.ok("Registro editado com sucesso");
    }
}
