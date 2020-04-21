package com.parking.dto.pojo;

import java.time.Instant;

import com.parking.dto.DataTransferObject;

import org.bson.types.ObjectId;


public class ParkingSpotPO extends DataTransferObject{
	
	private ObjectId _id;
	private String identifier;
	private String type = "standard"; 
	private String pricePolicy;
	private boolean available = true;
	private Instant checkedIn;
	private Float toPay;

	public ParkingSpotPO() {
		// TODO Auto-generated constructor stub
	}
		

	public ParkingSpotPO(String identifier, String pricePolicy, String type) {
		super();
		this.type = type;
		this.identifier = identifier;
		this.pricePolicy = pricePolicy;
	}


	public ParkingSpotPO(String identifier, String type, String pricePolicy, boolean available,
			Instant checkedIn)  {
		super();
		//this._id = _id;
		this.identifier = identifier;
		this.type = type;
		this.pricePolicy = pricePolicy;
		this.available = available;
		this.checkedIn = checkedIn;
	}

	
	public Float getToPay() {
		return toPay;
	}
	
	public void setToPay(float toPay) {
		this.toPay = new Float(toPay);
	}
	
	public void setToPay(Float toPay) {
		this.toPay = toPay;
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

	public void setType(String type) {
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
	
	 
	/** 
	public static void main(String args[]){
		ParkingSpotPO obj = new ParkingSpotPO("A001", "standard-policy", "standard");
		Map map = obj.pick(Arrays.asList("identifier","type"));
		Map map2 = obj.omit(Arrays.asList("identifier","type","_id"));
		System.out.println(map.toString());
		System.out.println(map2.toString());
	}
	**/
}
