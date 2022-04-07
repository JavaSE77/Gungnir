package edu.cs.ship.rs0699.gungnir;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CSVhandler {
  
  private String fileName;
  private boolean verbose = ConfigurationHandler.getInstance().verbose();

  private SensorEventHandler sensorEventHandlerA;
  private SensorEventHandler sensorEventHandlerB;
  private SensorEventHandler sensorEventHandlerC;
  private UserSettings settings = UserSettings.getInstance();
  
  public CSVhandler(String fileName) {
      this.fileName = fileName;   
  }
  
  /**
   * Read the entire CSV file as a string
   * */
  public String readFile() {
    
    StringBuilder sb = new StringBuilder();
    
    try {  
      //Open the config file
      FileInputStream inputStream=new FileInputStream(fileName);       
      Scanner scanner=new Scanner(inputStream);
      while(scanner.hasNextLine()) {
          String line = scanner.nextLine();
          sb.append(line + "\n");
      }
    
      scanner.close(); 
    }  
      catch(IOException e) {  
          if (verbose) e.printStackTrace();  
          
          System.out.println("There was an error reading the CSV file. Please correct the file before continuing");
  } 
  return sb.toString();
}

  
  /**
   * Gets the line from the CSV file at the requested index
   * @param int line number
   * @return String in the CSV file at index
   * */
  public String getLine(int lineNumber) {
    String line = null;
    
    try {  
      //Open the config file
      FileInputStream inputStream=new FileInputStream(fileName);       
      Scanner scanner=new Scanner(inputStream);
      int i = 0;
      while(scanner.hasNextLine()) {
        if(i == lineNumber) {
          line = scanner.nextLine();
          System.out.println(line);
          break;
        }
          i++;
      }
    
      scanner.close(); 
    }  
      catch(IOException e) {  
          if (verbose) e.printStackTrace();  
          
          System.out.println("There was an error reading the CSV file. Please correct the file before continuing");
  } 
  return line;
}
  
  
  /**
   * Writes a line to the end of the CSV file. No error checking performed
   * @param String line - line to append to CSV
   * */
  public void appendLineNoErrorChecking(String line) {
    try {
    BufferedWriter outputStream = new BufferedWriter(new FileWriter(fileName, true));

      outputStream.append("\n" + line);

      outputStream.close();
    } catch (IOException e) {
      if (verbose) e.printStackTrace();  
      
      System.out.println("There was an error writing to the CSV file. Please correct the file before continuing");
    }

  }
  
  
  /**
   * Writes a line to the end of the CSV file. fills in remaining fields from user settingsa
   * */
  public void addRecord() {
    //CSV header looks like:
    //User  Distance  Weight  Speed Acceleration  Force sensorA sensorB SensorC date
    String user = settings.getUser();
    String distance = null;
    String weight = settings.getUser();
    String angle = "" + settings.getAngle();
    String speed = "-1";
    String acceleration = "-1";
    String force = "-1";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
    Date date = new Date();
    String dateHumanReadable = sdf.format(date); 
    Long sensorAtime = (long) 0; 
    Long sensorBtime = (long) 0;
    Long sensorCtime = (long) 0;
    if (sensorEventHandlerA != null) sensorAtime = sensorEventHandlerA.sensorTime;
    if (sensorEventHandlerB != null) sensorBtime = sensorEventHandlerB.sensorTime;
    if (sensorEventHandlerC != null) sensorCtime = sensorEventHandlerC.sensorTime;

    StringBuilder sb = new StringBuilder();
    sb.append(user);
    sb.append(",");
    sb.append(distance);
    sb.append(",");
    sb.append(weight);
    sb.append(",");
    sb.append(angle);
    sb.append(",");
    sb.append(speed);
    sb.append(",");
    sb.append(weight);
    sb.append(",");
    sb.append(acceleration);
    sb.append(",");
    sb.append(force);
    sb.append(",");
    sb.append(sensorAtime);
    sb.append(",");
    sb.append(sensorBtime);
    sb.append(",");
    sb.append(sensorCtime);
    sb.append(",");
    sb.append(dateHumanReadable);
    
    appendLineNoErrorChecking(sb.toString());
    
    if(Main.verbose) System.out.println("Added record to the CSV");
    

  }
  
  /**
   * Gives the CSV handler access to the sensor records
   * @param SensorEventHandler sensorEventHandlerA,
      SensorEventHandler sensorEventHandlerB, SensorEventHandler sensorEventHandlerC
   * */
  public void initializeSensorRecords(SensorEventHandler sensorEventHandlerA,
      SensorEventHandler sensorEventHandlerB, SensorEventHandler sensorEventHandlerC) {
    this.sensorEventHandlerA = sensorEventHandlerA;
    this.sensorEventHandlerB = sensorEventHandlerB;
    this.sensorEventHandlerC = sensorEventHandlerC;
  }
  
}