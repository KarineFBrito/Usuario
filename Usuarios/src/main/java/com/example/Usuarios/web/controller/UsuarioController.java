package com.example.Usuarios.web.controller;

import com.example.Usuarios.entity.Usuario;
import com.example.Usuarios.service.UsuarioService;
import com.example.Usuarios.web.dto.UsuarioCreateDto;
import com.example.Usuarios.web.dto.UsuarioResponseDto;
import com.example.Usuarios.web.dto.UsuarioSenhaDto;
import com.example.Usuarios.web.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Contém todas as operações relativas ao rescursos de cadastro, edição e leitura de um usuário")
@RestController
@RequestMapping("api/v3/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;


    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.criar(usuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> AlterarSenha(@PathVariable @Valid Long id, @RequestBody UsuarioSenhaDto senhaDto){
        Usuario user = usuarioService.changePassword(id, senhaDto.getSenhaAtual(), senhaDto.getNovaSenha(), senhaDto.getConfirmarSenha());
    return ResponseEntity.ok("Senha alterada com sucesso");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Usuario user = usuarioService.buscarId(id);
        usuarioService.delete(id);
        return ResponseEntity.ok(String.format("Usuário(a) %s deletado(a)", user.getUsername()));
    }
}

