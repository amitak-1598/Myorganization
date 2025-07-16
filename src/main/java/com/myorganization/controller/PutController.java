package com.myorganization.controller;

import com.myorganization.modelclass.Employees;
import com.myorganization.responsemodel.Response;
import com.myorganization.responsemodel.ResponseFormat;
import com.myorganization.service.UpdateService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller
public class PutController {

	@Inject
	ResponseFormat responseFormat;

	@Inject
	UpdateService updateservice;

	@Put("/upEmployee")
	public HttpResponse<Response> updateEmployeeField(@Body Employees body, @QueryValue("source") String source) {
		try {
			updateservice.updateEmployee(body);

			Response success = responseFormat.getSuccessResponse("Employee updated successfully");

			return HttpResponse.ok(success);

		} catch (Exception ex) {
			ex.printStackTrace();

			Response error = responseFormat.getErrorResponse(ex, "Failed to update employee");

			return HttpResponse.serverError().body(error);
		}
	}
}
