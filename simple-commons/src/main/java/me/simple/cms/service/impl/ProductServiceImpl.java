package me.simple.cms.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.cms.service.ProductService;
import me.simple.commons.entity.Pagination;
import me.simple.commons.entity.Product;
import me.simple.commons.entity.ProductRowMapper;
import me.simple.util.Constants;
import me.simple.util.SqlUtil;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product saveProduct(Product product) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.saveProduct");
//	long productId = product.getProductId();
//	if (checkProductExists(productId)) {
//	    throw new DuplicateKeyException(String.valueOf(productId));
//	}
	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	String productId = SqlUtil.uuid();
	
	product.setOwner(owner);
	product.setCrtime(now);
	product.setProductId(productId);
	
	jdbcTemplate.update("insert into cms_product(product_id,product_name,intro,spec,rich_content,deleted,owner,cruser,crtime) values(?,?,?,?,?,?,?,?,?)", 
		productId,
		product.getProductName(),
		product.getIntro(), 
		product.getSpec(),
		product.getRichContent(),
		false,
		owner,
		owner,
		now
		);

	List<String> tags = product.getTags();
	List<Object[]> batchArgs = Lists.newArrayList();
	for (String tagname : tags) {
	    batchArgs.add(new Object[]{productId,tagname});
	}
	jdbcTemplate.batchUpdate("insert into cms_product_tag(product_id,tagname) values(?,?)", batchArgs);
	
	return product;
    }

    @Override
    public int removeProduct(String productId) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.removeProduct");
	if (!checkProductExists(productId)) {
	    throw new EmptyResultDataAccessException(String.valueOf(productId), 1);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	
	int affected = jdbcTemplate.update("delete from cms_product_tag where product_id = ?", productId);
	affected = jdbcTemplate.update("update cms_product set deleted = ?,mduser = ?,mdtime = ? where product_id = ?", 
		true,
		owner,
		now,
		productId);
	return affected;
    }

    @Override
    public int updateProduct(Product product) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.updateProduct");
	String productId = product.getProductId();
	if (!checkProductExists(productId)) {
	    throw new EmptyResultDataAccessException(String.valueOf(productId), 1);
	}

	String owner = (String) SecurityUtils.getSubject().getPrincipal();
	Timestamp now = new Timestamp(System.currentTimeMillis());
	
	jdbcTemplate.update("delete from cms_product_tag where product_id = ?", productId);
	List<String> tags = product.getTags();
	List<Object[]> batchArgs = Lists.newArrayList();
	for (String tagname : tags) {
	    batchArgs.add(new Object[]{productId,tagname});
	}
	jdbcTemplate.batchUpdate("insert into cms_product_tag(product_id,tagname) values(?,?)", batchArgs);
	
	return jdbcTemplate.update("update cms_product set product_name = ? ,intro = ? ,spec = ? ,rich_content = ?, mduser = ?,mdtime = ? where product_id = ?", 
		product.getProductName(),
		product.getIntro(), 
		product.getSpec(),
		product.getRichContent(),
		owner,
		now,
		productId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkProductExists(String productId) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.existsProduct");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select count(1) from cms_product where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	querySql.append("and product_id = ? ");
	args.add(productId);
	
	int count = jdbcTemplate.queryForObject(querySql.toString(), Integer.class,
		args.toArray());

	return count > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(String productId) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.getProduct");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_product where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	querySql.append("and product_id = ? ");
	args.add(productId);
	
	List<Product> productList = jdbcTemplate.query(querySql.toString(),
		args.toArray(), new ProductRowMapper());

	if (productList.isEmpty()) {
	    throw new EmptyResultDataAccessException(String.valueOf(productId), 1);
	}
	return productList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> queryProduct(Product product, Pagination<Product> pagination) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.queryProduct");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select * from cms_product where deleted = 0 ");

	List<Object> args = Lists.newArrayList();
	Subject subject = SecurityUtils.getSubject();
	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
	    querySql.append("and owner = ? ");
	    args.add(subject.getPrincipal());
	}
	
	String productName = product.getProductName();
	if (StringUtils.hasText(productName)) {
	    querySql.append("and product_name like ? ");
	    args.add(SqlUtil.like(productName));
	}

	int total = jdbcTemplate.queryForObject(SqlUtil.countSql(querySql.toString()), Integer.class, args.toArray());
	pagination.setTotalRecord(total);

	String order = pagination.getOrder();
	String sort = pagination.getSort();
	if (StringUtils.hasText(order)) {
	    querySql.append("order by ").append(SqlUtil.getOrder(order)).append(" ");
	    if (StringUtils.hasText(sort)) {
		querySql.append(SqlUtil.getOrder(sort)).append(" ");
	    }
	}
	querySql.append("limit ?,?");
	args.add(pagination.getOffset());
	args.add(pagination.getPageSize());

	List<Product> records = jdbcTemplate.query(querySql.toString(), new ProductRowMapper(), args.toArray());
	pagination.setRecords(records);

	return records;
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<String> queryProductags(String productId) {
	logger.info("me.simple.admin.service.impl.ProductServiceImpl.queryProductags");
	StringBuffer querySql = new StringBuffer();
	querySql.append("select tagname from cms_product_tag where 1 = 1 ");

	List<Object> args = Lists.newArrayList();
//	Subject subject = SecurityUtils.getSubject();
//	if(!subject.hasRole(Constants.ROLE_SYS_ADMIN)){
//	    querySql.append("and owner = ? ");
//	    args.add(subject.getPrincipal());
//	}
	querySql.append("and product_id = ? ");
	args.add(productId);

	return jdbcTemplate.queryForList(querySql.toString(), String.class, args.toArray());
    }

}
