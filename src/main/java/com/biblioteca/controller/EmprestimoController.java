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
    public Emprestimo salvar(@RequestBody java.util.Map<String, Integer> request) {
        int usuarioId = request.get("usuarioId");
        int livroId = request.get("livroId");
        return emprestimoService.createEmprestimo((long)usuarioId, (long)livroId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizar(@PathVariable Long id, @RequestBody java.util.Map<String, Object> request) {
        int usuarioId = (Integer) request.get("usuarioId");
        int livroId = (Integer) request.get("livroId");
        String dataDevolucaoStr = (String) request.get("dataDevolucao");
        Emprestimo atualizado = emprestimoService.updateEmprestimo(id, usuarioId, livroId, dataDevolucaoStr);
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
