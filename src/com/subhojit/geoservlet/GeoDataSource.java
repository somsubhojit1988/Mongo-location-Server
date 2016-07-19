package com.subhojit.geoservlet;

public class GeoDataSource {
		
		private double lon,lat;
		private String nameId;
		public GeoDataSource() {
			this.lon=0;
			this.lat=0;
			this.nameId="";
		}
		public void setNameId(String nameId){this.nameId = nameId;}
		public void setLongitude(double lon){this.lon = lon;}
		public void setLatitude(double lat){this.lat = lat;}
		public Double getLatitude(){return new Double(this.lat); }
		public Double getLongitude(){return new Double(this.lon);}
		public String getNameId(){return this.nameId;}
	

}
