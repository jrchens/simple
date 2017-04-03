package me.simple.backup.service;

import java.util.List;

import me.simple.entity.SysBase;

public interface SysBaseService {
    public int save(SysBase SysBase);

    public int delete(SysBase SysBase);

    public int update(SysBase SysBase);

    public SysBase get(Long row_id);

    public List<SysBase> query(SysBase SysBase);
}
