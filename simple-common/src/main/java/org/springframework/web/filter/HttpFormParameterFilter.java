package org.springframework.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

public class HttpFormParameterFilter extends OncePerRequestFilter {

    private final FormHttpMessageConverter formConverter = new AllEncompassingFormHttpMessageConverter();
    private static final Logger logger = LoggerFactory.getLogger(HttpFormParameterFilter.class);
    private String[] saveSuffixs = { "save" };
    private String[] modifySuffixs = { "update" };
    private String[] skipURI = { "login","logout" };

    /**
     * The default character set to use for reading form data.
     */
    public void setCharset(Charset charset) {
	this.formConverter.setCharset(charset);
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response,
	    FilterChain filterChain) throws ServletException, IOException {
	String uri = StringUtils.trimWhitespace(request.getRequestURI());
	logger.info("{}",uri);
	boolean skip = false;
	for (String _uri : skipURI) {
	    if(uri.endsWith(_uri)){
		skip = true;
		break;
	    }
	}
	
	if ("POST".equals(request.getMethod()) && !skip) {
	    Subject subject = SecurityUtils.getSubject();
	    Object user = subject.getPrincipal();
	    Timestamp now = new Timestamp(System.currentTimeMillis());
	    logger.info("{}, {}",user,now);
	    
	    HttpInputMessage inputMessage = new ServletServerHttpRequest(request) {
		@Override
		public InputStream getBody() throws IOException {
		    return request.getInputStream();
		}
	    };
	    MultiValueMap<String, String> formParameters = formConverter.read(null, inputMessage);
	    if (uri != null) {
		boolean bool = true;
		for (String modify : modifySuffixs) {
		    if (uri.endsWith(modify)) {
			formParameters.put("mduser", Lists.newArrayList(user.toString()));
			formParameters.put("mdtime", Lists.newArrayList(now.toString()));
			bool = false;
			break;
		    }
		}
		if (bool) {
		    for (String save : saveSuffixs) {
			if (uri.endsWith(save)) {
			    formParameters.put("cruser", Lists.newArrayList(user.toString()));
			    formParameters.put("crtime", Lists.newArrayList(now.toString()));
			    bool = false;
			    break;
			}
		    }
		}
	    }
	    HttpServletRequest wrapper = new HttpPutFormContentRequestWrapper(request, formParameters);
	    filterChain.doFilter(wrapper, response);
	} else {
	    filterChain.doFilter(request, response);
	}
    }

    private static class HttpPutFormContentRequestWrapper extends HttpServletRequestWrapper {

	private MultiValueMap<String, String> formParameters;

	public HttpPutFormContentRequestWrapper(HttpServletRequest request, MultiValueMap<String, String> parameters) {
	    super(request);
	    this.formParameters = (parameters != null ? parameters : new LinkedMultiValueMap<String, String>());
	}

	@Override
	public String getParameter(String name) {
	    String queryStringValue = super.getParameter(name);
	    String formValue = this.formParameters.getFirst(name);
	    return (queryStringValue != null ? queryStringValue : formValue);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
	    Map<String, String[]> result = new LinkedHashMap<String, String[]>();
	    Enumeration<String> names = getParameterNames();
	    while (names.hasMoreElements()) {
		String name = names.nextElement();
		result.put(name, getParameterValues(name));
	    }
	    return result;
	}

	@Override
	public Enumeration<String> getParameterNames() {
	    Set<String> names = new LinkedHashSet<String>();
	    names.addAll(Collections.list(super.getParameterNames()));
	    names.addAll(this.formParameters.keySet());
	    return Collections.enumeration(names);
	}

	@Override
	public String[] getParameterValues(String name) {
	    String[] queryStringValues = super.getParameterValues(name);
	    List<String> formValues = this.formParameters.get(name);
	    if (formValues == null) {
		return queryStringValues;
	    } else if (queryStringValues == null) {
		return formValues.toArray(new String[formValues.size()]);
	    } else {
		List<String> result = new ArrayList<String>(queryStringValues.length + formValues.size());
		result.addAll(Arrays.asList(queryStringValues));
		result.addAll(formValues);
		return result.toArray(new String[result.size()]);
	    }
	}
    }
}
