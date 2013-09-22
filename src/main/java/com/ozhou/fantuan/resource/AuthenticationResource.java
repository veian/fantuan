package com.ozhou.fantuan.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Path("/auth")
public class AuthenticationResource {

	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null)
			return securityContext.getAuthentication().getName();
		else
			return "";
	}

}
