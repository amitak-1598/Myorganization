package com.myorganization.responsemodel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailResponse extends Response {
	
	@NotNull
	@NotBlank
	private String Status;
	
	@NotNull
	@NotBlank
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Object data;
	
	@NotNull
	@NotBlank
	private String message;
	
	public String getStatus() {
		return Status;
	}
	
	public void setStatus(String status) {
		this.Status=status;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data=data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
