package com.br.foodconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(@NotBlank String email, @NotBlank @Size(min = 6, message = "Min 6 characters") String password) {
}
