package com.subhojit.geoservlet;

import org.bson.Document;
import static java.util.Arrays.asList;

import java.util.List;

public class GeoDataConverter {
	public static Document dataToDoc(GeoDataSource data)/* throws JSONException*/{
		Document doc = new Document();
		if(!data.getNameId().isEmpty()){
			doc.put("name_Id", data.getNameId());
			doc.append("loc", new Document().append("type", "point").
									  append("coordinates",asList(data.getLongitude(),data.getLatitude() )));
			System.out.println("DATAtoDOC__CONVERTER=>"+doc.toString());	
		}            
		return doc;
	}	
	public static GeoDataSource DoctToData(Document doc) /*throws JSONException*/{
		GeoDataSource src = new GeoDataSource();
		src.setNameId((String)doc.get("name_Id"));
		List<Double> coord =  (List<Double>) ((Document)doc.get("loc")).get("coordinates");
		src.setLongitude(coord.get(0));
		src.setLatitude(coord.get(1));		
		return src;
	}
	public static void showData(Document document){
		GeoDataSource ds = new GeoDataSource();		
		ds = GeoDataConverter.DoctToData(document);	
		System.out.println("Name_ID : "+ds.getNameId()+" Longitude: "+ds.getLongitude()+" Latitude"+ds.getLatitude());
	}
}
