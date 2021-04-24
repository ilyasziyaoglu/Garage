package com.example.garage.model.enums;

/**
 * @author Ilyas Ziyaoglu
 * @date 2021-04-22
 */

public enum VehicleType {
	CAR(1),
	JEEP(2),
	TRUCK(4);

	private final int width;

	VehicleType(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}
}
