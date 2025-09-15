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
        // Marcar livro como indisponível ao emprestar
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(false);
        livroRepository.save(livro);
        return emprestimoRepository.save(emprestimo);
    }

    public void deletar(Long id) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            // Marcar livro como disponível ao devolver
            Livro livro = emprestimo.getLivro();
            livro.setDisponivel(true);
            livroRepository.save(livro);
        }
        emprestimoRepository.deleteById(id);
    }
}
