package me.simple.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

// HandlerInterceptorAdapter
// HandlerInterceptor
// HandlerInterceptorAdapter
public class IPInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(IPInterceptor.class);
    private static final List<String> allowedIPList = Lists.newArrayList();
    private static final List<String> deniedIPList = Lists.newArrayList();
    
//    static {
//	try {
//	    File file = ResourceUtils.getFile("classpath:config.properties");
//	    Reader reader = new FileReader(file);
//	} catch (Exception e) {
//	}
//    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	String ip = request.getRemoteAddr();
	String host = request.getRemoteHost();
	String disabled = "1";
	if("1".equals(disabled) || "true".equalsIgnoreCase(disabled)){
	    return true;
	}
	logger.info("IP Interceptor ,  ip: {}, host: {}", ip,host);
	if(deniedIPList.contains(ip)){
	    // response.sendError(HttpStatus.FORBIDDEN.value());
	    return false;
	}
	if(allowedIPList.contains(ip)){
	    return true;
	}
	// response.sendError(HttpStatus.FORBIDDEN.value());
	return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	    ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	    throws Exception {
    }

}
