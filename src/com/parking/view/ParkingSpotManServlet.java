package com.parking.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.parking.controller.ParkingManagerCont;
import com.parking.exception.ParkingException;

/**
 * Servlet implementation class ParkingSpotManServlet
 */
@WebServlet(name = "ParkingSpotMan", urlPatterns = { "/ParkingSpotMan" })
public class ParkingSpotManServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ParkingManagerCont parkManCont;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParkingSpotManServlet() {
        super();
        this.parkManCont = new ParkingManagerCont();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();	
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			String json = this.parkManCont.checkin(params);	
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			response.setStatus(500);
			Map err = new HashMap();
			err.put("ok", false);
			err.put("msg", e.getMessage());			
			response.getWriter().print(gson.toJson(err, Map.class));			
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();	
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			String json = this.parkManCont.checkout(params);	
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			response.setStatus(500);
			Map err = new HashMap();
			err.put("ok", false);
			err.put("msg", e.getMessage());			
			response.getWriter().print(gson.toJson(err, Map.class));			
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();	
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			String json = this.parkManCont.confirm(params);	
			response.setStatus(200);
			response.getWriter().print(json);
		} catch (ParkingException e) {
			response.setStatus(500);
			Map err = new HashMap();
			err.put("ok", false);
			err.put("msg", e.getMessage());			
			response.getWriter().print(gson.toJson(err, Map.class));			
		}
	}

}
