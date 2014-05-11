/**
 * Copyright (c) 2011, 2012 Sentaca Communications Ltd.
 */
package com.sentaca.android.accordion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sentaca.android.accordion.utils.FontUtils;
import com.sentaca.android.accordion.widget.AccordionView;

public class AccordionWidgetDemoActivity extends Activity {
  private static final String TAG = "AccordionWidgetDemoActivity";
  String incidentList="",locationString="";
  int value=0;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final AccordionView v = (AccordionView) findViewById(R.id.accordion_view);

//    LinearLayout ll = (LinearLayout) v.findViewById(R.id.example_get_by_id);
//    TextView tv = new TextView(this);
//    tv.setText("Added in runtime...");
//    FontUtils.setCustomFont(tv, getAssets());
//    ll.addView(tv);
    
    Button submit=(Button)findViewById(R.id.submit);
    final RadioGroup radioPersonGroup = (RadioGroup) v.findViewById(R.id.people);
    final RadioGroup radioYesNo= (RadioGroup) v.findViewById(R.id.radioYesNo);
    
    final CheckBox eve=(CheckBox)v.findViewById(R.id.chkEve);
    final CheckBox voy=(CheckBox) v.findViewById(R.id.chkVoy);
    final CheckBox acid=(CheckBox ) v.findViewById(R.id.chkAcid);
    final CheckBox stalk=(CheckBox) v.findViewById(R.id.chkStalk);
    final CheckBox rape=(CheckBox) v.findViewById(R.id.chkRape);
    final CheckBox disrobe=(CheckBox) v.findViewById(R.id.chkDisrobing);
    
    final EditText otherIncident=(EditText)v.findViewById(R.id.otherIncident);
    final EditText location=(EditText)v.findViewById(R.id.location);
    final EditText timeEditText=(EditText)v.findViewById(R.id.time);
    final EditText dateEditText=(EditText)v.findViewById(R.id.date);
    final EditText additionalInfo=(EditText)v.findViewById(R.id.additionalInfo);
    final EditText fName=(EditText)v.findViewById(R.id.fName);
    final EditText phoneNumber=(EditText)v.findViewById(R.id.phoneNumber);
    final EditText Email=(EditText)v.findViewById(R.id.Email);
    
    
    
    
    submit.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click   
        	double locationLat = 0;
        	double locationLng = 0;
        	int flag=0;
           int personId=radioPersonGroup.getCheckedRadioButtonId();
           String person= (String) ((RadioButton) findViewById(personId)).getText();
           int yesNoId= radioYesNo.getCheckedRadioButtonId();
           String yesNo=(String) ((RadioButton) findViewById(yesNoId)).getText();
           
           if(eve.isChecked())
           {
        	   incidentList+="EVE_TEASING,";
        	   value=1;
           }
           if(voy.isChecked())
           {
        	   incidentList+="VOYEURISM,";
        	   value=1;
           }
           if(acid.isChecked())
           {
        	   incidentList+="ACID_VIOLENCE,";
        	   value=1;
           }
           if(stalk.isChecked())
           {
        	   incidentList+="STALKING,";
        	   value=1;
           }
           if(rape.isChecked())
           {
        	   incidentList+="RAPE,";
        	   value=1;
           }
           if(disrobe.isChecked())
           {
        	   incidentList+="DISROBING,";
        	   value=1;
           }
           
           if(value==0 && !otherIncident.getText().equals(""))
        	   incidentList+=otherIncident.getText()+",";
           if (incidentList.length() > 0 && incidentList.charAt(incidentList.length()-1)==',') {
           	incidentList = incidentList.substring(0, incidentList.length()-1);
             }
           
           locationString=location.getText().toString();
           try {
  		        String location = locationString;
  		        Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
  		        List<Address> addresses= gc.getFromLocationName(location, 1); // get the found Address Objects
  		        if(addresses.size()>0) {
  		        for(Address a : addresses){
  		            if(a.hasLatitude() && a.hasLongitude()){
  		               locationLat = a.getLatitude();
  		                locationLng = a.getLongitude();
  		            }  
  		        }  
  		    }
  		       else {
  		    	 flag=1;  
  		    	 Toast.makeText(getApplicationContext(), "Please enter correct Location", Toast.LENGTH_SHORT);  
  		       }
		 }
  		       catch (IOException e) {
  		    	Log.d("kanav", "location error");
  		         // handle the exception
  		    }
           String time1 = timeEditText.getText().toString();
           if(time1=="") {
        	   Toast.makeText(getApplicationContext(), "Please enter correct time", Toast.LENGTH_SHORT);  
   		      flag=1;
           }
           String date1 = dateEditText.getText().toString();
           if(date1=="") {
        	   Toast.makeText(getApplicationContext(), "Please enter correct Date", Toast.LENGTH_SHORT);  
   		       flag=1;
           }
           if(flag==0) {
        	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
          		Date date = null;
				try {
					date = formatter.parse(date1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          		date1 = (formatter.format(date)).toUpperCase(); 
           String firstName = fName.getText().toString();
           String email = Email.getText().toString();
           String number = phoneNumber.getText().toString();
           String additionalInformation = additionalInfo.getText().toString();
           JSONObject jObjectData = new JSONObject();
           try {
			jObjectData.put("person", person);
			 jObjectData.put("doYouKnow", yesNo);
	           jObjectData.put("incidentList", incidentList);
	           jObjectData.put("email", email);
	           jObjectData.put("location", locationString);
	           jObjectData.put("locationLat", locationLat);
	           jObjectData.put("locationLng", locationLng);
	           jObjectData.put("incidentDate", date1);
	           jObjectData.put("incidentTime", time1);
	           jObjectData.put("comments", additionalInformation);
	           jObjectData.put("firstName", fName);
	           jObjectData.put("number", number);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	String address = "http://54.186.110.31/submitReport";
           	HttpClient client = new DefaultHttpClient();
           	HttpPost post = new HttpPost(address);
           	StringEntity se = null;
			try {
				se = new StringEntity(jObjectData.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           	post.setEntity(se);
        	Log.d("kanav", se.toString());
        	//post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json"); 
           	//HttpResponse response = client.execute(post);
           	
           	ResponseHandler<String> responseHandler = new BasicResponseHandler();
			//Log.d("fer","also");
			String response = null;
			try {
				response = client.execute(post, responseHandler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           //	Log.d("rahul",response.toString());
           
           
           }
           
           
           
           
           
           
           
           
           
        }
    });
    
  }
}