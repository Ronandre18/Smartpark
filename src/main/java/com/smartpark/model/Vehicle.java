package com.smartpark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
public class Vehicle {
	
	@Id
	@Pattern(regexp = "^[A-Za-z0-9-]+$")
	private String licensePlate;
	@Enumerated(EnumType.STRING)
	private VehicleType type;
	@Pattern(regexp = "^[A-Za-z]+$")
	private String ownerName;
	@ManyToOne
	@JoinColumn(name = "parking_lot_id")
	private ParkingLot parkingLot;
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	
	
	public Vehicle() {
		
	}
	

}
