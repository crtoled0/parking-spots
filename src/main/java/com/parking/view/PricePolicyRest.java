
package com.parking.view;

import java.util.List;

import com.parking.controller.MaintainerCont;
import com.parking.dto.ResponseDTO;
import com.parking.dto.pojo.PricePolicyPO;
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
public class PricePolicyRest {

    private MaintainerCont maintainerCont;

    public PricePolicyRest(){
        this.maintainerCont = new MaintainerCont();
    }

    @GetMapping("/PricePolicy")
    public ResponseDTO<List> getPricePolicy(@RequestParam(value = "filter", defaultValue = "") String filter, 
                                            @RequestHeader(value = "Authorization") String authToken) {
            if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");

			return this.maintainerCont.find("PricePolicy", filter);		
    }
    
    @PostMapping("/PricePolicy")
    public ResponseDTO<String> postPricePolicy(@RequestBody PricePolicyPO obj, 
                                               @RequestHeader(value = "Authorization") String authToken) {
            if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");
            
			return this.maintainerCont.create("PricePolicy", obj);
    }

    
    @PutMapping("/PricePolicy/{polId}")
    public ResponseDTO<String> putPricePolicy(@RequestBody PricePolicyPO obj, 
                                               @PathVariable("polId") String polId,
                                               @RequestHeader(value = "Authorization") String authToken) {
            if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");            
                obj.setName(polId);
            
			return this.maintainerCont.save("PricePolicy", obj);
    }
    
    @DeleteMapping("/PricePolicy/{polId}")
    public ResponseDTO<String> deletePricePolicy(@PathVariable("polId") String polId,
                                                 @RequestHeader(value = "Authorization") String authToken) {
 
            if(authToken == null || authToken == "")
                throw new AccessException("Not Authorized");
                
			return this.maintainerCont.delete("PricePolicy", polId); 
	}
}