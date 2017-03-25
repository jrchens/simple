package me.simple.admin.service;

import java.util.List;

import me.simple.common.entity.Menu;

public interface MenuService {

    String getMenuUrl(String menuname);
    
    List<Menu> queryAll();

}