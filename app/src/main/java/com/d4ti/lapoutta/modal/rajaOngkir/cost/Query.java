package com.d4ti.lapoutta.modal.rajaOngkir.cost;

import com.google.gson.annotations.SerializedName;

public class Query{

	@SerializedName("courier")
	private String courier;

	@SerializedName("origin")
	private String origin;

	@SerializedName("destination")
	private String destination;

	@SerializedName("weight")
	private int weight;

	public void setCourier(String courier){
		this.courier = courier;
	}

	public String getCourier(){
		return courier;
	}

	public void setOrigin(String origin){
		this.origin = origin;
	}

	public String getOrigin(){
		return origin;
	}

	public void setDestination(String destination){
		this.destination = destination;
	}

	public String getDestination(){
		return destination;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	public int getWeight(){
		return weight;
	}

	@Override
 	public String toString(){
		return 
			"Query{" + 
			"courier = '" + courier + '\'' + 
			",origin = '" + origin + '\'' + 
			",destination = '" + destination + '\'' + 
			",weight = '" + weight + '\'' + 
			"}";
		}
}