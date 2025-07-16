package com.myorganization.responsemodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Primary
@Singleton
public class ResponseFormatImp implements ResponseFormat{
	
	Response resp;

	@Override
	public Response getSuccessResponse(String data) {
		resp = new SuccessResponse();
		resp.setStatus("succeed");
		Map<String, Object> message = new HashMap<>();
		message.put("message", data);
		resp.setData(message);
		return resp;

	}

	@Override
	public Response getSuccessResponse(Object data) {
		resp = new SuccessResponse();
		resp.setStatus("succeed");
		if (data instanceof List<?>) {
			if (((List) data).isEmpty()) {
				resp.setData(null);
			} else {
				resp.setData(data);
			}

		} else if (data instanceof Map) {
			if (((Map) data).isEmpty()) {
				resp.setData(null);
			} else {
				resp.setData(data);
			}
		} else {
			resp.setData(data);
		}
		return resp;
	}

	@Override
	public Response getFailResponse(Map<String, Object> data) {
		resp = new FailResponse();
		resp.setStatus("fail");
		resp.setData(data);
		return resp;
	}

	@Override
	public Response getFailResponse(Exception e) {
		resp = new FailResponse();
		resp.setStatus("fail");
		Map<String, Object> message = new HashMap<>();
		message.put("message", e.getMessage());
		resp.setData(message);
		return resp;
	}

	@Override
	public Response getFailResponse() {
		resp = new FailResponse();
		resp.setStatus("fail");
		resp.setData(null);
		return resp;
	}
//
	@Override
	public Response getFailResponse(Object data) {
		resp = new FailResponse();
		resp.setStatus("fail");
		resp.setData(data);
		return resp;
	}

	@Override
	public Response getFailResponse(String data) {
		resp = new FailResponse();
		resp.setStatus("fail");
		Map<String, Object> message = new HashMap<>();
		message.put("message", data);
		resp.setData(message);
		return resp;
	}

	@Override
	public Response getFailResponse(JSONObject data) {
		resp = new FailResponse();
		resp.setStatus("fail");
		resp.setData(data.toMap());
		return resp;
	}

	@Override
	public Response getErrorResponse(String message) {
		resp = new ErrorResponse();
		resp.setStatus("error");
		resp.setMessage(message);
		return resp;
	}

	@Override
	public Response getErrorResponse(Exception e, String message) {
		resp = new ErrorResponse();
		resp.setStatus("error");
		resp.setMessage(message);
		return resp;
	}

}

