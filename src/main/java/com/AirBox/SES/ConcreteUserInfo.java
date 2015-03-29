package com.AirBox.SES;
import java.util.ArrayList;
import java.util.Random;


public class ConcreteUserInfo implements UserInformation {
	
	String receiversEmail = null;
	String sendersEmail = null;
	private ArrayList<String> list = new ArrayList<String>();
	Random r = new Random();
	
	public ConcreteUserInfo() {
		this.list.add("sumantmurke@gmail.com");
	//	this.list.add("rohietkothari@gmail.com");
	//	this.list.add("chetan.burande7@gmail.com");
	//	this.list.add("bhagyashreen@gmail.com");
	}
	
	public ConcreteUserInfo(String receiversEmail) {
		super();
		setReEmail(receiversEmail);
		
	}
	
	public ConcreteUserInfo(String sendersEmail, String receiversEmail){
		super();
		setSeEmail(sendersEmail);
		setReEmail(receiversEmail);
	}
	
	public void setReEmail(){
		this.receiversEmail = randomizer();
	}

	public void setReEmail(String receiversEmail){
		this.receiversEmail = receiversEmail;
	}
	
	public String getReEmail(){
		return receiversEmail;
	}
	
	public void setSeEmail(String sendersEmail){
		this.sendersEmail = sendersEmail;
	}
	
	public String getSeEmail(){
		return sendersEmail;
	}
	
	public String randomizer(){
		String tempEmail = list.get(r.nextInt(list.size()));
		
		return tempEmail;
		
	}
	
}
