package com.actio.actio_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserProfileDTO {
    //@NotBlank
    private String fullName;

    //@NotBlank
    //@Pattern(regexp = "\\d{9}", message = "NIF must have 9 digits")
    private String nif;

    //@NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Garante formato JSON correto
    private LocalDate birthDate;

    //@Email
    //@NotBlank
    private String email;


}

