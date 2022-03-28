package edu.cs.ship.rs0699.gungnir;

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

    HTMLfileReader reader = new HTMLfileReader();
    String response = reader.readFile("website/Home.html");
    exchange.sendResponseHeaders(200, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    os.close();
    
  }

}
