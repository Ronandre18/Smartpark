package com.smartpark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
public class ParkingLot {
	
	@Id
	@Size(max = 50)
	private String lotId;
	private String location;
	private int capacity;
	private int occupiedSpaces;
	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getOccupiedSpaces() {
		return occupiedSpaces;
	}

	public void setOccupiedSpaces(int occupiedSpaces) {
		this.occupiedSpaces = occupiedSpaces;
	}
	

	public ParkingLot() {
	}
	

	
	public ParkingLot(@Size(max = 50) String lotId, String location, int capacity) {
		super();
		this.lotId = lotId;
		this.location = location;
		this.capacity = capacity;
		this.occupiedSpaces = 0;
	}

	
	
	
	

}
