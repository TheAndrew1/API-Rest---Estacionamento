package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloService modeloService;

    /* Sem usar Autowired
    public ModeloController(ModeloRepository modeloRepository){
        this.modeloRepository = modeloRepository;
    }*/

    /** http://localhost:8080/api/modelo/1 */
    /*@GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado.")
                : ResponseEntity.ok(modelo);
    }*/

    /** http://localhost:8080/api/modelo?id=1 */
    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloService.findById(id);
        return ResponseEntity.ok(modelo);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Modelo> modelos = this.modeloService.findAll();
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Modelo> modelos = this.modeloService.findByAtivo();
        return ResponseEntity.ok(modelos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try{
            this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Modelo modelo){
        try{
            this.modeloService.editar(id, modelo);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("id") final Long id){
        try{
            String mensagem = this.modeloService.excluir(id);
            return ResponseEntity.ok(mensagem);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
