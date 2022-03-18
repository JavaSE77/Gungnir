package edu.cs.ship.rs0699.gungnir.tests;

import edu.cs.ship.rs0699.gungnir.ConfigurationHandler;
import junit.framework.TestCase;

public class ConfiguationTests extends TestCase {

  
  public void testConfigurationRead() {
    
    ConfigurationHandler config = ConfigurationHandler.getInstance();
    
    config.readFile("config.txt");
    //if no errors are thrown, then it works
    assert(true);
    
  }

  public void testInitialValues() {

    ConfigurationHandler config = ConfigurationHandler.getInstance();
    
    
    assertEquals(config.getConfiglocation(), "config.txt");
    assertEquals(config.verbose(), true);
    assertEquals(config.portRangeLower(), 8000);
    assertEquals(config.portRangeUpper(), 8080);
    
    assertEquals(config.URLincludesPort(), true);
    assertEquals(config.URL(), "localhost");
    
    assertEquals(config.ANGLETUTORIAL(), "youtube.com");
    assertEquals(config.WEIGHTTUTORIAL(), "youtube.com");
    assertEquals(config.NAMETUTORIAL(), "youtube.com");
    
  }
  
  public void testDefaultValues() {

    ConfigurationHandler config = ConfigurationHandler.getInstance();
    config.readFile("config.txt");
    
    assertEquals(config.getConfiglocation(), "config.txt");
    assertEquals(config.verbose(), true);
    assertEquals(config.portRangeLower(), 8000);
    assertEquals(config.portRangeUpper(), 8080);
    
    assertEquals(config.URLincludesPort(), true);
    assertEquals(config.URL(), "localhost");
    
    assertEquals(config.ANGLETUTORIAL(), "youtube.com");
    assertEquals(config.WEIGHTTUTORIAL(), "youtube.com");
    assertEquals(config.NAMETUTORIAL(), "youtube.com");
    
  }
  
  
  public void testModifiedValues() {

    ConfigurationHandler config = ConfigurationHandler.getInstance();
    config.readFile("testConfig.txt");
    
    assertEquals(config.getConfiglocation(), "testConfig.txt");
    assertEquals(config.verbose(), true);
    assertEquals(config.portRangeLower(), 1);
    assertEquals(config.portRangeUpper(), 2);
    
    assertEquals(config.URLincludesPort(), true);
    assertEquals(config.URL(), "localhost");

    System.out.println("KEY=119111 " +config.WEIGHTTUTORIAL());
    assertEquals(config.ANGLETUTORIAL(), "ANGLETUTORIAL.com");
    assertEquals(config.WEIGHTTUTORIAL(), "WEIGHTTUTORIAL.com");
    assertEquals(config.NAMETUTORIAL(), "NAMETUTORIAL.com");
    
  }
  
}
