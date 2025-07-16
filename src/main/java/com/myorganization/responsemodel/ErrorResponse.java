package com.myorganization.responsemodel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends Response{
	
	@NotNull
    @NotBlank
	private String Status;
	@NotNull
	@NotBlank
    private String Message;
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		this.Message=message;
	}
}


