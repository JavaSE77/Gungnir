package edu.cs.ship.rs0699.engr499;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTMLfileReader {

  public String readFile(String fileName) {
    
    String fileContents = "file not found";

    
    try {  
      //get the path of the file
      Path file = Path.of(fileName);
      //get the file as a string
      fileContents = Files.readString(file);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.out.println("Because the file read returned an error, we displayed 'file not found' to the user" );
    }
    //get the url of the website. Replace all instances of %URL% with the sites url as set in the main
    String url = ServerHandler.getInstance().getURL() + ":" + ServerHandler.getInstance().getBoundPort();
    String angle = "" + UserSettings.getInstance().getAngle();
    String weight = "" + UserSettings.getInstance().getWeight();
    fileContents = fileContents.replaceAll("%URL%", url).replaceAll("%ANGLE%", angle).replaceAll("%WEIGHT%", weight);
    //return contents of the file read in as a string. If try catch throws exception, return "file not found"
    return fileContents;
  }
  
  
  
}
