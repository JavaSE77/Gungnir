package edu.cs.ship.rs0699.gungnir;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class InputHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {

 
    //Design idea, three pages. One page that is the main page, gives links to the creators, links to the other pages. Shows settings
    //First page is to input the settings, weight of the projectile, etc.
    //second page is to display the output of the last run. Refreshes every few seconds.
    
    
    int responseCode = handleInput(exchange.getRequestURI().toString());
    
    if(Main.verbose) System.out.println("returned response code: " + responseCode);
    
    HTMLfileReader reader = new HTMLfileReader();
    String response = reader.readFile("inputPage.txt");
    exchange.sendResponseHeaders(responseCode, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    System.out.println(exchange.getRequestURI());
    os.close();
    
  }
  
  public int handleInput(String URI) {
    if(Main.verbose) System.out.println(URI);
    //the way HTML uris work, every other element of this string will have a '/' deliminator. So we need to check every other 
    String url[] = URI.split("/");
    
    if(Main.verbose) System.out.println(url.length);
    
    try {
    //if this contains &, then we know it is the input string, lets edit that string
    if(url.length >= 3 && url[2].contains("&")) {
      String angle = url[2].split("&")[0];
      String weight = url[2].split("&")[1];
      //then we know the machine as set the angle
      if(Main.verbose) System.out.println("String angle " + angle );
      UserSettings.getInstance().setAngle(Double.parseDouble(angle.split("=")[1]));
      UserSettings.getInstance().setWeight(Double.parseDouble(weight.split("=")[1]));
      if(Main.verbose) System.out.println(UserSettings.getInstance().getAngle());
      //return response code 200 (success)
      return 200; 
    }
    } catch(Exception e) {
      if(Main.verbose) e.printStackTrace();
      return 400;
    }
    
    //return response code 400. That means there was an error;
    return 400;
  }
  

  
}
