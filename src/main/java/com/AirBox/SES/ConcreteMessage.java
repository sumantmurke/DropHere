package com.AirBox.SES;

public class ConcreteMessage implements MessageBodyFormat{

	String msgBody;
	String msgSubject;
	
	
	
	public ConcreteMessage() {
		super();
		
		this.msgBody = "Thank You for subscribing with us. Hope we prove usefull to you and your organisation";
		this.msgSubject = "AirBox subscription confirmation mail";
	}

	public ConcreteMessage(String msgBody, String msgSubject) {
		super();
		this.msgBody = msgBody;
		this.msgSubject = msgSubject;
	}

	public String getMsgBody() {
		return msgBody;
	}
	
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	
	
	public String getMsgSubject() {
		return msgSubject;
	}
	 
	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
}
