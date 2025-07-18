package com.myorganization.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.transaction.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myorganization.model.amitedge.ContactUs;
import com.myorganization.repository.ContactUsRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public class ContactUsService {

	@Inject
	ContactUsRepository contactUsRepository;

	ObjectMapper globalMapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	@Transactional
	public String postContactUs(Map<String, Object> body) throws InterruptedException, ExecutionException {

		ContactUs contactus = globalMapper.convertValue(body, ContactUs.class);
		contactus.setDeleted(0);
		contactus.setCreatedBy(contactus.getCreatedBy());
		contactus.setCreatedAt(LocalDateTime.now());
		contactus.setUpdatedAt(LocalDateTime.now());
		contactus.setUpdatedBy(contactus.getCreatedBy());

		CompletableFuture<ContactUs> response = contactUsRepository.save(contactus);
		response.get();
		return "contactus submitted successfully";
	}

}
