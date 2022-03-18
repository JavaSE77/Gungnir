package edu.cs.ship.rs0699.gungnir;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConfigurationHandler {


  private static ConfigurationHandler singleton = null;
  //this needs to be a singleton

  private String configFileName = "config.txt";
  private Boolean verbose = true;
  private int portRangeLower = 8000;
  private int portRangeUpper = 8080;
  private Boolean URLincludesPort = true;
  private String URL = "localhost";
  private String ANGLETUTORIAL = "youtube.com";
  private String WEIGHTTUTORIAL = "youtube.com";
  private String NAMETUTORIAL = "youtube.com";
  
  // Static method
  // Static method to create instance of Singleton class
  public static ConfigurationHandler getInstance()
  {
      if (singleton == null)
        singleton = new ConfigurationHandler();
        
      return singleton;
  }
  
  /**
   * Reads the configuation from config.txt
   * fills in the values to be retreived from their getter methods
   * */
  public void readFile(String config) {
    this.configFileName = config;
    try {  
      //Open the config file
      FileInputStream inputStream=new FileInputStream(configFileName);       
      Scanner scanner=new Scanner(inputStream);
      while(scanner.hasNextLine()) {
          String line = scanner.nextLine();
          String[] values = line.split("=");
          
          
          if (values.length != 2) {
            if (verbose) System.out.println("We found a broken key in the configuration.");
            if (verbose && values.length >= 1) System.out.println("Key: " + values[0] + " value: " + values[1]);
            break;
          }
          
          if(values[0].equalsIgnoreCase("verbose")) {
            verbose = Boolean.parseBoolean(values[1].trim());
          }
          else if(values[0].equalsIgnoreCase("portRangeLower")) {
            portRangeLower = Integer.parseInt(values[1].trim());
          }
          else if(values[0].equalsIgnoreCase("portRangeUpper")) {
            portRangeUpper = Integer.parseInt(values[1].trim());
          }
          else if(values[0].equalsIgnoreCase("URLincludesPort")) {
            URLincludesPort = Boolean.parseBoolean(values[1].trim());
          }
          else if(values[0].equalsIgnoreCase("%URL%")) {
            URL = values[1];
          }
          else if(values[0].equalsIgnoreCase("%ANGLETUTORIAL%")) {
            ANGLETUTORIAL = values[1];
          }
          else if(values[0].equalsIgnoreCase("%WEIGHTTUTORIAL%")) {
            WEIGHTTUTORIAL = values[1];
          }
          else if(values[0].equalsIgnoreCase("%NAMETUTORIAL%")) {
            NAMETUTORIAL = values[1];
          } else if (verbose) {
            System.out.println("We found an unknown key in the configuration. Key: " + values[0] + " value: " + values[1]);
          }
          
      }  
        scanner.close(); 
      }  
        catch(IOException e) {  
            if (verbose) e.printStackTrace();  
            
            System.out.println("There was an error reading the config file. Please correct the file before continuing");
    }  

  }
  
  /**
   * Get the location of the config file
   * @return String filePath
   * */
  public String getConfiglocation() {
    return this.configFileName;
  }
  
  /**
   * boolean verbose
   * @return boolean value in config
   * */
  public boolean verbose() {
    return verbose;
  }
  
  /**
   * int port range lower
   * @return int value in config
   * */
  public int portRangeLower() {
    return portRangeLower;
  }
  
  /**
   * int port range upper
   * @return int value in config
   * */
  public int portRangeUpper() {
    return portRangeUpper;
  }
  
  /**
   * boolean if url contains a port number
   * This would be true with an A or an AAAA DNS record
   * This would be false with an SRV DNS record
   * @return boolean value in config
   * */
  public boolean URLincludesPort() {
    return URLincludesPort;
  }
  
  /**
   * String url
   * Could be an IP, or a DNS record
   * @return String value in config
   * */
  public String URL() {
    return URL;
  }
  
  /**
   * String ANGLETUTORIAL
   * Could be an IP, or a DNS record
   * @return String value in config
   * */
  public String ANGLETUTORIAL() {
    return ANGLETUTORIAL;
  }
  
  /**
   * String WEIGHTTUTORIAL
   * Could be an IP, or a DNS record
   * @return String value in config
   * */
  public String WEIGHTTUTORIAL() {
    return WEIGHTTUTORIAL;
  }
  
  /**
   * String NAMETUTORIAL
   * Could be an IP, or a DNS record
   * @return String value in config
   * */
  public String NAMETUTORIAL() {
    return NAMETUTORIAL;
  }
}
