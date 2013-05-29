package com.ozhou.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
	public static final String AUTH_USER = "authenticatedUser";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String authUser = (String) httpServletRequest.getSession()
				.getAttribute(AUTH_USER);

		StatusExposingServletResponse response = new StatusExposingServletResponse(
				(HttpServletResponse) servletResponse);

		if (authUser == null) {
			response.setStatus(401);
			response.getWriter().print("{}");
			response.getWriter().flush();
		} else {
			filterChain.doFilter(servletRequest, response);
		}

	}

	@Override
	public void destroy() {
	}
}
