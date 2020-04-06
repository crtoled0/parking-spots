package com.parking.model.pojo;

import java.time.Instant;

import org.bson.types.ObjectId;

import com.parking.exception.ParkingException;

public class ParkingSpotPO {
	
	private ObjectId _id;
	private String identifier;
	private String type = "standard"; 
	private String pricePolicy;
	private boolean available = true;
	private Instant checkedIn;

	public ParkingSpotPO() {
		// TODO Auto-generated constructor stub
	}
		

	public ParkingSpotPO(String identifier, String pricePolicy, String type) throws ParkingException {
		super();
		this.type = type;
		this.identifier = identifier;
		this.pricePolicy = pricePolicy;
	}


	public ParkingSpotPO(String identifier, String type, String pricePolicy, boolean available,
			Instant checkedIn) throws ParkingException {
		super();
		//this._id = _id;
		this.identifier = identifier;
		this.type = type;
		this.pricePolicy = pricePolicy;
		this.available = available;
		this.checkedIn = checkedIn;
	}


	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) throws ParkingException {
		this.type = type;
	}

	public String getPricePolicy() {
		return pricePolicy;
	}

	public void setPricePolicy(String pricePolicy) {
		this.pricePolicy = pricePolicy;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Instant getCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(Instant checkedIn) {
		this.checkedIn = checkedIn;
	}
	
	
	
}
