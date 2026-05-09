package com.smartpark.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartpark.model.ParkingLot;
import com.smartpark.model.Vehicle;
import com.smartpark.service.ParkingLotService;

@RestController	
@RequestMapping("parking/api")
public class ParkingController {
	
	private final ParkingLotService parkingService;

	public ParkingController(ParkingLotService parkingService) {
		this.parkingService = parkingService;
	}
	
	@PostMapping("RegisterParkingLot")
	public ParkingLot registerParkingLot(@RequestBody ParkingLot parking) {
		return parkingService.registerParkingLot(parking);
	}
	
	@PostMapping("RegisterVehicle")
	public Vehicle registerVehicle(@RequestBody Vehicle vehicle) {
		return parkingService.registerVehicle(vehicle);
	}
	
	@PostMapping("CheckIn/{lotId}/{licensePlate}")
	public ResponseEntity<?> checkInVehicle(@PathVariable String lotId, @PathVariable String licensePlate) {
		return parkingService.checkInVehicle(lotId, licensePlate);
	}
	
	@PostMapping("Checkout/{licensePlate}")
	public ResponseEntity<?> checkOutVehicle(@PathVariable String licensePlate) {
		return parkingService.checkOutVehicle(licensePlate);
	}
	
	@GetMapping("ParkingLots/status/{lotId}")
	public Map<String, Object> getParkingLotStatus(@PathVariable String lotId){
		ParkingLot parking = parkingService.getParkingLotStatus(lotId);
		
		return Map.of(
				"lotId", parking.getLotId(),
				"location", parking.getLocation(),
				"capacity", parking.getCapacity(),
				"occupiedSpaces", parking.getOccupiedSpaces(),
				"avaiableSpaces", parking.getCapacity() - parking.getOccupiedSpaces()
				);
	}
	
	@GetMapping("GetAllVehiclesInLot/{lotId}")
	public List<Vehicle> getVehicleInLot(@PathVariable String lotId){
		return parkingService.getVehicleInLot(lotId);
	}
	
	@GetMapping("GetAllVehicles")
	public List<Vehicle> getAllVehiclesPark(){
		return parkingService.getAllVehiclesPark();
	}
	
}
