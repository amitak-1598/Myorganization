package com.myorganization.responsemodel;

import java.util.HashMap;
import java.util.Map;
import io.micronaut.http.annotation.Error;
import org.hibernate.exception.ConstraintViolationException;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;

public class ExceptionHandlerImp implements ExceptionHandler {

	@Inject
	ResponseFormatImp respf;

	@Override
	@Error(global = true)
	public HttpResponse<Response> exceptionhandler(HttpRequest<?> request, Exception ex) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (ex instanceof ConstraintViolationException) {
			res.put("message", ex.getMessage());
			return HttpResponse.status(HttpStatus.BAD_REQUEST).body(respf.getFailResponse(res));
		}
		return HttpResponse.status(HttpStatus.BAD_REQUEST).body(respf.getFailResponse(res));
	}

}
