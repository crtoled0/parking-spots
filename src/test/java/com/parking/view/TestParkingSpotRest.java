package com.parking.view;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


//@SpringBootTest(classes = com.parking.view.Application.class)
@ContextConfiguration(classes=com.parking.view.Application.class)
@WebMvcTest(com.parking.view.ParkingSpotRest.class)
@AutoConfigureMockMvc
public class TestParkingSpotRest {
   
    private String authToken;

    @Autowired
	private MockMvc mvc;


    @BeforeEach
    public void authorize() throws Exception {
        this.authToken = "123123";
        
    }  
    
    @Test
	public void notAuthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ParkingSpot")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
    }

    @Test
	public void wrongAuthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ParkingSpot")
                .header("Authorization", "")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(401));
    }

    @Test
	public void getAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ParkingSpot")
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }
    
    @Test
	public void getFiltered() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/ParkingSpot")
                .header("Authorization", this.authToken)
                .param("filter", "A0")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }
    
    @Test
	public void createSpotNoPolicy() throws Exception {
        String content = "[{\"identifier\": \"D010\",\"type\": \"20kw\",\"pricePolicy\": \"20kw\"}]";
        mvc.perform(MockMvcRequestBuilders.post("/ParkingSpot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
              //  .andDo(print())
				.andExpect(status().isBadRequest());
    }

 
    @Test
	public void createSpot() throws Exception {
        String content = "[{\"identifier\": \"D010\",\"type\": \"20kw\",\"pricePolicy\": \"policy-20kw\"}]";
        mvc.perform(MockMvcRequestBuilders.post("/ParkingSpot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
               // .andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
	public void updateSpot() throws Exception {
        String content = "{\"identifier\": \"D010\",\"type\": \"50kw\",\"pricePolicy\": \"policy-50kw\"}";
        mvc.perform(MockMvcRequestBuilders.put("/ParkingSpot/D010")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
                //.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
	public void deleteSpot() throws Exception {
        //String content = "{\"identifier\": \"D010\",\"type\": \"50kw\",\"pricePolicy\": \"policy-50kw\"}";
        mvc.perform(MockMvcRequestBuilders.delete("/ParkingSpot/D010")
                .header("Authorization", this.authToken)
               // .param("id", "D010")
                .accept(MediaType.APPLICATION_JSON))
              //  .andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}
}