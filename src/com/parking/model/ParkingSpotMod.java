package com.parking.model;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.parking.exception.ParkingException;
import com.parking.model.db.MongoDriver;
import com.parking.model.pojo.ParkingSpotPO;

public class ParkingSpotMod {

	
	private MongoCollection<ParkingSpotPO> coll;	
	private List<String> allowedTypes = Arrays.asList("standard", "20kw", "50kw");
	
	public ParkingSpotMod() {
		MongoDriver db = MongoDriver.getInstance();		
		this.coll =  db.getDB().getCollection("ParkingSlot", ParkingSpotPO.class);		
		IndexOptions indexOptions = new IndexOptions().unique(true);
		this.coll.createIndex(Indexes.ascending("identifier"), indexOptions);

	}	
	
	public List<ParkingSpotPO> find(String filter) {
		Bson filters = or(regex("identifier", filter , "ig"),
						  regex("type", filter , "ig"),
						  regex("pricePolicy", filter, "ig"));
		MongoCursor<ParkingSpotPO> cursor = this.coll.find(filters).iterator();
		List<ParkingSpotPO> list = new ArrayList<ParkingSpotPO>(); 
		while(cursor.hasNext())
		    list.add(cursor.next());
		return  list;
	}
	public List<ParkingSpotPO> find() {
		MongoCursor<ParkingSpotPO> cursor = this.coll.find().iterator();
		List<ParkingSpotPO> list = new ArrayList<ParkingSpotPO>(); 
		while(cursor.hasNext())
		    list.add(cursor.next());
		return  list;
	}
	
	public void create(ParkingSpotPO obj) throws ParkingException {		
	//	MongoCursor<ParkingSlotPO> cursor = this.coll.find(eq("identifier",obj.getIdentifier())).iterator();
	//	if(cursor.hasNext())
		//	throw new ParkingException("Parking Slot "+ obj.getIdentifier() + " already exist");
		try {
			this.coll.insertOne(obj);
		} catch (Exception e) {
			throw new ParkingException(e.getMessage());
		}
        
	}
	
	public void create(List<ParkingSpotPO> objList) throws ParkingException {	
		//objList.
		try {
			this.coll.insertMany(objList);
		} catch (Exception e) {
			throw new ParkingException(e.getMessage());
		}        
	}
	
	public ParkingSpotPO save(ParkingSpotPO obj) {		
        System.out.println("Original Person Model: " + obj);
        return this.coll.findOneAndReplace(eq("identifier", obj.getIdentifier()), obj);
	}
	
	public long delete(String identifier) {
		DeleteResult res = this.coll.deleteMany(eq("identifier", identifier));
		return res.getDeletedCount();
	}
	
	public ParkingSpotPO getAvailableSpot(String carType) throws ParkingException {		
		MongoCursor<ParkingSpotPO> cursor = this.coll.find(and(eq("type", carType),eq("available", true))).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new ParkingException("No Parking Spot Available for "+carType);	
		
    }
	
	public ParkingSpotPO getSpotByIdentifier(String parkIdentifier) throws ParkingException {		
		MongoCursor<ParkingSpotPO> cursor = this.coll.find(eq("identifier", parkIdentifier)).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new ParkingException("Parking Spot "+parkIdentifier+" doesn't exist. ");	
		
    }
	
	
	public boolean isValid(ParkingSpotPO obj) throws ParkingException {
		boolean allOK = true;
		String msg = "";
		if(obj.getIdentifier() == null || 
		   obj.getType() == null || 
		   obj.getPricePolicy() == null || 
		   obj.getIdentifier() == "" || 
		   obj.getType() == "" || 
		   obj.getPricePolicy() == "") {
			
			msg += "Fields Identifier, Type and PricePolicy are Mandatory.  ";
			allOK = false;
		}	
		if(!PricePolicyMod.getPricePolicyIds().contains(obj.getPricePolicy())) {
			msg += "Price Policy Selected ("+obj.getPricePolicy()+") Doesn't exist.  ";
			allOK = false;
		}
		if(!allowedTypes.contains(obj.getType())) {
			msg += "Allowed Car Types are 'standard', '20kw' and '50kw' for Parking Spot "+obj.getIdentifier() + ".  ";
			allOK = false;
		}
		
		if(!allOK)
			throw new ParkingException(msg);
		
		return allOK;
	}
	
	public boolean isValid(List<ParkingSpotPO> arr) throws ParkingException { 
		Iterator<ParkingSpotPO> cursor = arr.iterator();
		boolean allOK = true;
		while(cursor.hasNext()) {
			this.isValid(cursor.next());
		}
		return allOK;
	}

}
