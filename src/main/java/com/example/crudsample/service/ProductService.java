package com.example.crudsample.service;


import com.example.crudsample.dao.CategoryDao;
import com.example.crudsample.dao.ProductDao;
import com.example.crudsample.ds.Category;
import com.example.crudsample.ds.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> findAllCategory() {
        return categoryDao.findAll();
    }

    public void saveCategory(Category category) {
        categoryDao.save(category);
    }

    public List<Product> findAllProduct() {
        return productDao.findAll();
    }

    @Transactional
    public void saveProduct(Product product) {
        Category category = categoryDao.findById(product.getCategory().getId()).get();
        category.addProduct(product);
        productDao.save(product);
    }

    public Product findProductById(int id) {
        return productDao.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    //explicit Update
    public void updateProduct(Product product) {
        productDao.saveAndFlush(product);
    }

    //implicit Update
    @Transactional
    public void updateProductV2(int id, Product product) {
        Product existingProduct = findProductById(id);
        existingProduct.setCategory(product.getCategory());
        existingProduct.setLastUpdate(product.getLastUpdate());
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
    }

    public void deleteProduct(int id) {
        productDao.deleteById(id);
    }

    public void deleteCategory(int id) {
        categoryDao.deleteById(id);
    }
}
