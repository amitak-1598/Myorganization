package com.myorganization.controller;

import java.util.Map;
import org.hibernate.exception.ConstraintViolationException;
import com.myorganization.responsemodel.Response;
import com.myorganization.responsemodel.ResponseFormat;
import com.myorganization.service.EmployeesService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller
public class PostController {

	@Inject
	EmployeesService employeeservice;

	@Inject
	ResponseFormat responseFormat;

	@Post("/employee")
	public HttpResponse<?> postEmployee(@Body Map<String, Object> body, @QueryValue("source") String source) {

		try {
			String response = employeeservice.postEmployee(body);
			Response success = responseFormat.getSuccessResponse(response);
			return HttpResponse.status(HttpStatus.CREATED).body(success);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof ConstraintViolationException) {
				return HttpResponse.badRequest().body(responseFormat.getFailResponse(ex.getMessage()));
			}
			Response error = responseFormat.getErrorResponse(ex, "Failed to create employee");
			return HttpResponse.serverError().body(error);
		}

	}
	
	
}
