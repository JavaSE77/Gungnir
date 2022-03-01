package edu.cs.ship.rs0699.engr499;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MainHandler implements HttpHandler {
  

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    // TODO Auto-generated method stub

    
    //Design idea, three pages. One page that is the main page, gives links to the creators, links to the other pages. Shows settings
    //First page is to input the settings, weight of the projectile, etc.
    //second page is to display the output of the last run. Refreshes every few seconds.
//    String response = "<!DOCTYPE html>\n" + 
//        "<html>\n" + 
//        "<head>\n" + 
//        "<!-- HTML Codes by Quackit.com -->\n" + 
//        "<title>\n" + 
//        "ENGR 499 Project page</title>\n" + 
//        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
//        "<style>\n" + 
//        "body {background-color:#ffffff;background-image:url(https://www.ship.edu/globalassets/engineering/faculty/dias.jpg);background-repeat:no-repeat;background-position:bottom center;background-attachment:fixed;}\n" + 
//        "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}\n" + 
//        "p {font-family:Helvetica, sans-serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}\n" + 
//        "</style>\n" + 
//        "</head>\n" + 
//        "<body>\n" + 
//        "<h1>Hello Khiem</h1>\n" + 
//        "<p>This page can output data from the sensors.</p>\n" + 
//        "</body>\n" + 
//        "</html>\n" + 
//        "";
    HTMLfileReader reader = new HTMLfileReader();
    String response = reader.readFile("default.txt");
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
    
  }

}
