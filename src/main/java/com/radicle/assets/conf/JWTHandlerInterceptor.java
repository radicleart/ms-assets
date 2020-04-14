package com.radicle.assets.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

@Component
public class JWTHandlerInterceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager.getLogger(JWTHandlerInterceptor.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String Identity_Address = "IdentityAddress";
	@Value("${radicle.lsat.paths}") String lsatPaths;
	@Value("${radicle.lsat.lsat-server}") String lsatRedirect;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
            logger.info("Handling: " + handler + " path: " + request.getRequestURI());
            logger.info("remote host: " + request.getRemoteHost());
            logger.info("request url: " + request.getRequestURL());
            logger.info("Method: " + request.getMethod());
			if (handler instanceof HandlerMethod) {
				String path = request.getRequestURI();
				if (isProtected(path)) {
					String address = request.getHeader(Identity_Address);
					String authToken = request.getHeader(AUTHORIZATION);
					if (authToken != null) {
						authToken = authToken.split(" ")[1]; // stripe out Bearer string before passing along..
						request.getSession().setAttribute("USERNAME", authToken);
					}
				    response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
		            response.setHeader("Location", response.encodeRedirectURL(lsatRedirect));
		            response.setHeader("Content-Type", "application/json");
		            response.setHeader("Access-Control-Allow-Origin", "*");
				    //response.sendRedirect(response.encodeRedirectURL(lsatRedirect));
				    return false;
				} else {
					logger.info("Authentication not required.");
				}
			} else if (handler instanceof AbstractHandlerMapping) {
				// error occurred..
				logger.info("Error mapping.");
			} else {
				logger.info("Unknown request.");
			}
		} catch (Exception e) {
			throw e;
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	private boolean isProtected(String path) {
		boolean protectd = false;
		String[] protectedPaths = lsatPaths.split(",");
		for (String subpath : protectedPaths) {
			if (path.indexOf(subpath) > -1) {
				protectd = true;
			}
		}
		return protectd;
	}
}