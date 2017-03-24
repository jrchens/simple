package me.simple.admin.service;

import java.util.List;

import me.simple.commons.entity.Menu;

public interface MenuService {

    String getMenuUrl(String menuname);
    
    List<Menu> queryAll();

}