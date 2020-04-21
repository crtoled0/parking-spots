package com.parking.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.parking.dto.pojo.PricePolicyPO;
import com.parking.exception.BadRequestException;
import com.parking.model.db.MongoDriver;

import org.bson.conversions.Bson;

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
	
	public void create(PricePolicyPO obj) throws BadRequestException {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {			
			this.coll.insertOne(obj);
			this.refreshPolicyList();
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage().toString());
		}
		
	}
	
	public void create(List<PricePolicyPO> objList) throws BadRequestException {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {
			this.coll.insertMany(objList);
			this.refreshPolicyList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestException(e.getMessage().toString());
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
	
	public PricePolicyPO getPricePolicyByName(String name) throws BadRequestException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<PricePolicyPO> cursor = this.coll.find(eq("name", name)).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new BadRequestException( "Price Policy "+name+" doesn't exist. ");	
		
    }
	
	public boolean isValid(PricePolicyPO obj) throws BadRequestException {
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
			throw new BadRequestException(msg.toString());
		
		return allOK;
	}
}
