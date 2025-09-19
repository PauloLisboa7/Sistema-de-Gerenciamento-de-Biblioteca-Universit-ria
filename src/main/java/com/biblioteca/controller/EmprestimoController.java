package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return emprestimoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Emprestimo criar(@RequestBody Emprestimo emprestimo) {
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

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Emprestimo> confirmarDevolucao(@PathVariable Long id) {
        try {
            Emprestimo emprestimoConfirmado = emprestimoService.confirmarDevolucao(id);
            return ResponseEntity.ok(emprestimoConfirmado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
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
