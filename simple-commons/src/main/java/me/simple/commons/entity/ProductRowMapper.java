package me.simple.commons.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet res, int idx) throws SQLException {
	Product obj = new Product();
	
	obj.setProductId(res.getString("product_id"));
	obj.setProductName(res.getString("product_name"));
	obj.setIntro(res.getString("intro"));
	obj.setSpec(res.getString("spec"));
	obj.setRichContent(res.getString("rich_content"));
	
	obj.setOwner(res.getString("owner"));
	obj.setCruser(res.getString("cruser"));
	obj.setCrtime(res.getTimestamp("crtime"));
	obj.setMduser(res.getString("mduser"));
	obj.setMdtime(res.getTimestamp("mdtime"));
	return obj;
    }
    
}
