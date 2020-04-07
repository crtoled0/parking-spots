package com.parking.controller;

import java.time.Instant;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.Gson;
import com.parking.exception.ParkingException;
import com.parking.model.ParkingSpotMod;
import com.parking.model.PricePolicyMod;
import com.parking.model.pojo.ParkingSpotPO;
import com.parking.model.pojo.PricePolicyPO;


public class ParkingManagerCont {
	private ParkingSpotMod partSpotMod;
	private PricePolicyMod pricePolMod;

	public ParkingManagerCont() {
		this.partSpotMod = new ParkingSpotMod();
		this.pricePolMod = new PricePolicyMod();
	}
	
	public String checkin(Map req) throws ParkingException {
		String[] carTypeArg = (String[])req.get("carType");
		if(carTypeArg == null)
			throw new ParkingException("400","00400","Field carType is Mandatory");
		
		String carType = (String)carTypeArg[0];
		Gson gson = new Gson();
		ParkingSpotPO spot = this.partSpotMod.getAvailableSpot(carType);
		spot.setAvailable(false);
		spot.setCheckedIn(Instant.now());
		spot = this.partSpotMod.save(spot);		
		return "{\"ok\":true, spot: "+gson.toJson(spot, ParkingSpotPO.class)+"}";
	}
	
	public String checkout(Map req) throws ParkingException {
		
		String[] parkIdentifierArg = (String[])req.get("parkIdentifier");
		if(parkIdentifierArg == null)
			throw new ParkingException("400","00400","Field parkIdentifier is Mandatory");
		
		String parkIdentifier = (String)parkIdentifierArg[0];
		Gson gson = new Gson();
		ParkingSpotPO spot = this.partSpotMod.getSpotByIdentifier(parkIdentifier);
		float toPay = this.calculatePrice(spot);
		spot.setToPay(toPay);
		spot = this.partSpotMod.save(spot);		
		return "{\"ok\":true, \"spot\": \""+spot.getIdentifier()+"\",\"type\":\""+spot.getType()+"\", \"checkedIn\":\""+spot.getCheckedIn().toString()+"\", \"total\": "+toPay+"}";
	}
	
	public String confirm(Map req) throws ParkingException {
		
		String[] parkIdentifierArg = (String[])req.get("parkIdentifier");
		if(parkIdentifierArg == null)
			throw new ParkingException("400","00400","Field parkIdentifier is Mandatory");
		
		String parkIdentifier = (String)parkIdentifierArg[0];
		Gson gson = new Gson();
		ParkingSpotPO spot = this.partSpotMod.getSpotByIdentifier(parkIdentifier);
		if(spot.getToPay() == null) {
			throw new ParkingException("400","00400","You need to checkout spot "+spot.getIdentifier()+" before confirm payment");
		}
		float toPay = spot.getToPay().floatValue();
		String checkedIn = spot.getCheckedIn().toString();
		spot.setCheckedIn(null);
		spot.setAvailable(true);
		spot.setToPay(null);
		spot = this.partSpotMod.save(spot);		
		return "{\"ok\":true, \"spot\": \""+spot.getIdentifier()+"\",\"type\":\""+spot.getType()+"\", \"checkedIn\":\""+checkedIn+"\",\"payed\":"+toPay+"}";	
	}
	
	private float calculatePrice(ParkingSpotPO spot) throws ParkingException {
		Instant checkInTime =  spot.getCheckedIn();
		Instant checkOutTime = Instant.now();
		System.out.println("checkInTime ["+checkInTime+"]  checkOutTime: ["+checkOutTime+"]");
		PricePolicyPO policy = this.pricePolMod.getPricePolicyByName(spot.getPricePolicy());
		String formula = policy.getPriceFormula();
		if(formula == null || formula == "")
			formula = "fa + hp*nh";
		float fa = policy.getFixedAmount(), hp = policy.getHourPrice(), nh=0;
		long totalSecs = checkOutTime.getEpochSecond() - checkInTime.getEpochSecond();
		nh = (totalSecs/60);	
		nh = nh/60; 
		System.out.println(nh);
		System.out.println(formula);
		formula = formula.replaceAll("fa", String.valueOf((fa)));
		formula = formula.replaceAll("hp", String.valueOf((hp)));
		formula = formula.replaceAll("nh", String.valueOf((nh)));
		System.out.println(formula);
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		float total;
		try {
			Double result = (Double) engine.eval("parseFloat("+formula+")");
			System.out.println(result);
			total = result.floatValue();
		} catch (ScriptException e) {
			throw new ParkingException("501","00501","Failed use of formula associated to price policy "+spot.getPricePolicy()+ ". Please check formula and try again");			
		}		
		return total;
	}
	
	/** 
	public static void main(String[] args) throws ParkingException {
		//String[] vals = {"A002"};
		String[] vals = {"A001"};
		Map req = new HashMap();
		req.put("parkIdentifier", vals);
		ParkingManagerCont pmanc = new ParkingManagerCont();
		String res = pmanc.checkout(req);
		System.out.println(res);				
	**/

}
