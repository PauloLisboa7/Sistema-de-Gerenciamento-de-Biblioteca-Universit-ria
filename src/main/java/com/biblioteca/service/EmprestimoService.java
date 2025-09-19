package com.biblioteca.service;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public Emprestimo salvar(Emprestimo emprestimo) {
        // Decrementar quantidade disponível ao emprestar
        Livro livro = emprestimo.getLivro();
        if (livro.getQuantidadeDisponivel() > 0) {
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
            livroRepository.save(livro);
            return emprestimoRepository.save(emprestimo);
        } else {
            throw new RuntimeException("Livro não disponível em estoque");
        }
    }

    public Emprestimo confirmarDevolucao(Long id) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            if (!emprestimo.isConfirmado()) {
                emprestimo.setConfirmado(true);
                // Incrementar quantidade disponível ao confirmar devolução
                Livro livro = emprestimo.getLivro();
                livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
                livroRepository.save(livro);
                return emprestimoRepository.save(emprestimo);
            }
        }
        throw new RuntimeException("Empréstimo não encontrado ou já confirmado");
    }

    public void deletar(Long id) {
        // Deletar empréstimo (cancelar sem devolver)
        emprestimoRepository.deleteById(id);
    }
}
