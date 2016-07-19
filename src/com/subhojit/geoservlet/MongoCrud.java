package com.subhojit.geoservlet;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;


import com.mongodb.MongoClient;

public interface MongoCrud {

	public void add(MongoClient mClient,HttpServletRequest request,HttpServletResponse response);
	public JSONArray retreive (MongoClient mClient,HttpServletRequest request,HttpServletResponse response);
	public void update(MongoClient mClient,HttpServletRequest request,HttpServletResponse response);
	public String remove(MongoClient mClient,HttpServletRequest request,HttpServletResponse response);
}
