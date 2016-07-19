package com.subhojit.geoservlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mongodb.MongoClient;

public class GeoServletContextListener implements ServletContextListener {
	private final String TAG = "MongoServletContextListener:";
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println(TAG+" :contextDestroyed()");
		MongoClient mCLient = (MongoClient)arg0.getServletContext().getAttribute("MONGO_CLIENT");
		if(mCLient!=null){
			mCLient.close();
			System.out.println(TAG+"Closing MongoClient");
		}else System.out.println(TAG+"ERROR:MongoClient is NULL");		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println(TAG+" :contextInitialized()");
		MongoClient mClient = new MongoClient(
                arg0.getServletContext().getInitParameter("MONGODB_HOST"), 
                Integer.parseInt(arg0.getServletContext().getInitParameter("MONGODB_PORT")));
						
		arg0.getServletContext().setAttribute("MONGO_CLIENT",mClient);
		System.out.println(TAG+"Setting MongoClient attribute");
		if(mClient.getDatabase("locationfinder").getCollection("pointlocationfeed")==null){
			mClient.getDatabase("locationfinder").createCollection("pointlocationfeed");
			System.out.println(TAG+"Creating collection pointlocationfeed");
		}else
			System.out.println(TAG+"Collection pointlocationfeed already exists");	

	}

}
