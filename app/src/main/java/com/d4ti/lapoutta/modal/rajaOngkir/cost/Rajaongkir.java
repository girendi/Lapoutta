package com.d4ti.lapoutta.modal.rajaOngkir.cost;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Rajaongkir{

	@SerializedName("query")
	private Query query;

	@SerializedName("destination_details")
	private DestinationDetails destinationDetails;

	@SerializedName("origin_details")
	private OriginDetails originDetails;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("status")
	private Status status;

	public void setQuery(Query query){
		this.query = query;
	}

	public Query getQuery(){
		return query;
	}

	public void setDestinationDetails(DestinationDetails destinationDetails){
		this.destinationDetails = destinationDetails;
	}

	public DestinationDetails getDestinationDetails(){
		return destinationDetails;
	}

	public void setOriginDetails(OriginDetails originDetails){
		this.originDetails = originDetails;
	}

	public OriginDetails getOriginDetails(){
		return originDetails;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setStatus(Status status){
		this.status = status;
	}

	public Status getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Rajaongkir{" + 
			"query = '" + query + '\'' + 
			",destination_details = '" + destinationDetails + '\'' + 
			",origin_details = '" + originDetails + '\'' + 
			",results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}