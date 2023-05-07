package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorService condutorService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorService.findById(id);
        return ResponseEntity.ok(condutor);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Condutor> condutorres = this.condutorService.findAll();
        return ResponseEntity.ok(condutorres);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Condutor> condutorres = this.condutorService.findByAtivo();
        return ResponseEntity.ok(condutorres);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        this.condutorService.cadastrar(condutor);
        return ResponseEntity.ok("Registro cadastrado com sucesso");
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Condutor condutor){
        this.condutorService.editar(id, condutor);
        return ResponseEntity.ok("Registro editado com sucesso");
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        return ResponseEntity.ok(this.condutorService.excluir(id));
    }
}
