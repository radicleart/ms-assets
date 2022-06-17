package com.radicle.assets.common.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTHandlerInterceptor implements HandlerInterceptor {

	@Value("${radicle.lsat.paths}") String lsatPaths;
	@Value("${radicle.lsat.lsat-server}") String lsatRedirect;
	@Value("${radicle.lsat.lsat-verify}") String lsatVerify;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
