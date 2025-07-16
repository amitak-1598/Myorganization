package com.myorganization.responsemodel;

import java.util.Map;

import org.json.JSONObject;

public interface ResponseFormat {
	   Response getSuccessResponse(Object data);
		   
		   Response getSuccessResponse(String data);
		   
		   Response getFailResponse(Exception e);
		   
		   Response getFailResponse(Object data);
		   
		   Response getFailResponse();
		   
		   Response getFailResponse(String data);
		   
		   Response getFailResponse(JSONObject data);
		   
		   Response getFailResponse(Map<String,Object> e);
		   
		   Response getErrorResponse(Exception e, String message);
		   
		   Response getErrorResponse(String message);

	}

