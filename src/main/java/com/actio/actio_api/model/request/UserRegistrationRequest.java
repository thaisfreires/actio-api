package com.actio.actio_api.model.request;

import com.actio.actio_api.validation.annotation.Adult;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class UserRegistrationRequest {

    @NotNull
    @Length(min = 2, max = 250)
    private String name;

    @Size(min = 9, max = 9, message = "O NIF deve conter 9 digitos")
    @Pattern(regexp = "\\d{9}", message = "NIF inválido")
    @NotNull(message = "É obrigatório informar um NIF válido")
    private String nif;

    @NotNull(message = "É obrigatório informar a data de nascimento")
    @Adult()
    private LocalDateTime date_of_birth;

    @Column(unique = true)
    @NotNull(message = "É obrigatório informar o endereço de e-mail")
    @Email(message = "Endereço de e-mail inválido")
    private String email;

    @NotNull(message = "É obrigatório definir uma senha")
    @Length(min = 9, message = "A senha deve conter ao menos 9 caracteres")
    private String password;

}
