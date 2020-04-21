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
@WebMvcTest(com.parking.view.PricePolicyRest.class)
@AutoConfigureMockMvc
public class TestPricePolicyRest {
   
    private String authToken;

    @Autowired
	private MockMvc mvc;


    @BeforeEach
    public void authorize() throws Exception {
        this.authToken = "123123";
        
    }  
    
    @Test
	public void notAuthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/PricePolicy")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
    }

    @Test
	public void wrongAuthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/PricePolicy")
                .header("Authorization", "")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(401));
    }

    @Test
	public void getAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/PricePolicy")
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }
    
    @Test
	public void getFiltered() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/PricePolicy")
                .header("Authorization", this.authToken)
                .param("filter", "A0")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }
    
    @Test
	public void createPolicyWrongFomula() throws Exception {
        String content = "{\"name\": \"new-standard-policy\", \"hourPrice\": 8.0, \"fixedAmount\": 3.0, \"priceFormula\": \"nh > 0.1? rfgt (fa + hp*nh):0\"}";
        mvc.perform(MockMvcRequestBuilders.post("/PricePolicy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
              //  .andDo(print())
				.andExpect(status().isBadRequest());
    }

 
    @Test
	public void createPolicy() throws Exception {
        String content = "{\"name\": \"new-standard-policy\", \"hourPrice\": 8.0, \"fixedAmount\": 3.0, \"priceFormula\": \"nh > 0.1?(fa + hp*nh):0\"}";
        mvc.perform(MockMvcRequestBuilders.post("/PricePolicy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
               // .andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
	public void updatePolicy() throws Exception {
        String content = "{\"hourPrice\": 10.0, \"fixedAmount\": 5.0, \"priceFormula\": \"nh > 0.1?(fa + hp*nh*2):0\"}";
        mvc.perform(MockMvcRequestBuilders.put("/PricePolicy/new-standard-policy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", this.authToken)
                .accept(MediaType.APPLICATION_JSON))
                //.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
    }
    
    @Test
	public void deletePolicy() throws Exception {
        //String content = "{\"identifier\": \"D010\",\"type\": \"50kw\",\"pricePolicy\": \"policy-50kw\"}";
        mvc.perform(MockMvcRequestBuilders.delete("/PricePolicy/new-standard-policy")
                .header("Authorization", this.authToken)
               // .param("id", "D010")
                .accept(MediaType.APPLICATION_JSON))
              //  .andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}
}