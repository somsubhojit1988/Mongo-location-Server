package com.subhojit.geoservlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;
public class GeoDataCrud extends MongoCrudImpl<GeoDataSource> {
	private GeoDataSource mDataSource = new GeoDataSource();
	
	
	
	public GeoDataCrud(HttpServletRequest request) {
		super(request);
		String[] params = request.getQueryString().split("&");
		for(String par:params){
			if(!par.isEmpty()){
				String[] data = par.split("=");
				System.out.println("QUERY DATA=>"+data);
				if(data.length==2){
					System.out.println("ARGUMENT=> "+ data[0]+" DATA=>"+data[1]);					
					if(data[0].equalsIgnoreCase("nameId")&&data[1]!=null){
						this.mDataSource.setNameId(data[1]);
					}else if(data[0].equalsIgnoreCase("longitude")&&data[1]!=null){
						this.mDataSource.setLongitude(Double.parseDouble(data[1]));
					}else if(data[0].equalsIgnoreCase("latitude")&&data[1]!=null){
						this.mDataSource.setLatitude(Double.parseDouble(data[1]));
					}else	continue;
				}else continue;					
			}
		}
	}

	
	private void showAllDocuments(MongoClient mClient){
		FindIterable<Document>  iterable = mClient.getDatabase("locationfinder").getCollection("pointlocationfeed").find();
		System.out.println("Total Documents : "+mClient.getDatabase("locationfinder").getCollection("pointlocationfeed").count());
		iterable.forEach(new Block<Document>() {
		    @Override		    
		        public void apply(final Document document) {
		            System.out.println(document);		            
		    }
		});
	}	
	private int countCursor(MongoCursor< Document> mCursor){
		int a = 0;
		while(mCursor.hasNext()){
			a++;
			mCursor.next();
		}						
		return a;
	}	
	@Override
	public void add(MongoClient mClient, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CRUD__ADD");
		Document ar = GeoDataConverter.dataToDoc(this.mDataSource);
		if(mClient == null)
			System.out.println("MONGOCLIENT is NULL!!");
		if(countCursor(mClient.getDatabase("locationfinder").getCollection("pointlocationfeed").find(eq("name_Id",this.mDataSource.getNameId())).iterator())<= 0){			
			mClient.getDatabase("locationfinder").getCollection("pointlocationfeed").insertOne(ar);			
		}else
			this.update(mClient, request, response);
		showAllDocuments(mClient);					
	}
	@Override
	public JSONArray retreive (MongoClient mClient, HttpServletRequest request, HttpServletResponse response){
		FindIterable<Document> itr =   mClient.getDatabase("locationfinder").
					getCollection("pointlocationfeed").find(eq("name_Id",this.mDataSource.getNameId()));
		JSONArray jAry = new JSONArray();
		Block<Document> lc = new Block<Document>() {
		    @Override		    
	        public void apply(final Document document) {
	            System.out.println("CRUD__RETREIVE result=>"+document.toJson()); 
	            /*jArray.put(document.toJson());*/
	            jAry.put(document);	            
		    }
		   };
		itr.forEach(lc);
		return jAry;	
	}
	@Override
	public void update(MongoClient mClient, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CRUD__UPDATE");
		Document ar = GeoDataConverter.dataToDoc(this.mDataSource);
		mClient.getDatabase("locationfinder").getCollection("pointlocationfeed").replaceOne 
			(new Document().append("name_Id", mDataSource.getNameId()),ar );		
		showAllDocuments(mClient);		
	}
	@Override
	public String remove(MongoClient mClient, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CRUD__REMOVE");
		showAllDocuments(mClient);
		return null;
	}

}
