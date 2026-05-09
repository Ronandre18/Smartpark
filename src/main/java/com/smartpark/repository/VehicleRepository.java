package com.smartpark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartpark.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String>{
	 List<Vehicle> findAllByOrderByParkingLot_LotIdAsc();
	 
	 List<Vehicle> findByParkingLot_LotId(String lotId);
}
