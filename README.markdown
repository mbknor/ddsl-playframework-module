Play Framework module for DDSL
----------------

[For info about DDSL](https://github.com/mbknor/ddsl)

The play framework module **DDSL** brings DDSL to play..

With DDSL you can create plug-and-play clusters with your play app both as server or client.

This is how easy it is to locate a service in your backend dynamic cluster:

	String url = DDSL.getBestUrl( "PlayExampleServer", "1.0" );
	

This is how easy it is for your PLay app to be part of the backend dynamic cluster:

	module.ddsl=../ddsl
	#Set to true if you want to broadcast this play to DDSL
	ddsl.broadcastservice=true
	#Specify serviceId if ddsl.broadcastservice=true
	ddsl.environment=test
	ddsl.serviceid.type=http
	ddsl.serviceid.name=PlayExampleServer
	ddsl.serviceid.version=1.0
	


	
