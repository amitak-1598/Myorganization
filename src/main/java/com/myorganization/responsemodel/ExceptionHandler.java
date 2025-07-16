package com.myorganization.responsemodel;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;

public interface ExceptionHandler {
	public HttpResponse<Response> exceptionhandler(HttpRequest<?> request, Exception ex);

}
