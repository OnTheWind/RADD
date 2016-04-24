package com.uws.radd.data;

public class Drink {
	private String id;
	private String name;
	
	public Drink(String id, String name){
		this.id=id;
		this.name=name;
	}
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
}
