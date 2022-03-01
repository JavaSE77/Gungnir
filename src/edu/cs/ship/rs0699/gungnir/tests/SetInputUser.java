package edu.cs.ship.rs0699.gungnir.tests;

import edu.cs.ship.rs0699.gungnir.UserSettings;
import junit.framework.TestCase;

public class SetInputUser extends TestCase {
  
  public void testUserSettings() {
    
    UserSettings us = UserSettings.getInstance();
    
    assertNotNull(us);
    

  }
  
  public void testUserSet() {
    UserSettings us = UserSettings.getInstance();
    
    String user = "Russell";
    us.setUser(user);
    assertEquals(us.getUser(), user);
    
  }
  
  public void testUserName() {
    UserSettings us = UserSettings.getInstance();
    
    String user = "JavaSE";
    us.setUser(user);
    assertEquals(us.getUser(), user);  
    
  } 
  
  public void testUserID() {
    UserSettings us = UserSettings.getInstance();
    
    String user = "1010101010101010101";
    us.setUser(user);
    assertEquals(us.getUser(), user);
    
  }
  
  public void testUserNull() {
    UserSettings us = UserSettings.getInstance();
    
    String user = null;
    us.setUser(user);
    assertNull(user);
    
  }
}
