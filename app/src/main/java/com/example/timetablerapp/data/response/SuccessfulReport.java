package com.example.timetablerapp.data.response;

import com.google.gson.annotations.SerializedName;

public class SuccessfulReport {
	@SerializedName("message")
	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
