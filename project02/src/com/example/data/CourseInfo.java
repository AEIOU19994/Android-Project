package com.example.data;

public class CourseInfo {
	private int id;
	private String name;
	
	public CourseInfo(){
		
	}
	
	public CourseInfo(int id,String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
