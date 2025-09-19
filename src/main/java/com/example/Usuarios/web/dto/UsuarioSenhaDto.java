package com.example.Usuarios.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioSenhaDto {

    @NotBlank
    @Size(min = 6, max = 12)
    private String senhaAtual;
    @NotBlank
     @Size(min = 6, max = 12)
    private String novaSenha;
    @NotBlank
    @Size(min = 6, max = 12)
    private String confirmarSenha;
}
