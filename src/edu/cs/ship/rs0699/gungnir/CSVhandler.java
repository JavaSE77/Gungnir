package edu.cs.ship.rs0699.gungnir;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVhandler {
  
  private String fileName;
  private boolean verbose = ConfigurationHandler.getInstance().verbose();

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
  
}