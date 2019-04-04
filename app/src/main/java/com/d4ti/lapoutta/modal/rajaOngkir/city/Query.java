package com.d4ti.lapoutta.modal.rajaOngkir.city;

import com.google.gson.annotations.SerializedName;

public class Query{

	@SerializedName("province")
	private String province;

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	@Override
 	public String toString(){
		return 
			"Query{" + 
			"province = '" + province + '\'' + 
			"}";
		}
}