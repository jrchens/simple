package me.simple.cms.service;

import java.util.List;

import me.simple.common.entity.Pagination;
import me.simple.common.entity.Product;

public interface ProductService {

    Product saveProduct(Product product);

    int removeProduct(String productId);

    /**
     * just update product viewname
     * 
     * @param product
     * @return
     */
    int updateProduct(Product product);

    boolean checkProductExists(String productId);

    Product getProduct(String productId);

    /**
     * query by productname
     * 
     * @param product
     * @param pagination
     * @return
     */
    List<Product> queryProduct(Product product, Pagination<Product> pagination);
    
    public List<String> queryProductags(String productId);
}