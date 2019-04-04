package com.d4ti.lapoutta.modal.rajaOngkir.province;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Rajaongkir{

	@SerializedName("query")
	private List<Object> query;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("status")
	private Status status;

	public void setQuery(List<Object> query){
		this.query = query;
	}

	public List<Object> getQuery(){
		return query;
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
			",results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}