package com.subhojit.geoservlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import com.mongodb.MongoClient;

public abstract class MongoCrudImpl<Type> implements MongoCrud {
	protected MongoServlet.Operation operation;
	private final String  TAG = "MongoCRUD";
	public MongoCrudImpl(HttpServletRequest request) {
		String[] arguments = request.getQueryString().split("&");
		this.operation =convert( arguments[0]);		
		/*	  	this.operation = convert(Integer.parseInt(arguments[0]));	  */  
	}	
	private MongoServlet.Operation convert(String arg){
		System.out.println(TAG+" coverting=> "+arg);
		MongoServlet.Operation operation =  MongoServlet.Operation.UNDEFINED;
		if(arg.compareToIgnoreCase("add")==0){
			System.out.println(TAG + "converting to ADD");
			operation = MongoServlet.Operation.ADD;
		}else if(arg.compareToIgnoreCase("retreive")==0){
			System.out.println(TAG + "converting to RETREIVE");
			operation = MongoServlet.Operation.RETREIVE;
		}else if(arg.compareToIgnoreCase("update")==0)	{
			System.out.println(TAG + "converting to UPDATE");
			operation = MongoServlet.Operation.UPDATE;
		}else if(arg.compareToIgnoreCase("delete")==0) {
			System.out.println(TAG + "converting to DELETE");
			operation = MongoServlet.Operation.DELETE;
		}			
		return operation;							
	}
	
	public void  operate(MongoClient mClient,HttpServletRequest request,HttpServletResponse response ){		
		if(this.operation == MongoServlet.Operation.ADD){
			System.out.println(TAG+" Will call ADD");
			this.add(mClient, request, response);
		}else if(this.operation == MongoServlet.Operation.RETREIVE){
			System.out.println(TAG+"Will call RETREIVE");		
			JSONArray jArray = this.retreive(mClient, request, response);
			String result = jArray.toString();
			System.out.println(TAG+" JARRAY_RESULT(string) => "+ result);		
			response.setContentLength(result.length());
		try {
			response.getOutputStream().write(result.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else if(this.operation == MongoServlet.Operation.UPDATE){
			System.out.println(TAG+"Will call UPDATE");
			this.update(mClient, request, response);
		}else if(this.operation == MongoServlet.Operation.DELETE){
			System.out.println(TAG+"Will call DELETE");
			this.remove(mClient, request, response);
		}else 			System.out.println(TAG+"Undefined opertation");		
			
	}

}
