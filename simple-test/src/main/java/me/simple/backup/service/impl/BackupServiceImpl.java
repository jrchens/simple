package me.simple.backup.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.simple.backup.service.BackupService;
import me.simple.entity.Backup;
import me.simple.util.Page;
import me.simple.util.Pageable;

@Service
@Transactional
public class BackupServiceImpl implements BackupService {
	
	private static final Logger logger = LoggerFactory.getLogger(BackupService.class);
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;
	private String simpleJdbcInsertable = "sys_user";
	private List<String> simpleJdbcInsertColumnNames = Lists.newArrayList("username","viewname","password","deleted","cruser","crtime");
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
		this.simpleJdbcInsert.setTableName(simpleJdbcInsertable);
		this.simpleJdbcInsert.setColumnNames(simpleJdbcInsertColumnNames);
	}

	@Override
	public int saveBackup() {
		logger.info("me.simple.backup.service.impl.BackupService.saveBackup");
		int aff = 0;
		try {
			Map<String,Object> args = Maps.newHashMap();
			args.put("username","root");
			args.put("viewname","Root");
			args.put("password","********");
			args.put("deleted",false);
			args.put("cruser","root");
			args.put("crtime",new Timestamp(System.currentTimeMillis()));
			
			aff = simpleJdbcInsert.execute(args);
		} catch (Exception e) {
			throw new RuntimeException("save backup error",e);
		}
		return aff;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> getBackup() {
		logger.info("me.simple.backup.service.impl.BackupService.getBackup");
		Map<String, Object> result = null;
		try {
			result = jdbcTemplate.queryForMap("show variables where variable_name = ?","character_set_client");
		} catch (Exception e) {
			throw new RuntimeException("get backup error",e);
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> queryBackup() {
		logger.info("me.simple.backup.service.impl.BackupService.queryBackup");
		List<Map<String, Object>> result = null;
		try {
			result = jdbcTemplate.queryForList("show variables like ?","character%");
		} catch (Exception e) {
			throw new RuntimeException("query backup error",e);
		}
		return result;
	}

	@Override
	public int save(Backup backup) {
		logger.info("me.simple.backup.service.impl.BackupService.save(Backup backup)");
		int aff = 0;
		try {
			aff = simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(backup));
		} catch (Exception e) {
			throw new RuntimeException("save backup error",e);
		}
		return aff;
	}

	@Override
	public int delete(String backupId) {
	    return 0;
	}

	@Override
	public int update(Backup backup) {
	    return 0;
	}

	@Override
	public Backup get(String backupId) {
	    return null;
	}

	@Override
	public Page<Backup> query(Backup backup, Pageable pageable) {
	    return null;
	}
}
