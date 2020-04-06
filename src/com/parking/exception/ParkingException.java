package com.parking.exception;

public class ParkingException extends Exception {

	private static final long serialVersionUID = 1L;
	public ParkingException() {
		// TODO Auto-generated constructor stub
	}

	public ParkingException(String message) {
		super("{\"status\":\"500\",\"errorCode\":\"500\",\"msg\":\""+(message).replaceAll("[\"\\[\\]]", "'")+"\"}");
	}
	
	public ParkingException(String status, String errorCode, String message) {
		super("{\"status\":\""+status+"\",\"errorCode\":\""+errorCode+"\",\"msg\":\""+(message).replaceAll("[\"\\[\\]]", "'")+"\"}");
	}
	
	public ParkingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ParkingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ParkingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
