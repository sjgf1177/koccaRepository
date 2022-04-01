package com.credu.email;



public class EmailData {

	int seq;
	String userId;
	String className;
	String requestDate;
	
	public EmailData() {
	
	seq = 0;
	userId = "";
	className = "";
	requestDate = "";
	
		
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClassname() {
		return className;
	}
	public void setClassname(String classname) {
		this.className = classname;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	
	
}
