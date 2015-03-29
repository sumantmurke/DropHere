package com.AirBox.Domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class UploadObject {
	
	private String fileName;
	private long size;
	private Date dateCreated;
	private String username;
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param l the size to set
	 */
	public void setSize(long l) {
		this.size = l;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param date the dateCreated to set
	 */
	public void setDateCreated(java.util.Date date) {
		this.dateCreated = (Date) date;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
