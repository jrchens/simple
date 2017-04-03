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

import me.simple.entity.SysBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class BaseServiceTest {
    @Autowired
    private SysBaseService baseService;
    private final static Logger logger = LoggerFactory.getLogger(BaseServiceTest.class);

    // "row_id","name","price","pub_date","deleted","disabled","row_status","cruser","crtime","mduser","mdtime"
    @Test
    public void save() throws Exception {
	SysBase base = new SysBase();
	long now = System.currentTimeMillis();
	base.setName(UUID.randomUUID().toString().substring(0, 12));
	base.setPrice(45.67d);
	base.setPub_date(new Date(now));
	base.setDisabled(false);
	base.setDeleted(false);
	base.setRow_status(1);
	base.setCruser("admin");
	base.setCrtime(new Timestamp(now));

	baseService.save(base);

	base.setName(UUID.randomUUID().toString().substring(0, 12));
	baseService.save(base);
    }

    @Test
    public void delete() throws Exception {
	long now = System.currentTimeMillis();

	SysBase base = new SysBase(1l);
	base.setMduser("admin");
	base.setMdtime(new Timestamp(now));

	baseService.delete(base);
    }

    @Test
    public void update() throws Exception {
	SysBase base = new SysBase(2l);
	long now = System.currentTimeMillis();
	base.setName(UUID.randomUUID().toString().substring(0, 12));
	base.setPrice(55.66d);
	base.setPub_date(new Date(now));
	base.setDisabled(true);
	base.setRow_status(2);
	base.setMduser("admin");
	base.setMdtime(new Timestamp(now));
	baseService.update(base);
    }

    @Test
    public void get() throws Exception {
	Long rowId = 2l;
	SysBase base = baseService.get(rowId);
	Assert.isTrue(base.getRow_status() == 2);
    }

    @Test
    public void query() throws Exception {
	SysBase base = new SysBase();

	base.setName("9");

	base.setSrt("name");
	base.setOrd("desc");
	base.setPage(1);
	base.setRows(10);

	List<SysBase> rows = baseService.query(base);

	Assert.isTrue(!rows.isEmpty());
	Assert.isTrue(base.getTotal() == 2);
    }

}
