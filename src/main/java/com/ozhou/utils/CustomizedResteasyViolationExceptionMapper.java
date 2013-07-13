package com.ozhou.utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyViolationExceptionMapper;
import org.springframework.stereotype.Component;

@Component
@Provider
public class CustomizedResteasyViolationExceptionMapper extends
		ResteasyViolationExceptionMapper {

	@Override
	protected Response buildResponse(Object entity, String mediaType,
			Status status) {
		ExceptionDto exceptionDto = new ExceptionDto("ValidationException",
					entity.toString());
		ResponseBuilder builder = Response.status(status).entity(exceptionDto);
		builder.type(MediaType.APPLICATION_JSON_TYPE);
		return builder.build();
	}

}
