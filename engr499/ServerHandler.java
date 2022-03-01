package edu.cs.ship.rs0699.engr499;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class ServerHandler {

  private int portRangeLower;
  private int portRangeUpper;
  private int boundPort;
  private String url;
  private static ServerHandler singleton = null;
  
  //this needs to be a singleton
  private ServerHandler() {
    // 
  }
  
  // Static method
  // Static method to create instance of Singleton class
  public static ServerHandler getInstance()
  {
      if (singleton == null)
        singleton = new ServerHandler();

      return singleton;
  }
  
  
  /**
   * int rangeUpper int rangeLower. Normal range is 8000-8080
   * @return HttpServer
   * */
  public HttpServer bindServer() {
    
    if(portRangeUpper == 0) {
      System.out.println("Error! Please include port range!");
      return null;
    }
    
    for (int i = portRangeLower; i <= portRangeUpper; i++) {
      try {
        HttpServer server = HttpServer.create(new InetSocketAddress(i), 0);
        System.out.println("Bound to port: " + i);
        boundPort = i;
        return server;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        System.out.println("Failed to bind to port: " + i);
      }
    } 
    
    
    return null;
  }
  
  public void setPortRange(int portRangeLower, int portRangeUpper) {

    this.portRangeLower = portRangeLower;
    this.portRangeUpper = portRangeUpper;
    
  }
  
  public void setURL(String url) {
    this.url = url;
  }
  
  public int getBoundPort() {
    return boundPort;
  }
  
  public String getURL() {
    return this.url;
  }

}
