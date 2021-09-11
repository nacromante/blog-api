package com.framework.blogapi.exceptions;

//@ApiModel(description = "Objeto de detalhes de erros")
public class ErrorDetails {

//	@ApiModelProperty(notes = "Message de erro", example = "Sua solicitação não foi encontrada", dataType = "string", value = "message")
	private String message;

	public ErrorDetails(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
