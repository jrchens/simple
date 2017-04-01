package me.simple.backup.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.simple.entity.Base;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class BaseServiceTest {
    @Autowired
    private BaseService baseService;
    private final static Logger logger = LoggerFactory.getLogger(BaseServiceTest.class);

    // "row_id","name","price","pub_date","deleted","disabled","row_status","cruser","crtime","mduser","mdtime"
    @Test
    public void save() throws Exception {
	Base base = new Base();
	long now = System.currentTimeMillis();
	base.setName(UUID.randomUUID().toString().substring(0, 12));
	base.setPrice(45.67d);
	base.setPubDate(new Date(now));
	base.setDisabled(false);
	base.setDeleted(false);
	base.setRowStatus(1);
	base.setCruser("admin");
	base.setCrtime(new Timestamp(now));

	baseService.save(base);
    }

    @Test
    public void delete() throws Exception {
	long now = System.currentTimeMillis();
	
	Base base = new Base(1l);
	base.setMduser("admin");
	base.setMdtime(new Timestamp(now));
	
	baseService.delete(base);
    }

    @Test
    public void update() throws Exception {
	Base base = new Base(2l);
	long now = System.currentTimeMillis();
	base.setName(UUID.randomUUID().toString().substring(0, 12));
	base.setPrice(55.66d);
	base.setPubDate(new Date(now));
	base.setDisabled(true);
	base.setRowStatus(2);
	base.setMduser("admin");
	base.setMdtime(new Timestamp(now));
	baseService.update(base);
    }

    @Test
    public void get() throws Exception {
	Long rowId = 2l;
	Base base = baseService.get(rowId);
	Assert.isTrue(base.getRowStatus() == 2);
    }

    @Test
    public void query() throws Exception {
	Base base = new Base();
	base.setName("3");
	
	base.setSrt("name");
	base.setOrd("desc");
	base.setPage(1);
	base.setRows(10);
	
	List<Base> rows = baseService.query(base);
	
	Assert.isTrue(!rows.isEmpty());
	Assert.isTrue(base.getTotal() == 1);
    }

}
