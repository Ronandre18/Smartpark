package com.smartpark.service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartpark.model.ParkingLot;
import com.smartpark.model.Vehicle;
import com.smartpark.repository.ParkingLotRepository;
import com.smartpark.repository.VehicleRepository;

@Service
public class ParkingLotService {
		
	private final ParkingLotRepository parkingLotRepository;
	private final VehicleRepository vehicleRepository;

	public ParkingLotService(ParkingLotRepository parkingLotRepository, VehicleRepository vehicleRepository) {
		super();
		this.parkingLotRepository = parkingLotRepository;
		this.vehicleRepository = vehicleRepository;
	}
	
	public Boolean isParkingExists(String lotId) {
		Optional<ParkingLot> parking = parkingLotRepository.findById(lotId);
		if (parking.isPresent()) {
			return true;
		}
		return false;
	}
	
	public Boolean isVehicleExists(String licensedPlate) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(licensedPlate);
		if (vehicle.isPresent()) {
			return true;
		}
		return false;
	}
	
	public ParkingLot registerParkingLot(ParkingLot parking) {
		if(isParkingExists(parking.getLotId())) {
		 throw new ResponseStatusException(
				 HttpStatus.BAD_REQUEST,"Parking Lot already exists");
		}
		if(parking.getLotId().length() >50) {
		 throw new ResponseStatusException(
				 HttpStatus.BAD_REQUEST,"Lot Id max length is 50");
		}
		return parkingLotRepository.save(parking);
		
	}
	
	public Vehicle registerVehicle(Vehicle vehicle) {
		 	if (!vehicle.getLicensePlate().matches("^[A-Za-z0-9-]+$")) {
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid characters in license plate");
		    }
		    if (!vehicle.getOwnerName().matches("^[A-Za-z]+$")) {
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner name must contain letters only");
		    }
		    if(isVehicleExists(vehicle.getLicensePlate())) {
		    	throw new ResponseStatusException(
						 HttpStatus.BAD_REQUEST,"Vehicle already exists");
		    }
		return vehicleRepository.save(vehicle);
	}
	
	public ResponseEntity<?> checkInVehicle(String lotId, String licensePlate) {
		ParkingLot parkingLot = parkingLotRepository.findById(lotId)
				.orElseThrow(() -> new RuntimeException("Parking lot not found"));
		
		Vehicle vehicle = vehicleRepository.findById(licensePlate)
				.orElseThrow(() -> new RuntimeException("Vehicle not found"));
		
		if(parkingLot.getOccupiedSpaces() >= parkingLot.getCapacity()) {
			throw new RuntimeException("Parking lot is full");
		}
		
		if(vehicle.getParkingLot() != null) {
			throw new RuntimeException("Vehicle already parked in another parking lot");
		}
		
		vehicle.setParkingLot(parkingLot);
		parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);
		vehicleRepository.save(vehicle);
		parkingLotRepository.save(parkingLot);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("licensePlate", vehicle.getLicensePlate());
	    response.put("message", "Vehicle checked in successfully");
	    return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> checkOutVehicle(String licensedPlate) {
		Vehicle vehicle = vehicleRepository.findById(licensedPlate)
				.orElseThrow(() -> new RuntimeException("Vehicle not found"));
		
		ParkingLot parkingLot = vehicle.getParkingLot();
		
		if(parkingLot == null) {
			throw new RuntimeException("Vehicle is not parked");
		}
		
		parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
		vehicle.setParkingLot(null);
		parkingLotRepository.save(parkingLot);
		vehicleRepository.save(vehicle);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("licensePlate", vehicle.getLicensePlate());
	    response.put("message", "Vehicle checked out successfully");
	    return ResponseEntity.ok(response);
		
	}
	
    public ParkingLot getParkingLotStatus(String lotId) {
        return parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));
    }
	 
	public List<Vehicle> getVehicleInLot(String lotId) {
		List<Vehicle> vehicles = vehicleRepository.findByParkingLot_LotId(lotId);
		return vehicles.stream().toList();
	}
	
	public List<Vehicle> getAllVehiclesPark(){
		List<Vehicle> vehicles = vehicleRepository.findAllByOrderByParkingLot_LotIdAsc();
	    return vehicles.stream()
	            .filter(vehicle -> vehicle.getParkingLot() != null)
	            .toList();
	}

}
