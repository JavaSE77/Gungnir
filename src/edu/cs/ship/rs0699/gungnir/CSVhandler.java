package edu.cs.ship.rs0699.gungnir;

import java.io.BufferedWriter;
import java.io.File;
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
   * Writes a line to the end of the CSV file. fills in remaining fields from user settings
   * */
  public void addRecord() {
    
    File currentRecordFile = new File(fileName);
    long fileSize = currentRecordFile.length();
    if(fileSize > 1000000) {
      fileName = pickCSVFile();
      appendLineNoErrorChecking("User,Distance,Weight,Angle,Speed,Acceleration,Force,sensorA,sensorB,SensorC,date");
    }
    
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
  
  public String pickCSVFile() {
    String folderName = "/records";

    File csvFolder = new File(folderName);
    if (! csvFolder.exists()){
      csvFolder.mkdirs();
    }
   
    
    if(csvFolder.listFiles() == null) {
      File yourFile = new File("records-1.csv");
      try {
       yourFile.createNewFile();
     } catch (IOException e) {
       // TODO Auto-generated catch block
       if(verbose) e.printStackTrace();
     }
      return "records-1.csv";
    } else {
      File[] listOfFiles = csvFolder.listFiles();
      //if there are files in the directory, get them. 
      int max = 1;
      for(int i = 1; i < listOfFiles.length; i++) {
        String[] splitFileName = listOfFiles[i].getName().split("-");
        if(splitFileName.length > 1) {
          int fileNum = Integer.parseInt(splitFileName[1].split(".")[0]);
          if (fileNum > max) {
            max = fileNum;
          }
        }
        
      }
      File currentRecordFile = new File("records-" + max +".csv");
      long fileSize = currentRecordFile.length();
      if(fileSize < 1000000) {
        return "records-" + max +".csv";
      } else {
        File nextRecordFile = new File("records-" + max+1 +".csv");
        try {
         nextRecordFile.createNewFile();
       } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
       }
        return "records-" + max+1 +".csv";
      }
      
    }
    
  }

  
}