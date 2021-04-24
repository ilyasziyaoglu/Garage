package com.example.garage.model.entity;

import com.example.garage.model.enums.Status;
import com.example.garage.model.enums.VehicleType;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-22
 */

@Entity
@Table(name = "TICKETS")
public class Ticket {

	@Id
	@GeneratedValue
	private Long id;

	@ElementCollection
	@CollectionTable(name="SLOTS", joinColumns=@JoinColumn(name="ticket_id"))
	@Column(name="slot")
	private List<Integer> slots;

	@Column(nullable = false)
	private String plate;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private VehicleType vehicleType;

	@Column
	private Status status = Status.IN_GARAGE;

	public Ticket() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Integer> getSlots() {
		return slots;
	}

	public void setSlots(List<Integer> slots) {
		this.slots = slots;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
