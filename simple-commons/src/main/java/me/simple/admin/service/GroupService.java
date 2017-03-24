package me.simple.admin.service;

import java.util.List;

import me.simple.commons.entity.Group;
import me.simple.commons.entity.Pagination;

public interface GroupService {

    Group saveGroup(Group group);

    int removeGroup(String groupname);

    /**
     * just update group viewname
     * 
     * @param group
     * @return
     */
    int updateGroup(Group group);

    boolean checkGroupExists(String groupname);

    Group getGroup(String groupname);

    /**
     * query by viewname or groupname
     * 
     * @param group
     * @param pagination
     * @return
     */
    List<Group> queryGroup(Group group, Pagination<Group> pagination);
    

    List<Group> queryAll();

}