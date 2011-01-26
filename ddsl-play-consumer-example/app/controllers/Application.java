package controllers;

import play.*;
import play.mvc.*;
import play.libs.WS;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
	
		String url = DDSL.getBestUrl( "PlayExampleServer", "1.0" );
	
		
		String serverMsg = WS.url( url ).get().getString();
	
        render(url, serverMsg);
    }


	/**
	controller that tries to resove noneexisting url - which triggers fallback solution.
	*/
	public static void fallbackTest() {
	
		String url = DDSL.getBestUrl( "PlayExampleServer", "1.1" );
        
		renderText("url: " + url);
    }

	

}