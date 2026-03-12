package com.cognizant.entities.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	
	public Department(long l, String name, String desc) {
		// TODO Auto-generated constructor stub
		this.id=l;
		this.name=name;
		this.description=desc;
	}
	public Department() {
		
	}
	public Long getDepartmentId() {
		return id;
	}
	public void setDepartmentId(Long departmentId) {
		this.id = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	
}
