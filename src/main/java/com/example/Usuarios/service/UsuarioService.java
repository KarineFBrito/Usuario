package com.example.Usuarios.service;

import com.example.Usuarios.entity.Usuario;
import com.example.Usuarios.exception.EntityNotFoundException;
import com.example.Usuarios.exception.PasswordInvalidException;
import com.example.Usuarios.exception.UsernameUniqueViolationException;
import com.example.Usuarios.repository.UsuariosRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuariosRepository usuarioRepository;


    @Transactional
    public Usuario criar(Usuario usuario) {
        try {
            if(usuarioRepository.existsByUsername(usuario.getUsername())){
                throw new UsernameUniqueViolationException("Usuário com esse nome já existe");
            }
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException(String.format("Usuario {%s} já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Page<Usuario> buscarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario buscarId(Long id){
        Usuario user = usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado")
        );
        return user;
    }

    @Transactional
    public Usuario changePassword(Long id, @NotBlank @Size(min = 6, max = 12) String password, @NotBlank @Size(min = 6, max = 12) String newPassword, @NotBlank @Size(min = 6, max = 12) String confirmNewPassword) {
        Usuario user = buscarId(id);
        if(!password.equals(user.getPassword())){
            throw new PasswordInvalidException("Senha atual incorreta");
        }

        if(!newPassword.equals(confirmNewPassword)){
            throw new PasswordInvalidException("Alteração de senha negada");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional
    public void delete(Long id) {
        Usuario user = usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado")
        );
        usuarioRepository.delete(user);
    }
}
