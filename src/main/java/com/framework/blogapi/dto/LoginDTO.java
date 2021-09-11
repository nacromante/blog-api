package com.framework.blogapi.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

	@NotBlank(message = "Informe o E-mail")
	private String email;
	@NotBlank(message = "Informe a Senha")
	private String password;

}