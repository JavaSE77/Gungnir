package edu.cs.ship.rs0699.gungnir;


import com.sun.net.httpserver.HttpServer;

public class Main {

  public static boolean verbose = true;
  
  
  public static void main(String[] args) throws Exception {
    System.out.println("starting");
    
    int portRangeLower = 8000;    
    int portRangeUpper = 8080;
    ServerHandler serverHandler = ServerHandler.getInstance();
    
    //Remember, this must be configured on the same wifi network as the users of the machine. So if this is on ship secure,
    //so too must the user of the machine be. 
    //We can set another http server on not ship secure and link them
    
    System.out.println("trying to bind to port range " + portRangeLower + " to " + portRangeUpper);
    serverHandler.setPortRange(portRangeLower, portRangeUpper);
    serverHandler.setURL("localhost");
    HttpServer server = serverHandler.bindServer();
    if(server == null) {
      System.out.println("Unable to bind to ports within range. Server will close. "
          + "To resolve this issue, increase port range, or close other servers"
          + " if this is the only machine on the network, restart this machine and the network to resolve.");
      return;
    } else {
      System.out.println("HTTP is now online. To view to go: 127.0.0.1:" + serverHandler.getBoundPort());
    }
    server.createContext("/", new MainHandler());
    server.createContext("/input", new InputHandler());
    server.setExecutor(null); // creates a default executor
    server.start();
}
 

  
}
