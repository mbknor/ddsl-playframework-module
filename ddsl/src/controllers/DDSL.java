package controllers;

import com.kjetland.ddsl.*;
import com.kjetland.ddsl.model.*;
import play.modules.ddsl.*;
import play.*;

public class DDSL{
	
	public static DdslClient getClient(){
		return DdslPlugin.client;
	}
	
	public static String getBestUrl( String serviceName, String serviceVersion){
		return getBestUrl(serviceName, serviceVersion, "http");
	}
	
	public static String getBestUrl( String serviceName, String serviceVersion, String serviceType ){
		//load config
		//ddsl.clientid.name=PlayExampleClient
		//ddsl.clientid.version=1.0
		ClientId clientId = new ClientId( DdslPlugin.ddslEnvironment, 
			getProp("ddsl.clientid.name", "play-client", false), 
			getProp("ddsl.clientid.version", "1.0", false), 
			null);
			
		ServiceId serviceId = new ServiceId(DdslPlugin.ddslEnvironment, serviceType, serviceName, serviceVersion);
		ServiceRequest sr = new ServiceRequest( serviceId, clientId);
		return getClient().getBestServiceLocation(sr).url();
	}
	
	private static String getProp(String name, String defaultValue, boolean mustHave){
		String v = Play.configuration.getProperty(name, defaultValue);
		if( mustHave && v == null ){
			throw new RuntimeException("Needed config property not found in application.conf: " + name);
		}
		return v;
	}
	
}