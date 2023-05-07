package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Veiculo veiculo = this.veiculoService.findById(id);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Veiculo> veiculos = this.veiculoService.findAll();
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Veiculo> veiculos = this.veiculoService.findByAtivo();
        return ResponseEntity.ok(veiculos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        this.veiculoService.cadastrar(veiculo);
        return ResponseEntity.ok("Registro cadastrado com sucesso");
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
        this.veiculoService.editar(id, veiculo);
        return ResponseEntity.ok("Registro editado com sucesso");
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        return ResponseEntity.ok(this.veiculoService.excluir(id));
    }
}
