package com.enigma.teamtaskmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 1, max = 200, message = "First name can contain between 1 - 200 characters")
    private String firstname;
    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 1, max = 200, message = "Last name can contain between 1 - 200 characters")
    private String lastname;
    @Email
    private String email;
}
