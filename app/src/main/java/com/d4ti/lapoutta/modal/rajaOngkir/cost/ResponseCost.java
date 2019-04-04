package com.d4ti.lapoutta.modal.rajaOngkir.cost;

import com.google.gson.annotations.SerializedName;

public class ResponseCost{

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
			"ResponseCost{" + 
			"rajaongkir = '" + rajaongkir + '\'' + 
			"}";
		}
}