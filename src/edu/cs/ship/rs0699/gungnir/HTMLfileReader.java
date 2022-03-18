package edu.cs.ship.rs0699.gungnir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTMLfileReader {

  /**
   * String readFile. reads the given file and replaces regex
   * @param String fileName
   * @return contents of the file with the regex changed
   * */
  public String readFile(String fileName) {
    
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
    
    String angle = "" + UserSettings.getInstance().getAngle();
    String weight = "" + UserSettings.getInstance().getWeight();
    String name = "" + UserSettings.getInstance().getUser();
    fileContents = fileContents.replaceAll("%URL%", url).replaceAll("%ANGLE%", angle).replaceAll("%WEIGHT%", weight)
        .replaceAll("%NAME%", name).replaceAll("%ANGLETUTORIAL%", ANGLETUTORIAL)
        .replaceAll("%NAMETUTORIAL%", NAMETUTORIAL).replaceAll("%WEIGHTTUTORIAL%", WEIGHTTUTORIAL);
    //return contents of the file read in as a string. If try catch throws exception, return "file not found"
    return fileContents;
  }
  
  
  
}
