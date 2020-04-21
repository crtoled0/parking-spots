package com.parking.view;

import java.util.List;

import com.parking.controller.MaintainerCont;
import com.parking.dto.ResponseDTO;
import com.parking.dto.pojo.ParkingSpotPO;
import com.parking.exception.AccessException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingSpotRest {

    private MaintainerCont maintainerCont;

    public ParkingSpotRest(){
        this.maintainerCont = new MaintainerCont();
    }

    @GetMapping("/ParkingSpot")
    public ResponseDTO<List> getParkingSpot(@RequestParam(value = "filter", defaultValue = "") String filter, 
                                            @RequestHeader(value = "Authorization") String authToken)  {
        if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");

		return this.maintainerCont.find("ParkingSpot", filter);				
    }
    
    /**
    @PostMapping("/ParkingSpot")
    public ResponseDTO<String> postParkingSpot(@RequestBody ParkingSpotPO obj, 
                                               @RequestHeader(value = "Authorization") String authToken) {
        try {
            if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");
            
			return this.maintainerCont.create("ParkingSpot", obj);		
		} catch (Exception e) {
            return new ResponseDTO(e, false);			
		}
    }
    **/

    @PostMapping("/ParkingSpot")
    public ResponseDTO<String> postParkingSpot(@RequestBody ParkingSpotPO[] objs, 
                                               @RequestHeader(value = "Authorization") String authToken) {
       
        if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");
        return this.maintainerCont.bulkCreateParkingSpots(objs);				
    }
    
    
    @PutMapping("/ParkingSpot/{id}")
    public ResponseDTO<String> putParkingSpot(@RequestBody ParkingSpotPO obj, 
                                              @PathVariable("id") String id,
                                              @RequestHeader(value = "Authorization") String authToken) {
        if(authToken == null || authToken == "")
            throw new AccessException("Not Authorized");
        
        obj.setIdentifier(id);
		return this.maintainerCont.save("ParkingSpot", obj);
    }
    
    @DeleteMapping("/ParkingSpot/{id}")
    public ResponseDTO<String> deleteParkingSpot(@PathVariable(value = "id") String id,
                                                 @RequestHeader(value = "Authorization") String authToken) {        
        if(authToken == null || authToken == "")
            throw new AccessException("Not Authorized");

        return this.maintainerCont.delete("ParkingSpot", id);	
	}
}