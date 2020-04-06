package com.parking.exception;

import java.util.List;

import com.google.gson.Gson;

public class ParkingException extends Exception {

	private static final long serialVersionUID = 1L;
	public ParkingException() {
		// TODO Auto-generated constructor stub
	}

	public ParkingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
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
