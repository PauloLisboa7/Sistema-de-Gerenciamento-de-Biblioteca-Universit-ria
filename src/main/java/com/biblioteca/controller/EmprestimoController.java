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

    @PostMapping
    public Emprestimo salvar(@RequestBody Emprestimo emprestimo) {
        return emprestimoService.salvar(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizar(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        Emprestimo atualizado = emprestimoService.atualizar(id, emprestimo);
        return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        emprestimoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/emprestar/{usuarioId}/{livroId}")
    public ResponseEntity<Emprestimo> emprestarLivro(@PathVariable Long usuarioId, @PathVariable Long livroId) {
        Emprestimo emprestimo = emprestimoService.emprestarLivro(usuarioId, livroId);
        return emprestimo != null ? ResponseEntity.ok(emprestimo) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/devolver/{emprestimoId}")
    public ResponseEntity<Void> devolverLivro(@PathVariable Long emprestimoId) {
        boolean sucesso = emprestimoService.devolverLivro(emprestimoId);
        return sucesso ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
