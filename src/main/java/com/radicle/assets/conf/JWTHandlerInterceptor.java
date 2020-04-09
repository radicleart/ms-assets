package com.radicle.assets.conf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

@Component
public class JWTHandlerInterceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager.getLogger(JWTHandlerInterceptor.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String Identity_Address = "IdentityAddress";
	private static final String ALLOWED_PATHS = "/lightning/alice/getInfo /api/exchange/rates /bitcoin/getRadPayConfig /bitcoin/getPaymentAddress /trading/taxonomy/fetch /trading/user/contactEmail";
	private static final String ALLOWED_PATH_BTC = "/bitcoin/address";
	private static final String ALLOWED_PATH_LND = "/bitcoin/invoice";
	private static final String ALLOWED_PATH_INV = "/lightning";
	@Value("${radicle.security.lsat.paths}") List<String> lsatPaths;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			logger.info("Handling: " + handler + " path: " + request.getRequestURI());
			if (handler instanceof HandlerMethod) {
				String path = request.getRequestURI();
				if (!isProtected(path)) {
					String address = request.getHeader(Identity_Address);
					String authToken = request.getHeader(AUTHORIZATION);
					authToken = authToken.split(" ")[1]; // stripe out Bearer string before passing along..
				    response.sendRedirect("/login");
					request.getSession().setAttribute("USERNAME", authToken);
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
		boolean protectd = path.startsWith("/bitcoin") || path.startsWith("/lightning") || path.startsWith("/trading");
		if (ALLOWED_PATHS.indexOf(path) > -1) {
			protectd = false;
		} else if (path.startsWith(ALLOWED_PATH_BTC) || path.startsWith(ALLOWED_PATH_LND) || path.startsWith(ALLOWED_PATH_INV)) {
			protectd = false;
		}
		return protectd;
	}
}
