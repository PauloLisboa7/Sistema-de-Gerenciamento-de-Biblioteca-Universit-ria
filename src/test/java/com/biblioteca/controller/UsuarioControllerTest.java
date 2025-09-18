package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setMatricula("12345");
        usuario.setCurso("Engenharia");
        usuario.setEmail("joao@email.com");

        when(usuarioService.listarTodos()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    public void testGetUsuarioByIdNotFound() throws Exception {
        when(usuarioService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria Santos");
        usuario.setMatricula("67890");
        usuario.setCurso("Medicina");
        usuario.setEmail("maria@email.com");

        Usuario savedUsuario = new Usuario();
        savedUsuario.setId(1L);
        savedUsuario.setNome("Maria Santos");
        savedUsuario.setMatricula("67890");
        savedUsuario.setCurso("Medicina");
        savedUsuario.setEmail("maria@email.com");

        when(usuarioService.salvar(any(Usuario.class))).thenReturn(savedUsuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("João Silva Atualizado");

        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setId(1L);
        updatedUsuario.setNome("João Silva Atualizado");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(new Usuario()));
        when(usuarioService.salvar(any(Usuario.class))).thenReturn(updatedUsuario);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deletar(1L);
    }

    @Test
    public void testCreateUsuarioInvalidData() throws Exception {
        Usuario usuario = new Usuario(); // Dados inválidos, sem nome

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isBadRequest());
    }
}
