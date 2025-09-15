package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);
        return emprestimo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/emprestar")
    public ResponseEntity<Emprestimo> emprestar(@RequestParam Long usuarioId, @RequestParam Long livroId) {
        Emprestimo emprestimo = emprestimoService.emprestarLivro(usuarioId, livroId);
        if (emprestimo != null) {
            return ResponseEntity.ok(emprestimo);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/devolver/{id}")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.devolverLivro(id);
        if (emprestimo != null) {
            return ResponseEntity.ok(emprestimo);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Emprestimo salvar(@RequestBody Emprestimo emprestimo) {
        return emprestimoService.salvar(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizar(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        if (!emprestimoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        emprestimo.setId(id);
        return ResponseEntity.ok(emprestimoService.salvar(emprestimo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!emprestimoService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        emprestimoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
