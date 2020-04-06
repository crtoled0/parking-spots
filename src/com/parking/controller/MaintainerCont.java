package com.parking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.parking.exception.ParkingException;
import com.parking.model.ParkingSpotMod;
import com.parking.model.PricePolicyMod;
import com.parking.model.pojo.ParkingSpotPO;
import com.parking.model.pojo.PricePolicyPO;

public class MaintainerCont {
	
	private PricePolicyMod pricePolMod;
	private ParkingSpotMod parkSpotMod;
	
	public MaintainerCont() {
		this.pricePolMod = new PricePolicyMod();
		this.parkSpotMod = new ParkingSpotMod();
	}
	
	public String find(String model, Map req) throws ParkingException {
		Gson gson = new Gson();		
		
			String[] filters = (String[])req.get("filter");
			if(model.equalsIgnoreCase("PricePolicy")) {
				List<PricePolicyPO> res = (filters != null && filters.length > 0)?this.pricePolMod.find(filters[0]):this.pricePolMod.find();			
				return "{\"ok\": true,\"items\": "+gson.toJson(res, List.class)+"}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				List<ParkingSpotPO> res = (filters != null && filters.length > 0 && filters[0] != "")?this.parkSpotMod.find(filters[0]):this.parkSpotMod.find();
				return "{\"ok\": true,\"items\": "+gson.toJson(res, List.class)+"}";
			}
			else {
				throw new ParkingException("404","00404","Wrong Endpoint, Please check and try again");
			}
	}
	
	public String save(String model, String body) throws ParkingException {
		Gson gson = new Gson();
			if(model.equalsIgnoreCase("PricePolicy")) {
				PricePolicyPO obj = gson.fromJson(body, PricePolicyPO.class);
				if(this.pricePolMod.isValid(obj))
					this.pricePolMod.save(obj);				
				return "{\"ok\": true}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				ParkingSpotPO obj = gson.fromJson(body, ParkingSpotPO.class);
				if(this.parkSpotMod.isValid(obj))
					this.parkSpotMod.save(obj);
				return "{\"ok\": true}";
			}
			else {
				throw new ParkingException("404","00404","Wrong Parameters, Please check and try again");
			}
		
	}
	
	public String create(String model, String body) throws ParkingException {
		Gson gson = new Gson();
			if(model.equalsIgnoreCase("PricePolicy")) {
				PricePolicyPO obj = gson.fromJson(body, PricePolicyPO.class);
				if(this.pricePolMod.isValid(obj))
					this.pricePolMod.create(obj);		
				return "{\"ok\": true}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				System.out.println(body);
				ParkingSpotPO[] arr;
				List<ParkingSpotPO> lst;
				try {
					arr = gson.fromJson(body, ParkingSpotPO[].class);
					lst = Arrays.asList(arr);
				}catch (Exception e) {
					ParkingSpotPO obj = gson.fromJson(body, ParkingSpotPO.class);
					lst = new ArrayList<ParkingSpotPO>(); 
					lst.add(obj);
				}
				if(this.parkSpotMod.isValid(lst))
					this.parkSpotMod.create(lst);
				return "{\"ok\": true}";
			}
			else {
				throw new ParkingException("404","00404","Wrong Endpoint, Please check and try again");
			}				
	}
	
	public String delete(String model, Map req) throws ParkingException {
		Gson gson = new Gson();
		String[] ids = (String[]) req.get("id");
		if(ids == null || ids.length == 0)
			throw new ParkingException("400","00400","Policy name to remove is mandatory ");
		
			if(model.equalsIgnoreCase("PricePolicy")) {
				long deleted = this.pricePolMod.delete(ids[0]);					
				return "{\"ok\": " +(new Boolean(deleted>0)).toString()+ "}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				long deleted = this.parkSpotMod.delete(ids[0]);					
				return "{\"ok\": " +(new Boolean(deleted>0)).toString()+ "}";
			}
			else {
				throw new ParkingException("404","00404","Wrong Endpoint, Please check and try again");
			}		
	}
}
