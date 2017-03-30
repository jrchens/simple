package me.simple.backup;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import me.simple.backup.service.BackupService;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(BackupService.class);
	private static ApplicationContext context;
	
	public static void main(String[] args) {
		try {
			context =new ClassPathXmlApplicationContext("applicationContext.xml");
			BackupService backupService = context.getBean(BackupService.class);
			if(args.length > 0){
				if(args[0].equals("save")){
					backupService.saveBackup();
				}else if(args[0].equals("get")){
					Map<String,Object> result = backupService.getBackup();
					logger.info("result: {}",result);
				}else if(args[0].equals("query")){
					List<Map<String,Object>> result = backupService.queryBackup();
					logger.info("result: {}",result);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("error",e);
		}
	}

}
