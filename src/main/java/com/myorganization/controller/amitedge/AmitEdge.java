package com.myorganization.controller.amitedge;

import java.util.Map;
import org.hibernate.exception.ConstraintViolationException;
import com.myorganization.responsemodel.Response;
import com.myorganization.responsemodel.ResponseFormat;
import com.myorganization.service.ContactUsService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

@Controller
public class AmitEdge {

	@Inject
	ContactUsService contactusservice;

	@Inject
	ResponseFormat responseFormat;

	@Post("/contactus")
	public HttpResponse<Response> submitContactForm(@Body Map<String, Object> body,
			@QueryValue("source") String source) {
		try {
			String saved = contactusservice.postContactUs(body);
			Response success = responseFormat.getSuccessResponse(saved);
			return HttpResponse.status(HttpStatus.CREATED).body(success);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof ConstraintViolationException) {
				return HttpResponse.badRequest().body(responseFormat.getFailResponse(ex.getMessage()));
			}
			Response error = responseFormat.getErrorResponse(ex, "Failed to submit contactus form");
			return HttpResponse.serverError().body(error);
		}
	}

}
