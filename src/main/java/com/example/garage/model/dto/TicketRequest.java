package com.example.garage.model.dto;

import com.example.garage.model.enums.VehicleType;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-22
 */
public class TicketRequest {
	private String plate;
	private String color;
	private VehicleType vehicleType;

	public TicketRequest() { }

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
}
