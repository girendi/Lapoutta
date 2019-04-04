package com.d4ti.lapoutta.modal.rajaOngkir.province;

import com.google.gson.annotations.SerializedName;

public class Status{

	@SerializedName("code")
	private int code;

	@SerializedName("description")
	private String description;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	@Override
 	public String toString(){
		return 
			"Status{" + 
			"code = '" + code + '\'' + 
			",description = '" + description + '\'' + 
			"}";
		}
}