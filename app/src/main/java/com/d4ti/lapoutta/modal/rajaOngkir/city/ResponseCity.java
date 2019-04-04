package com.d4ti.lapoutta.modal.rajaOngkir.city;

import com.google.gson.annotations.SerializedName;

public class ResponseCity{

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
			"ResponseCity{" + 
			"rajaongkir = '" + rajaongkir + '\'' + 
			"}";
		}
}