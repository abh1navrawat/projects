package com.ar.demo.controller;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ar.demo.models.Product;
import com.ar.demo.models.ProductDto;
import com.ar.demo.services.ProductsRepo;

import jakarta.validation.Valid;

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
	
	@GetMapping("/create")
	public String showCreatePage(Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto",productDto);
		return "products/CreateProduct";
}
	
	@PostMapping("/create")
	public String createProduct
	(@Valid @ModelAttribute ProductDto productDto,
			BindingResult result) {
		
		
		if (productDto.getImg_file().isEmpty()) {
			result.addError(new FieldError("productDto", "img_file", "The image file is required"));
		}
		
		if (result.hasErrors()) {
			return "products/CreateProduct";
		}
		
		 
		//Save image file
		MultipartFile image= productDto.getImg_file();
		Date createdAt = new Date();
		String storageFileName =  createdAt.getTime() +"_"+ image.getOriginalFilename();
		
		  try {
			  String uploadDir = "public/imgs/";
			  Path uploadPath = Paths.get(uploadDir);
			  
		  if (!Files.exists(uploadPath)) {
			  Files.createDirectories(uploadPath);
		  }
		  
		  try (InputStream inputStream = image.getInputStream()){
			  Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
					   StandardCopyOption.REPLACE_EXISTING);
		  }
		  
		  }catch(Exception ex) {
			  System.out.println("Exception: " + ex.getMessage());
		  }
		 
		  
		Product product = new Product();
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCategory(productDto.getCategory());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		product.setCreatedAt(createdAt);
		product.setImg_file_name(storageFileName);	
		
		repo.save(product);
		
		
		return "redirect:/products";
	}
	
	@GetMapping("/edit")
	public String showEditPage(
		Model model,
		@RequestParam int id
		) {
		
		try {
			Product product = repo.findById(id).get();
			model.addAttribute("product",product);
			
			
			ProductDto productDto = new ProductDto();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());
			productDto.setDescription(product.getDescription());
			
			model.addAttribute("productDto",productDto);
		}
		catch(Exception ex) {
			System.out.println("Exception : " + ex.getMessage() );
			return "redirect:/products";
		}
		
		return "products/EditProduct";
	}
	
	@PostMapping("/edit")
	public String updateProduct(
		Model model,
		@RequestParam int id,
		@Valid @ModelAttribute ProductDto productDto,
		BindingResult result
	) {
		
	try {
		Product product = repo.findById(id).get();
		model.addAttribute("product", product);
	
		if (result.hasErrors()){
			return "product/EditProduct";
		}
			
		if (!productDto.getImg_file().isEmpty()) {
			//delete old image
			String uploadDir = "public/imgs/";
			Path oldImagePath = Paths.get(uploadDir + product.getImg_file_name());
			
			try {
				Files.delete(oldImagePath);
			}
			catch(Exception ex) {
				System.out.println("Exception : " + ex.getMessage() );		
			}
			
			//save new image file	
			MultipartFile image = productDto.getImg_file();
			Date createdAt = new Date();
			String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
			
			try (InputStream inputstream = image.getInputStream()){
				Files.copy(inputstream, Paths.get(uploadDir + storageFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}
			
			
			product.setImg_file_name(storageFileName);
			}
		
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());
			
			repo.save(product);
		}
	
		catch(Exception ex) {
			System.out.println("Exception : " + ex.getMessage() );
		}
			return "redirect:/products";
	}
	
	@GetMapping("/delete")
	public String DeleteName(@RequestParam int id) {
		try {
			Product product = repo.findById(id).get();
			
			String uploadDir = "public/imgs/";
			Path oldImagePath = Paths.get(uploadDir + product.getImg_file_name());
			
			try {
				Files.delete(oldImagePath);
			}
			catch(Exception ex) {
				System.out.println("Exception : " + ex.getMessage() );		
			}
			
			repo.deleteById(id);
		} catch (Exception ex) {
			System.out.println("the error is "+ex.getMessage());
		
		}

		return "redirect:/products";
	}
	

	
}
