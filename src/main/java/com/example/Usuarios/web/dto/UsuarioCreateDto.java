package com.example.Usuarios.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class UsuarioCreateDto {

    @NotBlank
    @Size(min = 5)
    private String username;
    @NotBlank
    @Size(min = 6, max = 12)
    private String password;

    public UsuarioCreateDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
