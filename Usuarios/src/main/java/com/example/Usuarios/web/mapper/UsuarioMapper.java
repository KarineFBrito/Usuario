package com.example.Usuarios.web.mapper;


import com.example.Usuarios.entity.Usuario;
import com.example.Usuarios.web.dto.UsuarioCreateDto;
import com.example.Usuarios.web.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto){
        return new  ModelMapper().map(createDto, Usuario.class);
    }
    public static UsuarioResponseDto toDto(Usuario usuario){
        return new ModelMapper().map(usuario, UsuarioResponseDto.class);
    }
    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios){
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}