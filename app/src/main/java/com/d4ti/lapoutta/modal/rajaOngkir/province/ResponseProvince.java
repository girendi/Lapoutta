package com.d4ti.lapoutta.modal.rajaOngkir.province;

import com.google.gson.annotations.SerializedName;

public class ResponseProvince{

	@SerializedName("rajaongkir")
	private Rajaongkir rajaongkir;

	public void setRajaongkir(Rajaongkir rajaongkir){
		this.rajaongkir = rajaongkir;
	}

	public Rajaongkir getRajaongkir(){
		return rajaongkir;
	}

	@Override
 	public String toString(){
		return 
			"ResponseProvince{" + 
			"rajaongkir = '" + rajaongkir + '\'' + 
			"}";
		}
}