package com.biblioteca.service;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroService livroService;

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public Emprestimo emprestarLivro(Long usuarioId, Long livroId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Livro> livroOpt = livroRepository.findById(livroId);

        if (usuarioOpt.isPresent() && livroOpt.isPresent()) {
            Livro livro = livroOpt.get();
            if (livro.isDisponivel()) {
                livroService.emprestarLivro(livroId);
                Emprestimo emprestimo = new Emprestimo(usuarioOpt.get(), livro, LocalDate.now());
                return emprestimoRepository.save(emprestimo);
            }
        }
        return null;
    }

    public Emprestimo devolverLivro(Long emprestimoId) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimoId);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            if (emprestimo.getDataDevolucao() == null) {
                emprestimo.setDataDevolucao(LocalDate.now());
                livroService.devolverLivro(emprestimo.getLivro().getId());
                return emprestimoRepository.save(emprestimo);
            }
        }
        return null;
    }

    public Emprestimo salvar(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    public void deletar(Long id) {
        emprestimoRepository.deleteById(id);
    }
}
