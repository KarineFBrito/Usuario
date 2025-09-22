package com.example.Usuarios.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class UsuarioCreateDto {

    @NotBlank
    @Size(min = 5)
    private String username;
    @NotBlank
    @Size(min = 5, max = 12)
    private String password;

}
