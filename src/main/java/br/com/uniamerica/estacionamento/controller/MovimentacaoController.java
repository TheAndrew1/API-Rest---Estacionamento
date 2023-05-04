package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado.")
                : ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Movimentacao> movimentacoess = this.movimentacaoRepository.findAll();

        return ResponseEntity.ok(movimentacoess);
    }

    @GetMapping("/abertos")
    public ResponseEntity<?> findByAberto(){
        final List<Movimentacao> movimentacoess = this.movimentacaoRepository.findBySaidaIsNull();

        return ResponseEntity.ok(movimentacoess);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);

            if(movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacao.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        try {
            final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
            if(movimentacao == null){
                throw new Exception("Registro inexistente");
            }

            movimentacao.setAtivo(false);
            this.movimentacaoRepository.delete(movimentacao);
            return ResponseEntity.ok("Registro não está mais ativo");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }
}
