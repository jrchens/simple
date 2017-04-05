package me.simple.generate.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import me.simple.entity.Column;
import me.simple.entity.ConInfo;
import me.simple.entity.Table;

@Controller
public class IndexController {

    @RequestMapping(value = { "", "login" }, method = RequestMethod.GET)
    public String login(Model model) {
	model.addAttribute(new ConInfo("localhost", "127.0.0.1", "simple", "develop", "develop"));
	return "login";
    }

    @RequestMapping(value = { "login" }, method = RequestMethod.POST)
    public String index(ConInfo conInfo, Model model) throws Exception {
	MysqlDataSource ds = new MysqlDataSource();
	ds.setUser(conInfo.getCon_username());
	ds.setPassword(conInfo.getCon_password());
	ds.setDatabaseName(conInfo.getCon_db());
	ds.setPort(conInfo.getCon_port());

	QueryRunner runner = new QueryRunner(ds);
	Connection conn = runner.getDataSource().getConnection();
	ResultSet rs = conn.getMetaData().getTables("", "", "%", null);
	List<Table> tables = Lists.newArrayList();
	while(rs.next()){
	    tables.add(new Table(rs.getString("TABLE_NAME")));
	}
	DbUtils.closeQuietly(rs);
	
	
	rs = conn.getMetaData().getColumns("", "", tables.get(0).getTab_name(), "");
	List<Column> columns = Lists.newArrayList();
	while(rs.next()){
	    columns.add(new Column(rs.getString("COLUMN_NAME"),rs.getString("TYPE_NAME"),rs.getString("DATA_TYPE")));
	}
	DbUtils.closeQuietly(rs);
	
	DbUtils.closeQuietly(conn);
	
	tables.get(0).setColumns(columns);
	
	model.addAttribute("tables", tables);
	
	return "index";
    }
}
