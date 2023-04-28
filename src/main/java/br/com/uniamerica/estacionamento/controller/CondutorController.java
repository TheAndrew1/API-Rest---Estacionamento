package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.Repository.CondutorRepository;
import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
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
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado.")
                : ResponseEntity.ok(condutor);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Condutor> condutores = this.condutorRepository.findAll();

        return ResponseEntity.ok(condutores);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Condutor> condutores = this.condutorRepository.findByAtivo();

        return ResponseEntity.ok(condutores);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try{
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Condutor condutor){
        try{
            final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

            if(condutorBanco == null || !condutorBanco.getId().equals(condutor.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.condutorRepository.save(condutor);
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
            final Condutor condutor = this.condutorRepository.findById(id).orElse(null);
            if(condutor == null){
                throw new Exception("Registro inexistente");
            }

            final List<Movimentacao> movimentacaos = this.movimentacaoRepository.findAll();
            for(Movimentacao movimentacao : movimentacaos){
                if(condutor.equals(movimentacao.getCondutor())){
                    condutor.setAtivo(false);
                    this.condutorRepository.save(condutor);
                    return ResponseEntity.ok("Registro não está mais ativo");
                }
            }

            if(condutor.isAtivo()){
                this.condutorRepository.delete(condutor);
                return ResponseEntity.ok("Registro deletado com sucesso");
            }
            else{
                throw new Exception("Não foi possível excluir o registro");
            }
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }
}
