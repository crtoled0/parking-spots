package com.parking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.parking.dto.ResponseDTO;
import com.parking.dto.pojo.ParkingSpotPO;
import com.parking.dto.pojo.PricePolicyPO;
import com.parking.exception.BadRequestException;
import com.parking.exception.NotFoundException;
import com.parking.model.ParkingSpotMod;
import com.parking.model.PricePolicyMod;


public class MaintainerCont {
	
	private PricePolicyMod pricePolMod;
	private ParkingSpotMod parkSpotMod;
	
	public MaintainerCont() {
		this.pricePolMod = new PricePolicyMod();
		this.parkSpotMod = new ParkingSpotMod();
	}
	
	public ResponseDTO<List> find(String model, String filter) throws NotFoundException {
		//Gson gson = new Gson();		
		System.out.println(ParkingSpotPO.class);	
		//System.out.println(Class.forName(ParkingSpotPO.class));
			if(model.equalsIgnoreCase("PricePolicy")) {
				List<PricePolicyPO> res = (filter != null && filter != "")?this.pricePolMod.find(filter):this.pricePolMod.find();			
				return new ResponseDTO(res);
				//return "{\"ok\": true,\"items\": "+gson.toJson(res, List.class)+"}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				List<ParkingSpotPO> res = (filter != null && filter != "")?this.parkSpotMod.find(filter):this.parkSpotMod.find();
				return new ResponseDTO(res);
				//return "{\"ok\": true,\"items\": "+gson.toJson(res, List.class)+"}";
			}
			else {
				throw new NotFoundException("Wrong Endpoint, Please check and try again");
			}
	}
	
	public ResponseDTO<String> save(String model, Object body) throws NotFoundException {
		//Gson gson = new Gson();
			if(model.equalsIgnoreCase("PricePolicy")) {
				//PricePolicyPO obj = gson.fromJson(body, PricePolicyPO.class);
				PricePolicyPO obj = (PricePolicyPO) body;
				boolean allOK = this.pricePolMod.isValid(obj);
				if(allOK && obj.getPriceFormula() != null)
					allOK = this.validateFormula(obj.getPriceFormula());				
				if(allOK)
					this.pricePolMod.save(obj);	
				return new ResponseDTO<String>("Success");				
				//return "{\"ok\": true}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				//ParkingSpotPO obj = gson.fromJson(body, ParkingSpotPO.class);
				ParkingSpotPO obj = (ParkingSpotPO) body;
				if(this.parkSpotMod.isValid(obj))
					this.parkSpotMod.save(obj);
			    return new ResponseDTO<String>("Success");	
					//return "{\"ok\": true}";
			}
			else {
				throw new NotFoundException("Wrong Parameters, Please check and try again");
			}		
	}
	
	public ResponseDTO<String> create(String model, Object body) throws NotFoundException {
	//	Gson gson = new Gson();
			if(model.equalsIgnoreCase("PricePolicy")) {
				//PricePolicyPO obj = gson.fromJson(body, PricePolicyPO.class);
				PricePolicyPO obj = (PricePolicyPO) body;
				boolean allOK = this.pricePolMod.isValid(obj);
				if(allOK && obj.getPriceFormula() != null)
					allOK = this.validateFormula(obj.getPriceFormula());				
				if(allOK)
					this.pricePolMod.create(obj);
				return new ResponseDTO<String>("Success");
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				System.out.println(body);
				List<ParkingSpotPO> lst = new ArrayList<ParkingSpotPO>();
				//ParkingSpotPO obj = gson.fromJson(body, ParkingSpotPO.class);
				lst.add((ParkingSpotPO) body);
				if(this.parkSpotMod.isValid(lst))
					this.parkSpotMod.create(lst);
				return new ResponseDTO<String>("Success");
			}
			else {
				throw new NotFoundException("Wrong Endpoint, Please check and try again");
			}				
	}

	public ResponseDTO<String> bulkCreateParkingSpots(ParkingSpotPO[] arr)  {
			List<ParkingSpotPO> lst = Arrays.asList(arr);
			if(this.parkSpotMod.isValid(lst))
				this.parkSpotMod.create(lst);
			return new ResponseDTO<String>("Success");
				//return "{\"ok\": true}";							
	}
	
	public ResponseDTO<String> delete(String model, String id) throws NotFoundException {
	//	Gson gson = new Gson();
		if(id == null || id == "-")
			throw new BadRequestException("Policy name to remove is mandatory ");
		
			if(model.equalsIgnoreCase("PricePolicy")) {
				long deleted = this.pricePolMod.delete(id);					
				return new ResponseDTO<String>("Removed "+deleted+" items", deleted>0);
				//return "{\"ok\": " +(new Boolean(deleted>0)).toString()+ "}";
			}
			else if(model.equalsIgnoreCase("ParkingSpot")) {
				long deleted = this.parkSpotMod.delete(id);					
				return new ResponseDTO<String>("Removed "+deleted+" items", deleted>0);
			}
			else {
				throw new NotFoundException("Wrong Endpoint, Please check and try again");
			}		
	}
	
	private boolean validateFormula(String form)  throws BadRequestException {
		String allowedChars = "[0-9\\.\\?\\(\\)\\+\\-\\/\\%\\*\\:\\<\\>\\s\\=]|nh|fa|hp";	
		String alloChrStr = "numeric ? : = + - * / % . nh fa hp";
		String res = form.replaceAll(allowedChars, "");
		System.out.println(res);
		System.out.println(res.length());
		if(res.length() > 0) {
			throw new BadRequestException("Formula contains the following not allowed characters ' "+res+" '. Please check and try again. Allowed Characters are '"+alloChrStr+"'");
		}
		form = form.replaceAll("fa", String.valueOf((3)));
		form = form.replaceAll("hp", String.valueOf((3)));
		form = form.replaceAll("nh", String.valueOf((3)));
		System.out.println("testing dummy form");
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			Double result = (Double) engine.eval("parseFloat("+form+")");
			System.out.println(result);
		} catch (ScriptException e) {
			throw new BadRequestException(" Mathematical error on formula. Tested example '"+form+"' failed. Please check formula and try again");			
		}		
		return true;
	}
}
