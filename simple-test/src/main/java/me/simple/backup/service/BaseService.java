package me.simple.backup.service;

import java.util.List;

import me.simple.entity.Base;

public interface BaseService {
    public int save(Base base);

    public int delete(Base base);

    public int update(Base base);

    public Base get(Long rowId);

    public List<Base> query(Base base);
}
