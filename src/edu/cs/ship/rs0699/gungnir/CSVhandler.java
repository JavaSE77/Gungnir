package edu.cs.ship.rs0699.gungnir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CSVhandler {
  
  private String fileName;
  private boolean verbose = ConfigurationHandler.getInstance().verbose();

  private SensorEventHandler sensorEventHandlerA;
  private SensorEventHandler sensorEventHandlerB;
  private SensorEventHandler sensorEventHandlerC;
  private UserSettings settings = UserSettings.getInstance();
  private Calculations calculator = Calculations.getInstance();
  private double distanceBetweenSensors = Main.distanceBetweenSensors;
  
  public CSVhandler(String fileName) {
      this.fileName = fileName;   
  }
  
  /**
   * Read the entire CSV file as a string
   * */
  public String readFile() {
    //just on the off case the file has changed between reads
    fileName = pickCSVFile();
    
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
  
  public String getLastRecord() {
    String line = null;
    //just on the off case the file has changed between reads
    fileName = pickCSVFile();
    
    try {  
      //Open the csv file
      FileInputStream inputStream=new FileInputStream(fileName);       
      Scanner scanner=new Scanner(inputStream);
      while(scanner.hasNextLine()) {
          line = scanner.nextLine();
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
      
      File file = new File(fileName); 
      if(!file.exists()) {
        //file.getParentFile().mkdirs(); // Will create parent directories if not exists
        file.createNewFile();
        appendLineNoErrorChecking("User,Distance,Weight,Angle,Speed,sensorA,sensorB,SensorC,date");
        appendLineNoErrorChecking("Test,100,5,45,100,0,0,0,4/1/22");
      }
      
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
    
    fileName = pickCSVFile();

    long sensorAtime = (long) 0; 
    long sensorBtime = (long) 0;
    long sensorCtime = (long) 0;
    //make sure all the sensors are connected
    if  ((sensorEventHandlerA != null) && (sensorEventHandlerB != null) && (sensorEventHandlerC != null)) {
     
        sensorAtime = sensorEventHandlerA.sensorTime;
        sensorBtime = sensorEventHandlerB.sensorTime;
        sensorCtime = sensorEventHandlerC.sensorTime;
        
        
      
      
    }
    
    
    
    //CSV header looks like:
    //User  Distance  Weight  Speed Acceleration  Force sensorA sensorB SensorC date
    String user = settings.getUser();
    //make sure to set this to the actual distance of the sensors
    double weight = settings.getWeight();
    double angle = settings.getAngle();
    long[] sensorReadings = {sensorAtime,sensorBtime,sensorCtime};
    double speed = Math.round((calculator.getSpeed(sensorReadings, distanceBetweenSensors) * 100.0)) / 100.0;
    //double acceleration =  Math.round((calculator.getAcceleration(sensorReadings, distanceBetweenSensors) * 100.0)) / 100.0;
    //double force = Math.round(calculator.getForce(acceleration, weight, 32.0) * 100.0) / 100.0;
    double distance = Math.round((calculator.getExpectedDistance(speed, 32.0, angle) * 100.0)) / 100.0;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
    Date date = new Date();
    String dateHumanReadable = sdf.format(date); 
    //Before we add a line to the file, lets make sure the sensors aren't reading a negative
    //value. This is the case when the slider is returning to be thrown again
    

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
//    sb.append(",");
//    sb.append(acceleration);
//    sb.append(",");
//    sb.append(force);
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
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    String folderName = s +"/records/";
    
   File csvFolder = new File(folderName);

   
   File[] listOfFiles = csvFolder.listFiles();
   //Make sure to check if the list of files is null. If it is, then we cannot
   //get the length
    if(listOfFiles == null || listOfFiles.length == 0) {
      return folderName + "records-1.csv";
    } else {
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

      Path filePath = Paths.get(folderName + "records-" + max +".csv");
      FileChannel fileChannel;
      long fileSize = 0;
      try {
        fileChannel = FileChannel.open(filePath);
        fileSize = fileChannel.size();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println("file size: " + fileSize);
      if(fileSize < 1000000) {
        return folderName +"records-" + max +".csv";
      } else {
        int newMax = max+1;
        return folderName +"records-" + newMax +".csv";
      }
      
    }
    
  }
  
  public String getFileName() {
    return fileName;
  }

  
  /**
   * https://stackoverflow.com/questions/3961087/zipping-a-folder-which-contains-subfolders
   * */
  public void addDirToZipArchive(File fileToZip, String parrentDirectoryName) throws Exception {
    FileOutputStream fos = new FileOutputStream("archive.zip");
    ZipOutputStream zos = new ZipOutputStream(fos);
    if (fileToZip == null || !fileToZip.exists()) {
        zos.close();
        return;
    }

    String zipEntryName = fileToZip.getName();
    if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
        zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
    }

    if (fileToZip.isDirectory()) {
        if (Main.verbose ) System.out.println("+" + zipEntryName);
        for (File file : fileToZip.listFiles()) {
            addDirToZipArchive(file, zipEntryName);
        }
    } else {
        if (Main.verbose) System.out.println("   " + zipEntryName);
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(fileToZip);
        zos.putNextEntry(new ZipEntry(zipEntryName));
        int length;
        while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
        }
        zos.closeEntry();
        zos.close();
        fis.close();
    }
}
  
}