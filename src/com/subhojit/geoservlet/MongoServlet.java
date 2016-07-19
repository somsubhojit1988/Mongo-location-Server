package com.subhojit.geoservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

/**
 * Servlet implementation class MongoServlet
 */
@WebServlet("/MongoServlet")
public class MongoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static enum Operation {
		UNDEFINED,
		ADD ,//1
		RETREIVE,//2
		UPDATE,//3
		DELETE//4
	}
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MongoServlet() {
        super();
        // TODO Auto-generated constructor stub
		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
		/*response.getWriter().append("Served at: ").append(request.getContextPath());*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void doService( HttpServletRequest request, HttpServletResponse response){		
		MongoClient mClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
		if(mClient==null){
			System.out.println("error====>COULD NOT RETREIVE MONGOCLIENT ISTANCE");
			return;
		}
		MongoCrudImpl <GeoDataSource> gCrud = new GeoDataCrud(request);
		gCrud.operate(mClient, request, response);
	}
}
