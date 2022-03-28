package edu.cs.ship.rs0699.gungnir;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PageHandler implements HttpHandler {
  
  String pageName = null;
  
  public PageHandler(String pageName) {
    this.pageName = pageName;
  }
  

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    // TODO Auto-generated method stub
    int responseCode = 200;
    
    if(Main.verbose) System.out.println("returned response code: " + responseCode);
    
    HTMLfileReader reader = new HTMLfileReader();
    String response = reader.readFile("website/" + pageName);
    exchange.sendResponseHeaders(responseCode, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    System.out.println(exchange.getRequestURI());
    os.close();
  }

}
