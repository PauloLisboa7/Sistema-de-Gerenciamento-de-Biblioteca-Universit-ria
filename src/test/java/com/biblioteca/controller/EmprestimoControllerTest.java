package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.EmprestimoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmprestimoController.class)
public class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmprestimoService emprestimoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllEmprestimos() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        when(emprestimoService.findAll()).thenReturn(Arrays.asList(emprestimo));

        mockMvc.perform(get("/api/emprestimos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuario.nome").value("João Silva"))
                .andExpect(jsonPath("$[0].livro.titulo").value("Livro Teste"));
    }

    @Test
    public void testGetEmprestimoById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);

        when(emprestimoService.findById(1L)).thenReturn(Optional.of(emprestimo));

        mockMvc.perform(get("/api/emprestimos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.nome").value("João Silva"));
    }

    @Test
    public void testGetEmprestimoByIdNotFound() throws Exception {
        when(emprestimoService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/emprestimos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateEmprestimo() throws Exception {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuarioId(1L);
        emprestimo.setLivroId(1L);
        emprestimo.setDataEmprestimo(LocalDate.now());

        Emprestimo savedEmprestimo = new Emprestimo();
        savedEmprestimo.setId(1L);
        savedEmprestimo.setUsuario(new Usuario());
        savedEmprestimo.setLivro(new Livro());

        when(emprestimoService.save(any(Emprestimo.class))).thenReturn(savedEmprestimo);

        mockMvc.perform(post("/api/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emprestimo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateEmprestimo() throws Exception {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataDevolucao(LocalDate.now().plusDays(7));

        Emprestimo updatedEmprestimo = new Emprestimo();
        updatedEmprestimo.setId(1L);
        updatedEmprestimo.setDataDevolucao(LocalDate.now().plusDays(7));

        when(emprestimoService.findById(1L)).thenReturn(Optional.of(new Emprestimo()));
        when(emprestimoService.save(any(Emprestimo.class))).thenReturn(updatedEmprestimo);

        mockMvc.perform(put("/api/emprestimos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emprestimo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataDevolucao").exists());
    }

    @Test
    public void testDeleteEmprestimo() throws Exception {
        when(emprestimoService.findById(1L)).thenReturn(Optional.of(new Emprestimo()));

        mockMvc.perform(delete("/api/emprestimos/1"))
                .andExpect(status().isNoContent());

        verify(emprestimoService, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateEmprestimoInvalidUsuario() throws Exception {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuarioId(999L); // ID inexistente
        emprestimo.setLivroId(1L);

        when(emprestimoService.save(any(Emprestimo.class))).thenThrow(new RuntimeException("Usuário não encontrado"));

        mockMvc.perform(post("/api/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emprestimo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateEmprestimoInvalidLivro() throws Exception {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuarioId(1L);
        emprestimo.setLivroId(999L); // ID inexistente

        when(emprestimoService.save(any(Emprestimo.class))).thenThrow(new RuntimeException("Livro não encontrado"));

        mockMvc.perform(post("/api/emprestimos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emprestimo)))
                .andExpect(status().isBadRequest());
    }
}
