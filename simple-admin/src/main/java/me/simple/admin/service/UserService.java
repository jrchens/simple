package me.simple.admin.service;

import java.util.List;

import me.simple.admin.entity.Pagination;
import me.simple.admin.entity.User;

public interface UserService {

    User saveUser(User user);

    int removeUser(String username);

    /**
     * just update user viewname
     * 
     * @param user
     * @return
     */
    int updateUser(User user);

    boolean checkUserExists(String username);

    User getUser(String username);

    /**
     * query by viewname or username
     * 
     * @param user
     * @param pagination
     * @return
     */
    List<User> queryUser(User user, Pagination<User> pagination);
    
    List<String> queryUserRoles(String username);
    

    String resetUserPassword(String username);
    
    
    List<User> queryAll();

}