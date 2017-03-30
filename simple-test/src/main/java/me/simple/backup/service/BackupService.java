package me.simple.backup.service;

import java.util.List;
import java.util.Map;

public interface BackupService {
	public int saveBackup();
	public Map<String,Object> getBackup();
	public List<Map<String,Object>> queryBackup();
}
