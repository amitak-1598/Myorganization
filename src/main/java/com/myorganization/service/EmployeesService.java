package com.myorganization.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myorganization.modelclass.Employees;
import com.myorganization.repository.EmployeesRepository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class EmployeesService {

	@Inject
	EmployeesRepository employeerepo;

	ObjectMapper globalMapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	@Transactional
	public String postEmployee(Map<String, Object> body) throws InterruptedException, ExecutionException {

		Employees employee = globalMapper.convertValue(body, Employees.class);
		employee.setDeleted(0);
		employee.setCreatedBy(employee.getCreatedBy());
		employee.setCreatedAt(LocalDateTime.now());
		employee.setUpdatedAt(LocalDateTime.now());
		employee.setUpdatedBy(employee.getCreatedBy());

		
		CompletableFuture<Employees> response = employeerepo.save(employee);
		response.get();
		return "Data Posted Successfully in Employee Table";
	}
}
