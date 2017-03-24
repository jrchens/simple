package me.simple.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class AdvancedController {
    @Autowired
    private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(AdvancedController.class);

    @ExceptionHandler(value = { DataAccessException.class })
    public String dataAccessException(WebRequest request, Exception ex) throws Exception {
	String uuid = UUID.randomUUID().toString();
	
	String code = null;
	if (ex instanceof EmptyResultDataAccessException) {
	    code = "empty.result.data.access.exception";
	} else if (ex instanceof DuplicateKeyException) {
	    code = "duplicate.key.exception";
	} else if (ex instanceof DataIntegrityViolationException) {
	    code = "data.integrity.violation.exception";
	} else {
	    code = "data.access.exception";
	}
	String value = messageSource.getMessage(code, new Object[] { ex.getMessage() }, Locale.getDefault());
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ex.printStackTrace(pw);
	
	request.setAttribute("ERROR_MESSAGE", value, RequestAttributes.SCOPE_SESSION);
	request.setAttribute("ERROR_UUID", uuid, RequestAttributes.SCOPE_SESSION);
	request.setAttribute("ERROR_CAUSE", sw.toString(), RequestAttributes.SCOPE_SESSION);	
	
	logger.error(ex.getMessage() + ", ERROR_UUID : " + uuid, ex);
	return "admin/error";
    }
    

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public String noHandlerFoundException(WebRequest request, Exception ex) throws Exception {
	String uuid = UUID.randomUUID().toString();
	String value = messageSource.getMessage("no.handler.found.exception", new Object[] { }, Locale.getDefault());
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ex.printStackTrace(pw);
	
	request.setAttribute("ERROR_MESSAGE", value, RequestAttributes.SCOPE_SESSION);
	request.setAttribute("ERROR_UUID", uuid, RequestAttributes.SCOPE_SESSION);
	request.setAttribute("ERROR_CAUSE", sw.toString(), RequestAttributes.SCOPE_SESSION);	
	
	logger.error(ex.getMessage() + ", ERROR_UUID : " + uuid, ex);
	return "admin/error";
    }
    
    @ExceptionHandler(value = { IllegalArgumentException.class })
    public String illegalArgumentException(WebRequest request, Exception ex) throws Exception {
	String uuid = UUID.randomUUID().toString();
	String code = messageSource.getMessage(ex.getMessage(), null,null);
	String defaultMessage = messageSource.getMessage("illegal.argument.exception", null,null);
	String value = messageSource.getMessage("illegal.argument.exception.more", new Object[] { code }, defaultMessage, Locale.getDefault());
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ex.printStackTrace(pw);
	
	request.setAttribute("ERROR_MESSAGE", value, RequestAttributes.SCOPE_SESSION);
	request.setAttribute("ERROR_UUID", uuid, RequestAttributes.SCOPE_SESSION);
	request.setAttribute("ERROR_CAUSE", sw.toString(), RequestAttributes.SCOPE_SESSION);	
	
	logger.error(ex.getMessage() + ", ERROR_UUID : " + uuid, ex);
	return "admin/error";
    }
    
    
    
}
