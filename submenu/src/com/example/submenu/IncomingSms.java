package com.example.submenu;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.google.gson.Gson;
import android.R.string;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {
    
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    public String makeInstructionString (String senderNum) {
    	String instruction = "Q1)Who is reporting the incident?\n"+
    						"1 Survivor\n"+
    						"2 Friend of Survivor\n"+
    						"3 Relative of Survivor\n"+
    						"4 Stranger\n"+
    						"Q2)Do you know the assailant?\n"+
    						"1 Yes\n"+
    						"2 No\n"+
    						"Q3)What incident would you like to report?\n"+
    						"1 Eve Teasing\n"+
    						"2 Voyeurism\n"+
    						"3 Acid Violence\n"+
    						"4 Stalking\n"+
    						"5 Rape\n"+
    						"6 Disrobing\n"+
    						"7 Domestic Violence\n"+
			    			"8 Marital Rape\n"+
			    			"Q4)Location(without space, you can use comma)\n"+
			    			"Q5)Date YYYY-MM-DD\n"+
			    	            "Sample:\n"+
    			"ZARIYA REPORT 2 1 2 Delhi 04-06-2014";
    	return instruction;
    }
    public void onReceive(Context context, Intent intent) {
     
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
 
        try {
             
            if (bundle != null) {
                 
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                 
                for (int i = 0; i < pdusObj.length; i++) {
                     
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    String[] messageBodyArray = new String[15];
                    messageBodyArray = message.split(" ");
                    Log.d("divided message", messageBodyArray[0]);
                    if ( messageBodyArray[0].equals("ZARIYA") && messageBodyArray[1].equals("REPORT") && messageBodyArray.length==2 ) {
                    	String replyMessage =  makeInstructionString(senderNum);
                    	int duration = Toast.LENGTH_LONG;
                    	Toast toast = Toast.makeText(context,"senderNum: "+ senderNum + ", message: " + message, duration);
                        toast.show();
                        
                        sms.sendMultipartTextMessage(senderNum, null, sms.divideMessage(replyMessage), null, null);
                    	}
                     else if(messageBodyArray[0].equals("ZARIYA") && messageBodyArray[1].equals("REPORT") && messageBodyArray.length>2 && messageBodyArray[2].equals("DETAIL") ){
                    	 String replyMessage = new String();
                    	 VictimClass victimDataObject = new VictimClass();
                    	 victimDataObject.number = senderNum;
                    	 int flag = 0;
                		 if( Integer.parseInt(messageBodyArray[3]) >=1 && Integer.parseInt(messageBodyArray[3])<=4 && flag==0) {
                    		switch (Integer.parseInt(messageBodyArray[3])) {
                             case KeyValuePairReporterType.SURVIVOR:
                                    victimDataObject.person = "SURVIVOR";
                                     break;
                             case KeyValuePairReporterType.FRIEND_OF_SURVVOR:
                            	 	 victimDataObject.person = "FRIEND_OF_SURVIVOR";
                                 	 break;
                             case KeyValuePairReporterType.RELATIVE_OF_SURVIVOR:
                            		 victimDataObject.person ="RELATIVE_OF_SURVIVOR";
                                     break;
                             case KeyValuePairReporterType.STRANGER:
                            		 victimDataObject.person ="NOT_RELATED";
                            		 break;
                    		 }

                   	 } else {
                    		 flag=1;
                    		 replyMessage = "Reporting Incident Value is between 1-4.Please reply again.";
                   	 }
                		
                	 if( Integer.parseInt(messageBodyArray[4]) >=1 && Integer.parseInt(messageBodyArray[4])<=2 && flag==0) {
                    		 switch (Integer.parseInt(messageBodyArray[4])) {
                             case KeyValuePairReporterType.dontKnowTheAssailant:
                                    victimDataObject.doYouKnow = "N";
                                     break;
                            case KeyValuePairReporterType.KnowTheAssailant:
                            		 victimDataObject.doYouKnow ="Y";
                                     break;
                           }

                   	 } else {
                    		 flag=1;
                    		 replyMessage = "Do You Know The Assailant.Please reply again.";
                   	 }
                String[] incidentList = new String[15];
                incidentList = messageBodyArray[5].split(",");
                for( int iterator=0;iterator<incidentList.length; iterator++) {
                	if( Integer.parseInt(incidentList[iterator]) >=1 && Integer.parseInt(incidentList[iterator])<=8 && flag==0) {
               		 switch (Integer.parseInt(incidentList[iterator])) {
                       case KeyValuePairReporterType.EVE_TEASING:
                               victimDataObject.incidentList  += "EVE_TEASING,";
                                break;
                       case KeyValuePairReporterType.VOYEURISM:
                       		 victimDataObject.incidentList +="VOYEURISM,";
                                break;
                       case KeyValuePairReporterType.ACID_VIOLENCE:
                     		 victimDataObject.incidentList +="ACID_VIOLENCE,";
                              break;
                       case KeyValuePairReporterType.STALKING:
                     		 victimDataObject.incidentList +="STALKING,";
                              break;
                       case KeyValuePairReporterType.RAPE:
                     		 victimDataObject.incidentList +="RAPE,";
                              break;
                       case KeyValuePairReporterType.DISROBING:
                     		 victimDataObject.incidentList +="DISROBING,";
                              break;
                       case KeyValuePairReporterType.DOMESTIC_VIOLENCE:
                     		 victimDataObject.incidentList +="DOMESTIC_VIOLENCE,";
                              break;
                       case KeyValuePairReporterType.MARITAL_RAPE:
                     		 victimDataObject.incidentList +="MARITAL_RAPE,";
                              break;
                   
                      }

              	 } else {
               		 flag=1;
               		 replyMessage = "Please enter valid incidence type.Please reply again.";
               		 break;
              	 }
               }
                //Log.d("kanav", "error");
     		      
                if (victimDataObject.incidentList.length() > 0 && victimDataObject.incidentList.charAt(victimDataObject.incidentList.length()-1)==',') {
                	victimDataObject.incidentList = victimDataObject.incidentList.substring(0, victimDataObject.incidentList.length()-1);
                  }
	           	 if(  flag==0) {
	           		 victimDataObject.location = messageBodyArray[6];
	           	 }	
	      		 try {
	           		        String location = victimDataObject.location;
	           		        Geocoder gc = new Geocoder(context, Locale.getDefault());
	           		        Log.d("kanav", victimDataObject.location);
	           		     	
	           		       List<Address> addresses= gc.getFromLocationName(location, 1); // get the found Address Objects
	           		       if(addresses.size()>0) {
	           		       for(Address a : addresses){
	           		            if(a.hasLatitude() && a.hasLongitude()){
	           		                victimDataObject.locationLat = a.getLatitude();
	           		                victimDataObject.locationLng = a.getLongitude();
	           		            }  
	           		        }  
	           		    }
	           		       else {
	           		    	   replyMessage = "please give correct location.";
	           		    	   flag=1;
	           		       }
	      		 }
	           		       catch (IOException e) {
	           		//    	Log.d("kanav", "plpllp");
	           		         // handle the exception
	           		    }
	           		
	         	Log.d("kanav", "plpllp");
           		
	           	  
	           	 if(  flag==0) {
	           		   
	           		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
	           		Date date = formatter.parse((messageBodyArray[7]));
	           		victimDataObject.date = (formatter.format(date)).toUpperCase();
	           	 }
	           	if(flag==0) {
	           	replyMessage = "Thank you for reporting.We will revert back to you shortly.";
	           	}
	           	sms.sendMultipartTextMessage(senderNum, null, sms.divideMessage(replyMessage), null, null);
	           	Gson victimJson = new Gson();
	           	String address = "http://54.186.110.31/submitReport";
	           	HttpClient client = new DefaultHttpClient();
	           	HttpPost post = new HttpPost(address);
	           	StringEntity se = new StringEntity(victimJson.toJson(victimDataObject).toString());
	           	post.setEntity(se);
	        	Log.d("kanav", se.toString());
	        	//post.setHeader("Accept", "application/json");
	            post.setHeader("Content-type", "application/json"); 
	           	//HttpResponse response = client.execute(post);
	           	
	           	ResponseHandler<String> responseHandler = new BasicResponseHandler();
				//Log.d("fer","also");
				String response = client.execute(post, responseHandler);
	           	Log.d("rahul",response.toString());

                    }
                } // end for loop
             } // bundle is null
 
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
             
        }
    }    
}
