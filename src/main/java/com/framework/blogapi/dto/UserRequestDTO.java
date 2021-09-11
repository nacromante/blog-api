package com.framework.blogapi.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserRequestDTO {

	@Email(message = "E-mail inválido")
	@NotBlank(message = "Informe o usuário")
	private String email;

	@NotBlank(message = "Informe o nome")
	private String name;

	@NotBlank(message = "Informe a senha")
	private String password;

}