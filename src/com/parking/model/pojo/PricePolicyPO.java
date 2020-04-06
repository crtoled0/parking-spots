package com.parking.model.pojo;

import org.bson.types.ObjectId;

public class PricePolicyPO {
	/**
	 * 
	 */
	private ObjectId _id;
	private String name;
	private float hourPrice;
	private float fixedAmount = 0;
	private String priceFormula = "fa + hp*nh";
	
	public PricePolicyPO() {
		
	}
	
	public PricePolicyPO(String name, float hourPrice) {
		super();
		this.name = name;
		this.hourPrice = hourPrice;
	}

	
	public PricePolicyPO(String name, float hourPrice, float fixedAmount, String priceFormula) {
		super();
		this.name = name;
		this.hourPrice = hourPrice;
		this.fixedAmount = fixedAmount;
		this.priceFormula = priceFormula;
	}
	
	public PricePolicyPO(ObjectId _id, String name, float hourPrice, float fixedAmount, String priceFormula) {
		super();
		this._id = _id;
		this.name = name;
		this.hourPrice = hourPrice;
		this.fixedAmount = fixedAmount;
		this.priceFormula = priceFormula;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getHourPrice() {
		return hourPrice;
	}
	public void setHourPrice(float hourPrice) {
		this.hourPrice = hourPrice;
	}
	public float getFixedAmount() {
		return fixedAmount;
	}
	public void setFixedAmount(float fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	public String getPriceFormula() {
		return priceFormula;
	}
	public void setPriceFormula(String priceFormula) {
		this.priceFormula = priceFormula;
	}

	
}
