package me.simple.backup.service;

import java.util.List;
import java.util.Map;

import me.simple.entity.Backup;
import me.simple.util.Page;
import me.simple.util.Pageable;

public interface BackupService {
    public int save(Backup backup);

    public int delete(String backupId);

    public int update(Backup backup);

    public Backup get(String backupId);

    public Page<Backup> query(Backup backup, Pageable pageable);

    
    
    public int saveBackup();

    public Map<String, Object> getBackup();

    public List<Map<String, Object>> queryBackup();
}
