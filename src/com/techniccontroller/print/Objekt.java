package com.techniccontroller.print;

import java.util.ArrayList;

public class Objekt {
	private String name;
	private ArrayList<int[]> code;
	
	public Objekt(){
		
	}
	
	public Objekt(String myName, ArrayList<int[]> myCode){
		name = myName;
		code = myCode;
	}

	public String getName() {
		return name;
	}

	public ArrayList<int[]> getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(ArrayList<int[]> code) {
		this.code = code;
	}
}
