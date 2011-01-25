package play.modules.ddsl;

import play.*;
import com.kjetland.ddsl.*;
import com.kjetland.ddsl.config.*;
import com.kjetland.ddsl.model.*;
import java.lang.*;
import java.net.*;


public class DdslPlugin extends PlayPlugin implements DdslConfig{
	
	
	private DdslClient client = null;
	
	@Override
	public void afterApplicationStart(){
		Logger.info("DDSL loading config from application.conf");
		this.client = new DdslClientImpl( this );
		
		ServiceId sid = getServiceId();
		if( sid == null ){
			//nothing to register - we're done.
			return ;
		}
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
		String port = getProp("http.port", null, true);
		String ip = getLocalIp();
		String url = "http://"+ip+":"+port + "/";
	}
	
	/**
	Resolve this computers IP
	*/
	private String getLocalIp(){
		return InetAddress.getLocalHost().getHostAddress();
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
			getProp("ddsl.serviceid.version", null, true),
			null);
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