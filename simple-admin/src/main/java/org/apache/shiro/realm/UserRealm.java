package org.apache.shiro.realm;

import java.util.List;

import javax.sql.DataSource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class UserRealm extends AuthorizingRealm {

    private static final String userQuery = "select password from sys_user where deleted = 0 and username = ?";
    private static final String userRolesQuery = "select rolename from sys_user_role where username = ?";
    private static final String rolePermissionsQuery = "select permname from sys_role_permission where rolename = ?";

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	String username = (String) principals.getPrimaryPrincipal();
	List<String> userRoleList = jdbcTemplate.queryForList(userRolesQuery, String.class, username);
	List<String> rolePermissionList = Lists.newArrayList();
	for (String rolename : userRoleList) {
	    rolePermissionList.addAll(jdbcTemplate.queryForList(rolePermissionsQuery, String.class, rolename));
	}
	SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
	simpleAuthorizationInfo.setRoles(Sets.newConcurrentHashSet(userRoleList));
	simpleAuthorizationInfo.setStringPermissions(Sets.newConcurrentHashSet(rolePermissionList));
	return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	UsernamePasswordToken usernamePasswordToken = null;
	if (token instanceof UsernamePasswordToken) {
	    usernamePasswordToken = (UsernamePasswordToken) token;
	}else{
	    throw new UnsupportedTokenException();
	}
	String username = usernamePasswordToken.getUsername();
	String password = String.valueOf(usernamePasswordToken.getPassword());
	List<String> list = jdbcTemplate.queryForList(userQuery, String.class, username);
	if(list.isEmpty()){
	    throw new UnknownAccountException();
	}
	if(!password.equals(list.get(0))){
	    throw new IncorrectCredentialsException();
	}
	// ConcurrentAccessException
	// DisabledAccountException
	// LockedAccountException
	return new SimpleAuthenticationInfo(token.getPrincipal(),token.getCredentials(),getName());
    }

    @Override
    public String getName() {
	return UserRealm.class.getName();
    }
}
