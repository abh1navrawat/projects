package com.ar.demo.models;

import java.util.Date;
import jakarta.persistence.*;

@Entity // telling JPA that the instance of this class are mapped to rows of the corresponding database table  
@Table(name = "products")
public class Product {
	@Id  //set the primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY ) //to make primary key unique 
	private int id;
	
	private String name;
	private String brand;
	private String category;
	private double price;
	
	private Date createdAt;
	@Column(columnDefinition = "TEXT") //make that column persistent
	private String description;
	private String img_file_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImg_file_name() {
		return img_file_name;
	}
	public void setImg_file_name(String img_file_name) {
		this.img_file_name = img_file_name;
	}
}

