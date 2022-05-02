package edu.cs.ship.rs0699.gungnir;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CSV2WebOutput implements HttpHandler {

  CSVhandler csv;
  
  public CSV2WebOutput(CSVhandler csv) {
    // TODO Auto-generated constructor stub
    this.csv = csv;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    // TODO Auto-generated method stub
    int responseCode = 200;
    
    if(Main.verbose) System.out.println("returned response code: " + responseCode);
    
    
    String response = csv.readFile();
    exchange.sendResponseHeaders(responseCode, response.length());
    OutputStream os = exchange.getResponseBody();
    os.write(response.getBytes());
    System.out.println(exchange.getRequestURI());
    os.close();
  }


}
