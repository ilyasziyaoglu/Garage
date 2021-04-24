package com.example.garage.model.dto;

import com.example.garage.model.enums.VehicleType;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-22
 */

public class TicketResponse {
	private Long id;
	private String plate;
	private String color;
	private VehicleType vehicleType;
	private List<Integer> slots;

	public TicketResponse() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Integer> getSlots() {
		return slots;
	}

	public void setSlots(List<Integer> slots) {
		this.slots = slots;
	}
}
