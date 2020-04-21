package com.parking.view;

import java.util.Map;

import com.parking.controller.ParkingManagerCont;
import com.parking.dto.ResponseDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingSpotManRest {

    private final ParkingManagerCont parkManCont;
    
    public ParkingSpotManRest() {
        this.parkManCont = new ParkingManagerCont();
    }

    @GetMapping("/ParkingSpotMan/{carType}")
    public ResponseDTO<Map> getParkingSpotMan(@PathVariable("carType") String carType) {
       	return this.parkManCont.checkin(carType);
	}

    @PutMapping("/ParkingSpotMan/{parkIdentifier}")
    public ResponseDTO<Map> putParkingSpotMan(@PathVariable("parkIdentifier") String parkIdentifier) {
        return this.parkManCont.checkout(parkIdentifier);
    }
    
    @PostMapping("/ParkingSpotMan/{parkIdentifier}")
    public ResponseDTO<Map> postParkingSpotMan(@PathVariable("parkIdentifier") String parkIdentifier) {
        return this.parkManCont.confirm(parkIdentifier);
	}
}