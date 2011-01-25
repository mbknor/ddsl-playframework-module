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

}