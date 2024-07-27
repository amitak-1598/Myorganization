package com.myorganization.controller;

import java.util.Map;

import com.myorganization.service.EmployeesService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller
public class PostController {

	@Inject
	EmployeesService employeeservice;

	@Post("/employee")
	public HttpResponse<String> postEmployee(@Body Map<String, Object> body, @QueryValue("source") String source) {

		try {
			String response = employeeservice.postEmployee(body);
			return HttpResponse.created(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			return HttpResponse.badRequest("Something Went Wrong");
		}

	}
}
