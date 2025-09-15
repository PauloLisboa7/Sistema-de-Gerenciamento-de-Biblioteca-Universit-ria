package com.biblioteca.service;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

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
                livro.setDisponivel(false);
                livroRepository.save(livro);

                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setUsuario(usuarioOpt.get());
                emprestimo.setLivro(livro);
                emprestimo.setDataEmprestimo(LocalDate.now());
                return emprestimoRepository.save(emprestimo);
            }
        }
        return null; // Ou lançar exceção
    }

    public boolean devolverLivro(Long emprestimoId) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimoId);
        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            Livro livro = emprestimo.getLivro();
            livro.setDisponivel(true);
            livroRepository.save(livro);

            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimoRepository.save(emprestimo);
            return true;
        }
        return false;
    }

    public Emprestimo salvar(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    public void deletar(Long id) {
        emprestimoRepository.deleteById(id);
    }
}
