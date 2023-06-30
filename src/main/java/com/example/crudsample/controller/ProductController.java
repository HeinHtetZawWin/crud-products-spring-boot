package com.example.crudsample.controller;

import com.example.crudsample.dao.CategoryDao;
import com.example.crudsample.dao.ProductDao;
import com.example.crudsample.ds.Category;
import com.example.crudsample.ds.Product;
import com.example.crudsample.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/category")
    public String categoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-form";
    }

    @PostMapping("/category")
    public String saveCategory(Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category-form";
        }

        productService.saveCategory(category);

        return "redirect:/list-category";
    }

    @GetMapping("/list-category")
    public String listCategory(Model model) {
        model.addAttribute("categories", productService.findAllCategory());
        return "list-category";
    }

    @GetMapping("/category/delete")
    public String deleteCategory(int id) {
        productService.deleteCategory(id);
        return "redirect:/list-category";

    }

    @GetMapping("/product")
    public ModelAndView productForm(@ModelAttribute("categories") List<Category> categories) {
        ModelAndView mv = new ModelAndView("product-form",
                "product", new Product());
        mv.addObject("listcategory", categories);
        return mv;
    }

    @PostMapping("/product")
    public String saveProduct(Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "product-form";
        }

        productService.saveProduct(product);
        return "redirect:/list-product";
    }

    @GetMapping("/list-product")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAllProduct());
        return "list-product";
    }

    @GetMapping("/product/delete")
    public String deleteProduct(int id) {
        productService.deleteProduct(id);
        return "redirect:/list-product";

    }

    @ModelAttribute("categories")
    public List<Category> listCategory() {
        return productService.findAllCategory();
    }

    @GetMapping("/product/update")
    public String updateProduct(int id, Model model) {
        this.pId = id;
        model.addAttribute("product", productService.findProductById(id));
        return "product-update";
    }

    private int pId;

    //explicit update
    @PostMapping("/product/update")
    public String saveUpdateProduct(Product product) {
        product.setId(pId);
        productService.updateProduct(product);
        /*productService.updateProductV2(pId, product); ==> implicit update
        * */
        return "redirect:/list-product";
    }


}
