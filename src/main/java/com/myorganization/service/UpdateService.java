package com.myorganization.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myorganization.modelclass.Audit;
import com.myorganization.modelclass.Employees;
import com.myorganization.repository.AuditRepository;
import com.myorganization.repository.EmployeesRepository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class UpdateService {

	@Inject
	EmployeesRepository employeerepo;
	@Inject
	AuditService auditservice;
	@Inject
	AuditRepository auditrepo;
	@Inject
	AuditField auditfield;

	ObjectMapper globalMapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	public void updateEmployee(Employees body) throws InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		Integer id = (Integer) body.getId();
		CompletableFuture<Employees> response = employeerepo.findById(id);

		Employees data = response.get();

		Map<String, Object> oldbody = globalMapper.convertValue(data, new TypeReference<Map<String, Object>>() {
		});

		System.out.println(oldbody);

		if (body.getAddress() != null) {
			data.setAddress(body.getAddress());
		}

		if (body.getUpdatedBy() != null) {
			data.setUpdatedBy(body.getUpdatedBy());
		}
		if (body.getEmail() != null) {
			data.setEmail(body.getEmail());
		}
		if (body.getPhone() != null) {
			data.setPhone(body.getPhone());
		}
		if (body.getFirstName() != null) {
			data.setFirstName(body.getFirstName());
		}
		if (body.getLastName() != null) {
			data.setLastName(body.getLastName());
		}
		if (body.getDeleted() != null) {
			data.setDeleted(body.getDeleted());
		}

		data.setUpdatedAt(LocalDateTime.now());

		CompletableFuture<Employees> responsedata = employeerepo.update(data);
		Employees responsedatas = responsedata.get();

		Map<String, Object> newbody = globalMapper.convertValue(responsedatas,
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println(newbody);

		List<Map<String, Object>> responses = auditservice.findDifferences(oldbody, newbody, data.getUpdatedBy(),"employee", 0);

		for (int k = 0; k < responses.size(); k++) {
			Audit audit = mapper.convertValue(responses.get(k), Audit.class);
			audit.setUpdatedAt(LocalDateTime.now());
			auditrepo.save(audit);
		}
	}

	public void updateEmployeeField(Employees body) throws InterruptedException, ExecutionException {

		Employees data1 = globalMapper.convertValue(body, Employees.class);
		Integer id = (Integer) body.getId();
		CompletableFuture<Employees> response = employeerepo.findById(id);

		auditfield.setInitials(id + "", "employees", body.getUpdatedBy());

		List<Audit> audits = new ArrayList<>();

		Employees data = response.get();
		
		if (!(Objects.equals(data1.getAddress(), data.getAddress()) || (data.getAddress() == null))) {
			audits.add(auditfield.genAudit(data1.getAddress(), data.getAddress(), "address"));
			data.setAddress(data1.getAddress());
		}

		if (!(Objects.equals(data1.getFirstName(), data.getFirstName()) || (data.getFirstName() == null))) {
			audits.add(auditfield.genAudit(data1.getFirstName(), data.getFirstName(), "firstname"));
			data.setFirstName(data1.getFirstName());
		}

		if (!(Objects.equals(data1.getLastName(), data.getLastName()) || (data.getLastName() == null))) {
			audits.add(auditfield.genAudit(data1.getLastName(), data.getLastName(), "lastname"));
			data.setLastName(data1.getLastName());
		}
		if (!(Objects.equals(data1.getPhone(), data.getPhone()) || (data.getPhone() == null))) {
			audits.add(auditfield.genAudit(data1.getPhone(), data.getPhone(), "phone"));
			data.setPhone(data1.getPhone());
		}
		if (!(Objects.equals(data1.getEmail(), data.getEmail()) || (data.getEmail() == null))) {
			audits.add(auditfield.genAudit(data1.getEmail(), data.getEmail(), "email"));
			data.setEmail(data1.getEmail());
		}
		if (!(Objects.equals(data1.getDateOfBirth(), data.getDateOfBirth()) || (data.getDateOfBirth() == null))) {
			audits.add(auditfield.genAudit(data1.getDateOfBirth() + "", data.getDateOfBirth() + "", "dateofbirth"));
			data.setDateOfBirth(data1.getDateOfBirth());
		}
		if (!(Objects.equals(data1.getDeleted(), data.getDeleted()) || (data.getDeleted() == null))) {
			audits.add(auditfield.genAudit(data1.getDeleted() + "", data.getDeleted() + "", "deleted"));
			data.setDeleted(data1.getDeleted());
		}

		if (!(Objects.equals(data1.getUpdatedBy(), data.getUpdatedBy()) || (data.getUpdatedBy() == null))) {
			audits.add(auditfield.genAudit(data1.getUpdatedBy(), data.getUpdatedBy(), "updatedby"));
			data.setUpdatedBy(data1.getUpdatedBy());
		}

		data.setUpdatedAt(LocalDateTime.now());
		
		CompletableFuture<Employees> updated = employeerepo.update(data);
		
		 auditrepo.saveAll(audits);

	}

}
