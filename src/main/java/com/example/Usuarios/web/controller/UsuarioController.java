package com.example.Usuarios.web.controller;

import com.example.Usuarios.entity.Usuario;
import com.example.Usuarios.service.UsuarioService;
import com.example.Usuarios.web.dto.UsuarioCreateDto;
import com.example.Usuarios.web.dto.UsuarioResponseDto;
import com.example.Usuarios.web.dto.UsuarioSenhaDto;
import com.example.Usuarios.web.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuários", description = "Contém todas as operações relativas ao rescursos de cadastro, edição, leitura e deletar de um usuário")
@RestController
@RequestMapping("api/v3/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;


    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        Usuario user = usuarioService.criar(usuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDto>> getAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Usuario> users = usuarioService.buscarTodos(pageable);
        Page<UsuarioResponseDto> userDto = users.map(UsuarioMapper::toDto);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> AlterarSenha(@PathVariable @Valid Long id, @RequestBody UsuarioSenhaDto senhaDto) {
        Usuario user = usuarioService.changePassword(id, senhaDto.getSenhaAtual(), senhaDto.getNovaSenha(), senhaDto.getConfirmarSenha());
        return ResponseEntity.ok("Senha alterada com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Usuario user = usuarioService.buscarId(id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

