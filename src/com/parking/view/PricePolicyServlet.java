package com.parking.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.parking.controller.MaintainerCont;
import com.parking.exception.ParkingException;

/**
 * Servlet implementation class PricePolicyServlet
 */
@WebServlet(name = "PricePolicy", urlPatterns = { "/PricePolicy" })
public class PricePolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MaintainerCont maintainerCont;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PricePolicyServlet() {
        super();
        this.maintainerCont = new MaintainerCont(); 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();	
		Gson gson = new Gson();		
		response.setContentType("application/json");
		try {
			String authToken = request.getHeader("Authorization");
			if(authToken == null || authToken == "")
				throw new ParkingException("401", "00401", "Not Authorized");
			String json = this.maintainerCont.find("PricePolicy", params);			
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			Map err = gson.fromJson(e.getMessage(), Map.class);
			int status = Integer.valueOf((String)err.get("status")).intValue();
			response.setStatus(status);
			err.put("ok", false);			
			response.getWriter().print(gson.toJson(err, Map.class));	
		} catch (Exception e) {
			response.setStatus(500);
			response.getWriter().print("{\"error\":\""+e.getMessage()+"\"}");	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bodyText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		Map params = request.getParameterMap();		
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			String authToken = request.getHeader("Authorization");
			if(authToken == null || authToken == "")
				throw new ParkingException("401", "00401", "Not Authorized");
			String json = this.maintainerCont.create("PricePolicy", bodyText);			
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			Map err = gson.fromJson(e.getMessage(), Map.class);
			int status = Integer.valueOf((String)err.get("status")).intValue();
			response.setStatus(status);
			err.put("ok", false);			
			response.getWriter().print(gson.toJson(err, Map.class));		
		} catch (Exception e) {
			response.setStatus(500);
			response.getWriter().print("{\"error\":\""+e.getMessage()+"\"}");	
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bodyText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		Map params = request.getParameterMap();		
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			String authToken = request.getHeader("Authorization");
			if(authToken == null || authToken == "")
				throw new ParkingException("401", "00401", "Not Authorized");
			String json = this.maintainerCont.save("PricePolicy", bodyText);			
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			Map err = gson.fromJson(e.getMessage(), Map.class);
			int status = Integer.valueOf((String)err.get("status")).intValue();
			response.setStatus(status);
			err.put("ok", false);			
			response.getWriter().print(gson.toJson(err, Map.class));			
		} catch (Exception e) {
			response.setStatus(500);
			response.getWriter().print("{\"error\":\""+e.getMessage()+"\"}");	
		}
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();	
		Gson gson = new Gson();
		String values = gson.toJson(params, Map.class);
		Object id =  params.get("id");
		response.setContentType("application/json");
		try {
			String authToken = request.getHeader("Authorization");
			if(authToken == null || authToken == "")
				throw new ParkingException("401", "00401", "Not Authorized");
			String json = this.maintainerCont.delete("PricePolicy", params);			
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			Map err = gson.fromJson(e.getMessage(), Map.class);
			int status = Integer.valueOf((String)err.get("status")).intValue();
			response.setStatus(status);
			err.put("ok", false);			
			response.getWriter().print(gson.toJson(err, Map.class));			
		} catch (Exception e) {
			response.setStatus(500);
			response.getWriter().print("{\"error\":\""+e.getMessage()+"\"}");	
		}
	}

}
