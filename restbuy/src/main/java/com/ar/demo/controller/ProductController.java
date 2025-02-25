package com.ar.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ar.demo.models.Product;
import com.ar.demo.services.ProductsRepo;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductsRepo repo;
	
	@GetMapping({"","/"})
	public String showProductList(Model model) {
		List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("products",products);
		return "products/index";
	}
}
