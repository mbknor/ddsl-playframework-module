package play.modules.ddsl;

import play.*;
import play.server.*;
import com.kjetland.ddsl.*;
import com.kjetland.ddsl.config.*;
import com.kjetland.ddsl.model.*;
import java.lang.*;
import java.net.*;
import org.joda.time.DateTime;


public class DdslPlugin extends PlayPlugin implements DdslConfig{
	
	
	private DdslClient client = null;
	
	@Override
	public void afterApplicationStart(){
		Logger.info("DDSL loading config from application.conf");
		client = new DdslClientImpl( this );
		
		ServiceId sid = getServiceId();
		if( sid == null ){
			//nothing to register - we're done.
			return ;
		}
		
		ServiceLocation sl = getServiceLocation();
		
		client.serviceUp( new Service(sid, sl));
	}
	
	@Override
	public void onApplicationStop() {
		//must terminate our client to make sure any registered services go offline
		if( client != null ){
			client.disconnect();
			client = null;
		}
    }


	/**
	Must resolve our serviceLocation
	*/
	private ServiceLocation getServiceLocation(){
		String port = getProp("http.port", "9000", false);
		String ip = getLocalIp();
		//TODO: does not work when mounted on context other than / - ie as war in tomcat etc..
		String url = "http://"+ip+":"+port + "/";
		
		return new ServiceLocation(url, "", 
			Double.parseDouble(getProp("ddsl.locationquality", "0.0", false)),
			new DateTime(),
			null);
	}
	
	/**
	Resolve this computers IP
	*/
	private String getLocalIp(){
		try{
			return InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	Tries to load serviceId from config - if pressent, register it
	*/
	private ServiceId getServiceId(){
		//are we broadcasting?
		if( !"true".equalsIgnoreCase( Play.configuration.getProperty("ddsl.broadcastservice", "false") )){
			//not broadcasting
			return null;
		}
		//we are broadcasting - needs serviceId config
		//ddsl.serviceid.environment=test
		//ddsl.serviceid.type=http
		//ddsl.serviceid.name=PlayExampleServer
		//ddsl.serviceid.version=1.0
		return new ServiceId( getProp("ddsl.serviceid.environment", null, true),
			getProp("ddsl.serviceid.type", null, true),
			getProp("ddsl.serviceid.name", null, true),
			getProp("ddsl.serviceid.version", null, true));
	}
	
	private String getProp(String name, String defaultValue, boolean mustHave){
		String v = Play.configuration.getProperty(name, defaultValue);
		if( mustHave && v == null ){
			throw new RuntimeException("Needed config property not found in application.conf: " + name);
		}
		return v;
	}
	
	
	@Override
	public String hosts(){
		String zkHostList = Play.configuration.getProperty("ddsl.zkhostslist", "localhost:2181");
		Logger.info("DDSL (application.conf) using ddsl.zkhostslist=" + zkHostList);
		return zkHostList;
	}
	
	@Override
	public DdslUrls getStaticUrls (ServiceId sid ){
		throw new RuntimeException("not impl yet");
	}
	
	
}