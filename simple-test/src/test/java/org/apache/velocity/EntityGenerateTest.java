package org.apache.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class EntityGenerateTest {

    private JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(EntityGenerateTest.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private String getBeaname(String tablename) {
	String[] tablenames = tablename.split("_");
	StringBuffer buffer = new StringBuffer();
	for (String p : tablenames) {
	    if (StringUtils.hasText(p)) {
		if (p.length() == 1) {
		    buffer.append(p.substring(0, 1).toUpperCase());
		} else {
		    buffer.append(p.substring(0, 1).toUpperCase()).append(p.substring(1));
		}
	    }
	}
	return buffer.toString();
    }

    @Test
    public void testGenerateBaseCode() throws Exception {
	final String pkg = "me.simple.backup";
	final String tablename = "sys_base";
	final List<String> queryFieldsDefine = Lists.newArrayList("name");
	final List<String> updateFieldsDefine = Lists.newArrayList("name", "price", "pub_date", "disabled",
		"row_status");

	StringBuffer updateFields = new StringBuffer();
	for (String field : updateFieldsDefine) {
	    updateFields.append(field).append(" = :").append(field).append(" , ");
	}

	final List<BeanField> list = Lists.newArrayList();
	final List<String> pks = Lists.newArrayList();
	final List<BeanField> queryFields = Lists.newArrayList();

	Connection con = jdbcTemplate.getDataSource().getConnection();
	DatabaseMetaData dbma = con.getMetaData();
	ResultSet rs = dbma.getPrimaryKeys(null, null, tablename);
	while (rs.next()) {
	    pks.add(rs.getString("COLUMN_NAME"));
	}
	JdbcUtils.closeResultSet(rs);
	JdbcUtils.closeConnection(con);

	jdbcTemplate.query("select * from " + tablename + " where 1 = 2", new ResultSetExtractor<List<BeanField>>() {
	    @Override
	    public List<BeanField> extractData(ResultSet rs) throws SQLException, DataAccessException {

		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
		    // Map<String, Object> field = Maps.newLinkedHashMap();
		    String columnName = rsmd.getColumnName(i);
		    String columnType = rsmd.getColumnClassName(i);
		    columnType = columnType.substring(columnType.lastIndexOf('.') + 1);
		    columnType = "BigInteger".equals(columnType) ? "Long" : columnType;
		    Boolean isPrimaryKey = pks.contains(columnName);

		    BeanField bf = new BeanField(columnName, columnType, isPrimaryKey);

		    if (queryFieldsDefine.contains(columnName)) {
			queryFields.add(bf);
		    }
		    // if (pks.contains(columnName)) {
		    // field.put("isPrimaryKey", true);
		    // } else {
		    // field.put("isPrimaryKey", false);
		    // }
		    // field.put("columnLabel", rsmd.getColumnLabel(i));
		    // field.put("columnName", columnName);
		    // field.put("columnType", rsmd.getColumnType(i)); //
		    // java.sql.Types
		    // field.put("columnTypeName", rsmd.getColumnTypeName(i));
		    // field.put("columnClassName", rsmd.getColumnClassName(i));
		    // field.put("precision", rsmd.getPrecision(i));
		    // field.put("scale", rsmd.getScale(i));

		    list.add(bf);
		}
		return list;
	    }

	});

	for (BeanField field : list) {
	    logger.info("name:{}, type:{}, pk:{}", field.getName(), field.getType(), field.getIsPrimaryKey());
	}

	VelocityEngine ve = new VelocityEngine();

	ve.setProperty("resource.loader", "class");
	ve.setProperty("class.resource.loader.class",
		"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

	ve.init();

	String beaname = getBeaname(tablename);
	
	VelocityContext context = new VelocityContext();
	context.put("package", pkg);
	context.put("beaname", beaname);
	context.put("tablename", tablename);
	context.put("PK", list.get(0));
	context.put("fields", list);
	context.put("queryFields", queryFields);
	context.put("updateFields", updateFields);

	StringWriter writer = new StringWriter();
	Template t = ve.getTemplate("entity.ftl");
	t.merge(context, writer);
	File file = new File("/Users/chensheng/Desktop/me/simple/entity", beaname + ".java");
	Files.createParentDirs(file);
	Writer out = new FileWriter(file);
	FileCopyUtils.copy(writer.toString(), out);

	writer = new StringWriter();
	t = ve.getTemplate("service.ftl");
	t.merge(context, writer);
	file = new File("/Users/chensheng/Desktop/" + pkg.replaceAll("\\.", "/"), beaname + "Service.java");
	Files.createParentDirs(file);
	out = new FileWriter(file);
	FileCopyUtils.copy(writer.toString(), out);

	writer = new StringWriter();
	t = ve.getTemplate("service-impl.ftl");
	t.merge(context, writer);
	file = new File("/Users/chensheng/Desktop/" + pkg.replaceAll("\\.", "/") + "/impl",
		beaname + "ServiceImpl.java");
	Files.createParentDirs(file);
	out = new FileWriter(file);
	FileCopyUtils.copy(writer.toString(), out);

    }
}
