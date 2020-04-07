package com.parking.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
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
import com.parking.model.pojo.PricePolicyPO;


public class PricePolicyMod {
	
	private static List<String> pricePolicyIds;
	MongoDriver db;
	private MongoCollection<PricePolicyPO> coll;
	
	
	public PricePolicyMod() {
		this.db = MongoDriver.getInstance();		
		this.coll =  this.db.getDB().getCollection("PricePolicy", PricePolicyPO.class);
		IndexOptions indexOptions = new IndexOptions().unique(true);
		this.coll.createIndex(Indexes.ascending("name"), indexOptions);
		this.refreshPolicyList();
	}
	
	private void refreshMongoConnection() {
		this.db = MongoDriver.getInstance();		
		this.coll =  this.db.getDB().getCollection("PricePolicy", PricePolicyPO.class);
	}
	
	private void refreshPolicyList() {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<PricePolicyPO> cursor = this.coll.find().iterator();
		PricePolicyMod.pricePolicyIds = new ArrayList<String>();
		while(cursor.hasNext())
			PricePolicyMod.pricePolicyIds.add((cursor.next()).getName());
	}
	
	public static List<String> getPricePolicyIds() {
		return PricePolicyMod.pricePolicyIds;
	}	
	
	public List<PricePolicyPO> find(String filter) {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		Bson filters = regex("name", filter, "ig");
		MongoCursor<PricePolicyPO> cursor = this.coll.find(filters).iterator();
		List<PricePolicyPO> list = new ArrayList<PricePolicyPO>(); 
		while(cursor.hasNext())
		    list.add(cursor.next());
		return  list;
	}
	public List<PricePolicyPO> find() {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<PricePolicyPO> cursor = this.coll.find().iterator();
		List<PricePolicyPO> list = new ArrayList<PricePolicyPO>(); 
		while(cursor.hasNext())
		    list.add(cursor.next());
		return  list;
	}
	
	public void create(PricePolicyPO obj) throws ParkingException {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {			
			this.coll.insertOne(obj);
			this.refreshPolicyList();
		} catch (Exception e) {
			throw new ParkingException("400","00400",e.getMessage().toString());
		}
		
	}
	
	public void create(List<PricePolicyPO> objList) throws ParkingException {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {
			this.coll.insertMany(objList);
			this.refreshPolicyList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParkingException("400","00400",e.getMessage().toString());
		}        
	}
	
	public PricePolicyPO save(PricePolicyPO obj) {		
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
        return this.coll.findOneAndReplace(eq("name", obj.getName()), obj);
	}
	
	
	public long delete(String name) {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		DeleteResult res = this.coll.deleteMany(eq("name", name));
		return res.getDeletedCount();
	}
	
	public PricePolicyPO getPricePolicyByName(String name) throws ParkingException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<PricePolicyPO> cursor = this.coll.find(eq("name", name)).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new ParkingException("400","00400", "Price Policy "+name+" doesn't exist. ");	
		
    }
	
	public boolean isValid(PricePolicyPO obj) throws ParkingException {
		boolean allOK = true;
		List<String> msg = new ArrayList<String>();
		if(obj.getName() == null || 
		   new Float(obj.getHourPrice()) == null || 
		   obj.getName() == "" 
		 ) {			
			msg.add("Fields Name and Hour Price are Mandatory.");
			allOK = false;
		}
		if(!allOK)
			throw new ParkingException("400","00400", msg.toString());
		
		return allOK;
	}
}
