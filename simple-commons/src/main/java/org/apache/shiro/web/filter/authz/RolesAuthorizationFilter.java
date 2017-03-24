package org.apache.shiro.web.filter.authz;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;

public class RolesAuthorizationFilter extends AuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }
        boolean accessAllowed = false;
        for (String role : rolesArray) {
            if(subject.hasRole(role)){
        	accessAllowed = true;
        	break;
            }
	}
        
        return accessAllowed;

//        Set<String> roles = CollectionUtils.asSet(rolesArray);
//        return subject.hasAllRoles(roles);
    }

}
