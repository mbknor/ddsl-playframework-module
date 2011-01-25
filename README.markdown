Play Framework module for DDSL
----------------

Plug-and-play cluster for Play


[For info about DDSL](https://github.com/mbknor/ddsl)

The play framework module **DDSL** brings DDSL to play..

With DDSL you can create plug-and-play clusters with your play app both as server or client.

This is how easy it is to locate a service in your backend dynamic cluster:

	String url = DDSL.getBestUrl( "PlayExampleServer", "1.0" );
	

This is how easy it is for your Play app to be part of the backend dynamic cluster:

	module.ddsl=../ddsl
	#Set to true if you want to broadcast this play to DDSL
	ddsl.broadcastservice=true
	#Specify serviceId if ddsl.broadcastservice=true
	ddsl.environment=test
	ddsl.serviceid.type=http
	ddsl.serviceid.name=PlayExampleServer
	ddsl.serviceid.version=1.0
	
	
If you need more server power, just start multiple play servers on new hardware - DDSL will automatically link it all together..

Try it
----------------

 This example uses two play servers as servers/producers and one play app as client/consumer

 * [For info about DDSL](https://github.com/mbknor/ddsl)
 * Download [ZooKeeper](https://hadoop.apache.org/zookeeper/) and start it with defaults
 * Get DDSL play module from github:
	git clone git@github.com:mbknor/ddsl-playframework-module.git

Start the first server
----------------------
 * cd into ddsl-playframework-module/ddsl-play-producer-example/
 * start it:
	~/tools/play-1.1.1/play run

start the consumer
----------------------
 * start a new console/terminal
 * cd into 

	ddsl-playframework-module/ddsl-play-consumer-example/

 * start it:
 * (Assuming *~/tools/play-1.1.1/play* is your valid path to play)

	~/tools/play-1.1.1/play run

Go to [http://localhost:9000/](http://localhost:9000/) with your browser.
You can se that the consumer has "found" the server.

Then start another server
----------------------

(This is just a copy of the first one with different http-port)

 * start a new console/terminal
 * cd into 

	ddsl-playframework-module/ddsl-play-producer-example-2nd-server/

 * start it:

	~/tools/play-1.1.1/play run
	
	
Go back to [http://localhost:9000/](http://localhost:9000/) with your browser.
If you hit refresh several times you can se that you get response from both servers.

 * It is not round-robin but round-random Loadbalancing :)

 * If you only see one server, just keep retrying

 * You can add more servers if you like.

 * If you take one down, you will get some errors but after a couple of seconds The client will stop asking the server who went down.

 * The client is configured to cache each url for 1 second.




	
