package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController{
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado.")
                : ResponseEntity.ok(marca);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> findAll(){
        final List<Marca> marcas = this.marcaRepository.findAll();

        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> findByAtivo(){
        final List<Marca> marcas = this.marcaRepository.findByAtivoIsTrue();

        return ResponseEntity.ok(marcas);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try{
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try{
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

            if(marcaBanco == null || !marcaBanco.getId().equals(marca.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.marcaRepository.save(marca);
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
            final Marca marca = this.marcaRepository.findById(id).orElse(null);
            if(marca == null){
                throw new Exception("Registro inexistente");
            }

            final List<Modelo> modelos = this.modeloRepository.findAll();

            for(Modelo modelo : modelos){
                if(marca.equals(modelo.getMarca())){
                    marca.setAtivo(false);
                    this.marcaRepository.save(marca);
                    return ResponseEntity.ok("Registro não está mais ativo");
                }
            }

            if(marca.isAtivo()){
                this.marcaRepository.delete(marca);
                return ResponseEntity.ok("Registro deletado com sucesso");
            }
            else{
                throw new Exception("Não foi possível excluir o registro");
            }
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }
}
