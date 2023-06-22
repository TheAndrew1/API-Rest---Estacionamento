package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoService.findById(id);
        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Movimentacao> movimentacoes = this.movimentacaoService.findAll();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/abertas")
    public ResponseEntity<?> findByAtivo(){
        final List<Movimentacao> movimentacoes = this.movimentacaoService.findByAberto();
        return ResponseEntity.ok(movimentacoes);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoService.editar(id, movimentacao);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        try{
            String mensagem = this.movimentacaoService.excluir(id);
            return ResponseEntity.ok(mensagem);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
