package com.example.submenu;

import java.io.InputStream;

import java.util.HashMap;


import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends FragmentActivity {
	
	GoogleMap mGoogleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		 
		
     int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

        	int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else
        
{ // Google Play Services are available
        	
	    	// Getting reference to the SupportMapFragment
	    	SupportMapFragment fragment = ( SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	    			
	    	// Getting Google Map
	    	mGoogleMap = fragment.getMap();
	    			
	    	// Enabling MyLocation in Google Map
	    	mGoogleMap.setMyLocationEnabled(true);
		
		
		// I am calling the track with trackid 533ecbfde4b09d7b34f80643
		//and since there are many measurement values included in it
		// for different co-ordinates, i am calling the measurement
		//with measurement id 533ecbfde4b09d7b34f80645
		
		 new fetchData().execute();
		 
}
		
	}

	
	
	class fetchData extends AsyncTask<String, String, Void>
	{
	 InputStream is = null ;
	 String result = "",response="";
	 
	 protected void onPreExecute() {
	    
	  }
	    @Override
		protected Void doInBackground(String... params) {
	    	
	  	  String url_select = "http://54.186.110.31/viewReport";
		  DefaultHttpClient client = new DefaultHttpClient();
	      HttpGet httpPost = new HttpGet(url_select);
	      Log.d("her","got");
		 
		    try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			Log.d("fer","also");
			response = client.execute(httpPost, responseHandler);
			Log.d("rahul",response);
			
			} catch (Exception e) {

			Log.e("log_tag", "Error in http connection "+e.toString());
			}
				return null;
	   }	
		
	protected void onPostExecute(Void v) {
		
			 try{
			 
			 JSONArray json = new JSONArray(response);
			 
			 for(int i=0;i<json.length();i++)
			 {
				 
				JSONObject object=(JSONObject) json.get(i);
				String latitude=object.getString("latitude");
				String longitude=object.getString("longitude");
				String categories=object.getString("categories");
				
				MarkerOptions markerOptions = new MarkerOptions();
	            
	            
	
	            // Getting latitude of the place
	            double lat = Double.parseDouble(latitude);	            
	            
	            // Getting longitude of the place
	            double lng = Double.parseDouble(longitude);
	            
	           
	            
	            LatLng latLng = new LatLng(lat, lng);
	            
	            // Setting the position for the marker
	            markerOptions.position(latLng);
	
	            // Setting the title for the marker. 
	            //This will be displayed on taping the marker
	            markerOptions.title(categories);	            
	
	            // Placing a marker on the touched position
	            mGoogleMap.addMarker(markerOptions);
	            Toast.makeText(getApplicationContext(), ""+json.length(), Toast.LENGTH_LONG).show();
	           
	           
				 
				 
			 }
			 //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			 //Log.d("rahul",response);
			// JSONArray subObj = json.getJSONArray("tags");
			 // stringList.clear();		  
			  
			// for(int i=0;i<subObj.length();i++)
			     //  stringList.add(subObj.getString(i));
			 
			// call(stringList);
			     
		 }
		 
			catch(JSONException e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	    }
	    
	 }
	           
	}
	
	

}