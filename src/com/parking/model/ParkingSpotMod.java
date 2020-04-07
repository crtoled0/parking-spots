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

	private MongoDriver db;
	private MongoCollection<ParkingSpotPO> coll;	
	private List<String> allowedTypes = Arrays.asList("standard", "20kw", "50kw");
	
	public ParkingSpotMod() {
		this.db = MongoDriver.getInstance();		
		this.coll =  this.db.getDB().getCollection("ParkingSlot", ParkingSpotPO.class);		
		IndexOptions indexOptions = new IndexOptions().unique(true);
		this.coll.createIndex(Indexes.ascending("identifier"), indexOptions);
	}	
	
	private void refreshMongoConnection() {
		this.db = MongoDriver.getInstance();		
		this.coll =  this.db.getDB().getCollection("ParkingSlot", ParkingSpotPO.class);
	}
	
	public List<ParkingSpotPO> find(String filter) {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		
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
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<ParkingSpotPO> cursor = this.coll.find().iterator();
		List<ParkingSpotPO> list = new ArrayList<ParkingSpotPO>(); 
		while(cursor.hasNext())
		    list.add(cursor.next());
		return  list;
	}
	
	public void create(ParkingSpotPO obj) throws ParkingException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {
			this.coll.insertOne(obj);
		} catch (Exception e) {
			throw new ParkingException("400","00400",e.getMessage().toString());
		}
        
	}
	
	public void create(List<ParkingSpotPO> objList) throws ParkingException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {
			this.coll.insertMany(objList);
		} catch (Exception e) {
			throw new ParkingException("400","00400",e.getMessage().toString());
		}        
	}
	
	public ParkingSpotPO save(ParkingSpotPO obj) {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
        System.out.println("Original Person Model: " + obj);
        return this.coll.findOneAndReplace(eq("identifier", obj.getIdentifier()), obj);
	}
	
	public long delete(String identifier) {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		DeleteResult res = this.coll.deleteMany(eq("identifier", identifier));
		return res.getDeletedCount();
	}
	
	public ParkingSpotPO getAvailableSpot(String carType) throws ParkingException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<ParkingSpotPO> cursor = this.coll.find(and(eq("type", carType),eq("available", true))).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new ParkingException("400","00404","No Parking Spot Available for "+carType);	
		
    }
	
	public ParkingSpotPO getSpotByIdentifier(String parkIdentifier) throws ParkingException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<ParkingSpotPO> cursor = this.coll.find(eq("identifier", parkIdentifier)).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new ParkingException("400","00400","Parking Spot "+parkIdentifier+" doesn't exist. ");	
		
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
			throw new ParkingException("400","00400",msg);
		
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

