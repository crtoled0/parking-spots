package com.parking.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.parking.dto.ResponseDTO;
import com.parking.dto.pojo.ParkingSpotPO;
import com.parking.dto.pojo.PricePolicyPO;
import com.parking.exception.BadRequestException;
import com.parking.exception.NotAcceptableException;
import com.parking.model.ParkingSpotMod;
import com.parking.model.PricePolicyMod;


public class ParkingManagerCont {
	private ParkingSpotMod partSpotMod;
	private PricePolicyMod pricePolMod;

	public ParkingManagerCont() {
		this.partSpotMod = new ParkingSpotMod();
		this.pricePolMod = new PricePolicyMod();
	}
	
	public ResponseDTO<Map> checkin(String carType) throws BadRequestException {
		if(carType == null || carType == "-")
			throw new BadRequestException("Field carType is Mandatory");
			
		ParkingSpotPO spot = this.partSpotMod.getAvailableSpot(carType);
		spot.setAvailable(false);
		spot.setCheckedIn(Instant.now());
		this.partSpotMod.save(spot);	
		return new ResponseDTO<Map>(spot.pick(Arrays.asList("identifier","type","checkedIn")));	
		//return "{\"ok\":true, spot: "+gson.toJson(spot, ParkingSpotPO.class)+"}";
	}
	
	public ResponseDTO<Map> checkout(String parkIdentifier) throws BadRequestException {
		if(parkIdentifier == null || parkIdentifier == "-")
			throw new BadRequestException("Field parkIdentifier is Mandatory");
				
		ParkingSpotPO spot = this.partSpotMod.getSpotByIdentifier(parkIdentifier);
		if(spot.getCheckedIn() == null || spot.isAvailable())
			throw new BadRequestException("Spot was already checked out and released.");
		float toPay = this.calculatePrice(spot);
		System.out.println("toPay: "+ toPay);
		spot.setToPay(toPay);
		System.out.println("getToPay: "+ spot.getToPay());
		this.partSpotMod.save(spot);	
		System.out.println("getToPay: "+ spot.getToPay());
		Map res = spot.pick(Arrays.asList("identifier","type","checkedIn","toPay"));
		res.put("checkoutAt", Instant.now().toString());
		return new ResponseDTO<Map>(res);
		//return "{\"ok\":true, \"spot\": \""+spot.getIdentifier()+"\",\"type\":\""+spot.getType()+"\", \"checkedIn\":\""+spot.getCheckedIn().toString()+"\", \"total\": "+toPay+"}";
	}
	
	public ResponseDTO<Map> confirm(String parkIdentifier) throws BadRequestException {
		
		if(parkIdentifier == null || parkIdentifier == "-")
			throw new BadRequestException("Field parkIdentifier is Mandatory");
		
		ParkingSpotPO spot = this.partSpotMod.getSpotByIdentifier(parkIdentifier);
		if(spot.getCheckedIn() == null || spot.isAvailable())
			throw new BadRequestException("Spot was already checked out and released.");
		if(spot.getToPay() == null) 
			throw new BadRequestException("You need to checkout spot "+spot.getIdentifier()+" before confirm payment");		
		
		//float toPay = spot.getToPay().floatValue();
		//String checkedIn = spot.getCheckedIn().toString();
		Map res = spot.pick(Arrays.asList("identifier","type","checkedIn","toPay"));
		res.put("payed", spot.getToPay());
		spot.setCheckedIn(null);
		spot.setAvailable(true);
		spot.setToPay(null);
		this.partSpotMod.save(spot);
		return new ResponseDTO<Map>(res);
		//return "{\"ok\":true, \"spot\": \""+spot.getIdentifier()+"\",\"type\":\""+spot.getType()+"\", \"checkedIn\":\""+checkedIn+"\",\"payed\":"+toPay+"}";	
	}
	
	private float calculatePrice(ParkingSpotPO spot) throws NotAcceptableException {
		
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
			throw new NotAcceptableException("Failed use of formula associated to price policy "+spot.getPricePolicy()+ ". Please check formula and try again");
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
