package com.parking.view;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


//@SpringBootTest(classes = com.parking.view.Application.class)
@ContextConfiguration(classes=com.parking.view.Application.class)
@WebMvcTest(com.parking.view.ParkingSpotManRest.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class TestParkingSpotManRest {
   
    private static String savedSpotId;

    @Autowired
	private MockMvc mvc;


    @Test
    @Order(1)
	public void checkinWrongType() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ParkingSpotMan/75kw")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());             
    }

    @Test
    @Order(2)
	public void checkin() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/ParkingSpotMan/20kw")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn(); 

         String content = result.getResponse().getContentAsString(); 
         ObjectMapper mapper = new ObjectMapper();
         Map resp = mapper.readValue(content, Map.class);
         Map node = (Map)resp.get("result");
         savedSpotId = (String)node.get("identifier");
         System.out.println(content);         
         System.out.println(savedSpotId);
    }    

   
    @Test
    @Order(3)
	public void checkoutOverFreeSpot() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/ParkingSpotMan/C003")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
    }
    
    
    @Test
    @Order(4)
	public void confirmNotYetCheckedOut() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/ParkingSpotMan/"+savedSpotId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
	public void checkoutOK() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/ParkingSpotMan/"+savedSpotId)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }

 
    @Test
    @Order(6)
	public void confirm() throws Exception {
        
        mvc.perform(MockMvcRequestBuilders.post("/ParkingSpotMan/"+savedSpotId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }  
}