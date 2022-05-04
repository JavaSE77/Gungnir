package edu.cs.ship.rs0699.gungnir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

public class HTMLfileReader {

  /**
   * String readFile. reads the given file and replaces regex
   * @param String fileName
   * @return contents of the file with the regex changed
   * */
  public String readFile(String fileName, CSVhandler csv) {
    
    String fileContents = "file not found";
    ConfigurationHandler config = ConfigurationHandler.getInstance();

    
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
    String url = null;
    if(config.URLincludesPort()) {
        url = ServerHandler.getInstance().getURL() + ":" + ServerHandler.getInstance().getBoundPort();
    }else {
        url = ServerHandler.getInstance().getURL();
    }
    
    String ANGLETUTORIAL = config.ANGLETUTORIAL();
    String NAMETUTORIAL = config.NAMETUTORIAL();
    String WEIGHTTUTORIAL = config.WEIGHTTUTORIAL();
    FileHandler cssFile = new FileHandler("website/nicepage.css");
    FileHandler jsFile = new FileHandler("website/nicepage.js");
    //image files must be encoded in base64
    FileHandler backgroundFile = new FileHandler("website/images/background.txt");
    FileHandler logoFile = new FileHandler("website/images/logo.txt");
    String CSS = cssFile.readFile();
    String background = backgroundFile.readFile();
    String logo = logoFile.readFile();
    
    String angle = "" + UserSettings.getInstance().getAngle();
    String weight = "" + UserSettings.getInstance().getWeight();
    String name = "" + UserSettings.getInstance().getUser();
    
    String distance = "";
    String speed = "";
    String acceleration = "";
    String force = "";
    
    if(csv != null) {
      try {
      String[] csvRecord = csv.getLastRecord().split(",");
      //CSV record looks like this:
      //User,Distance,Weight,Angle,Speed,Acceleration,Force,sensorA,sensorB,SensorC,date
      distance = csvRecord[1].replaceAll(",", "") + " feet";
//      speed = Math.round((Double.parseDouble(csvRecord[4].replaceAll(",", "")))*100.0)/100.0 + " MPH";
      speed = csvRecord[4].replaceAll(",", "") + " MPH";
      acceleration = csvRecord[6].replaceAll(",", "") + " FPS";
      force = csvRecord[7].replaceAll(",", "") + " pound";
      } catch (IndexOutOfBoundsException e) {
        if (Main.verbose) e.printStackTrace();
        System.out.println("Error reading results from CSV");
      }
    }
    fileContents = fileContents.replaceAll("%URL%", url).replaceAll("%ANGLE%", angle).replaceAll("%WEIGHT%", weight)
        .replaceAll("%NAME%", name).replaceAll("%ANGLETUTORIAL%", ANGLETUTORIAL)
        .replaceAll("%NAMETUTORIAL%", NAMETUTORIAL).replaceAll("%WEIGHTTUTORIAL%", WEIGHTTUTORIAL).replaceAll("%INCLUDECSS%", CSS)
        .replaceAll("%INCLUDEBACKGROUND%", background).replaceAll("%INCLUDELOGO%", logo)
        .replaceAll("%DISTANCE%", distance).replaceAll("%SPEED%", speed).replaceAll("%ACCELERATION%", acceleration)
        .replaceAll("%FORCE%", force);
       
    //return contents of the file read in as a string. If try catch throws exception, return "file not found"
    return fileContents;
  }
  
  
  
}
