package com.actio.actio_api.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @Column(unique = true)
    @NotNull(message = "É obrigatório informar o endereço de e-mail para se autenticar no sistema")
    @Email(message = "Endereço de e-mail inválido")
    private String email;

    @NotNull(message = "É obrigatório informar a senha para se autenticar no sistema")
    private String password;

}
