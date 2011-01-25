package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
		String serverPort = Play.configuration.getProperty("http.port", "9000");
        renderText("Hello from server runing at port " + serverPort);
    }

}