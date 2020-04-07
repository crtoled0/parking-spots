package com.parking.model.db;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.util.Properties;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDriver {
    
	private static MongoDriver _instance = null;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private CodecRegistry pojoCodecRegistry;
	private boolean isMongoDown = true;
	
	private MongoDriver() {
		Properties prop = new Properties();
		String localUrl = "mongodb://localhost:27017", dbName = "parking-db";
		try {
			prop.load(MongoDriver.class.getClassLoader().getResourceAsStream("com/parking/resources/config.properties"));
			localUrl = prop.getProperty("mongo.url");
			dbName = prop.getProperty("mongo.db");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mongoClient = MongoClients.create(localUrl);
		 // create codec registry for POJOs
        this.pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                				 fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.database = this.mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
        this.isMongoDown = false;
	}
	
	public boolean isMongoDown() {
		return isMongoDown;
	}

	public static MongoDriver getInstance() {
		if(MongoDriver._instance == null || MongoDriver._instance.mongoClient == null)
			return new MongoDriver();
		else {
			return MongoDriver._instance;
		}	
	}

	public MongoDatabase getDB() {
		return database;
	}
	
	@Override
	public void finalize() {
	    this.mongoClient.close();
	    this.mongoClient = null;
	    this.isMongoDown = true;
	}
}
