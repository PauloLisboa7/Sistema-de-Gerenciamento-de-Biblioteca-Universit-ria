package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);
        return emprestimo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/emprestar")
    public ResponseEntity<Emprestimo> emprestarLivro(@RequestParam Long usuarioId, @RequestParam Long livroId) {
        Emprestimo emprestimo = emprestimoService.emprestarLivro(usuarioId, livroId);
        if (emprestimo == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(emprestimo);
    }

    @PostMapping("/devolver/{id}")
    public ResponseEntity<Void> devolverLivro(@PathVariable Long id) {
        boolean sucesso = emprestimoService.devolverLivro(id);
        if (!sucesso) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
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
