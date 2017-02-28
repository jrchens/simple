package me.simple.admin.service;

import java.util.List;

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.Role;

public interface RoleService {

    Role saveRole(Role role);

    int removeRole(String rolename);

    /**
     * just update role viewname
     * 
     * @param role
     * @return
     */
    int updateRole(Role role);

    boolean checkRoleExists(String rolename);

    Role getRole(String rolename);

    /**
     * query by viewname or rolename
     * 
     * @param role
     * @param pagination
     * @return
     */
    List<Role> queryRole(Role role, Pagination<Role> pagination);

    List<Role> queryAll();
}