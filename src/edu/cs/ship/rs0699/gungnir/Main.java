package edu.cs.ship.rs0699.gungnir;


import javax.security.auth.login.Configuration;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.sun.net.httpserver.HttpServer;


public class Main {

  public static boolean verbose = true;
  
  
  public static void main(String[] args) throws Exception {
    System.out.println("starting");
    
    ConfigurationHandler config = ConfigurationHandler.getInstance();
    config.readFile("config.txt");
    verbose = config.verbose();
    
    int portRangeLower = config.portRangeLower();    
    int portRangeUpper = config.portRangeUpper();
    ServerHandler serverHandler = ServerHandler.getInstance();
    
    //Remember, this must be configured on the same wifi network as the users of the machine. So if this is on ship secure,
    //so too must the user of the machine be. 
    //We can set another http server on not ship secure and link them
    
    System.out.println("trying to bind to port range " + portRangeLower + " to " + portRangeUpper);
    serverHandler.setPortRange(portRangeLower, portRangeUpper);
    serverHandler.setURL(config.URL());
    HttpServer server = serverHandler.bindServer();
    if(server == null) {
      System.out.println("Unable to bind to ports within range. Server will close. "
          + "To resolve this issue, increase port range, or close other servers"
          + " if this is the only machine on the network, restart this machine and the network to resolve.");
      return;
    } else {
      if(config.URLincludesPort()) {
      System.out.println("HTTP is now online. To view to go: "+ serverHandler.getURL()+":" + serverHandler.getBoundPort());
      } else {
        System.out.println("HTTP is now online. To view to go: "+ serverHandler.getURL());
      }
    }
    server.createContext("/", new MainHandler());
    server.createContext("/input", new InputHandler());
    server.createContext("/About", new PageHandler("About.html"));
    server.createContext("/results", new PageHandler("Results.html"));
    server.setExecutor(null); // creates a default executor
    server.start();
    
    setupSensorArray();
    
}
  
 public static void setupSensorArray() {
   
   //TODO move away from test data and handle the issue of having multiple CSV records
  
   CSVhandler CSV = new CSVhandler("testData.csv");
   
   System.out.println("Starting sensor array");
   // create gpio controller
   final GpioController gpio = GpioFactory.getInstance();
   
   SensorHandler sensorA = new SensorHandler(gpio, RaspiPin.GPIO_25);
   GpioPinDigitalInput GPIOA = sensorA.setupPin();   
   
   SensorHandler sensorB = new SensorHandler(gpio, RaspiPin.GPIO_24);
   GpioPinDigitalInput GPIOB = sensorB.setupPin();     
   
   SensorHandler sensorC = new SensorHandler(gpio, RaspiPin.GPIO_23);
   GpioPinDigitalInput GPIOC = sensorC.setupPin();

   SensorEventHandler sensorEventHandlerA = new SensorEventHandler();
   sensorEventHandlerA.registerListener(GPIOA, CSV, true, false);
   SensorEventHandler sensorEventHandlerB = new SensorEventHandler();
   sensorEventHandlerB.registerListener(GPIOB, CSV, true, false);
   SensorEventHandler sensorEventHandlerC = new SensorEventHandler();
   sensorEventHandlerC.registerListener(GPIOC, CSV, true, true);
   
   CSV.initializeSensorRecords(sensorEventHandlerA,sensorEventHandlerB,sensorEventHandlerC);
   
 }

  
}
