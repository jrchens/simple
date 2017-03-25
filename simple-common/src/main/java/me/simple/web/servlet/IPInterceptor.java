package me.simple.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

// HandlerInterceptorAdapter
// HandlerInterceptor
// HandlerInterceptorAdapter
public class IPInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(IPInterceptor.class);
    private static final List<String> authorizedIPList = Lists.newArrayList();
    private static final List<String> deniedIPList = Lists.newArrayList();
    private boolean enable = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	String ip = request.getRemoteAddr();
	String host = request.getRemoteHost();
	if (enable) {
	    logger.info("IP Interceptor ,  ip: {}, host: {}", ip, host);
	    if (deniedIPList.contains(ip)) {
		// response.sendError(HttpStatus.FORBIDDEN.value());
		return false;
	    }
	    if (authorizedIPList.contains(ip)) {
		return true;
	    }
	    // response.sendError(HttpStatus.FORBIDDEN.value());
	}
	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	    ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	    throws Exception {
    }

    public void setAuthorizedIps(String authorizedIps) {
	String[] data = StringUtils.tokenizeToStringArray(authorizedIps, ",", true, true);
	if (data != null && data.length > 0) {
	    for (String ip : data) {
		authorizedIPList.add(ip);
	    }
	}
    }

    public void setDeniedIps(String deniedIps) {
	String[] data = StringUtils.tokenizeToStringArray(deniedIps, ",", true, true);
	if (data != null && data.length > 0) {
	    for (String ip : data) {
		deniedIPList.add(ip);
	    }
	}
    }

    public void setEnable(boolean enable) {
	this.enable = enable;
    }

}
