package com.myorganization.controller;

import com.myorganization.modelclass.Employees;
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
	UpdateService updateservice;

	@Put("/upEmployee")
	public HttpResponse<String> updateEmployeeField(@Body Employees body, @QueryValue("source") String source) {
		try {
			updateservice.updateEmployee(body);
			return HttpResponse.created("Data Updated Successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			return HttpResponse.badRequest("Something Went Wrong");
		}

	}
}
