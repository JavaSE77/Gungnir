package edu.cs.ship.rs0699.gungnir.tests;

import edu.cs.ship.rs0699.gungnir.UserSettings;
import junit.framework.TestCase;

public class AngleInput extends TestCase {

  
  public void testUserSettings() {
    
    UserSettings us = UserSettings.getInstance();
    
    assertNotNull(us);

    
  }
  
  public void testAngleSetUnder180() {
    UserSettings us = UserSettings.getInstance();
    
    //test under 180
    double angleToSet = 80.0;
    us.setAngle(angleToSet);
    assertEquals(us.getAngle(), angleToSet);
    
    
  }
  
  
  public void testAngleSetOver180() {
    UserSettings us = UserSettings.getInstance();
    
    //test over 180
    double angleToSet = 280.0;
    us.setAngle(angleToSet);
    if(us.getAngle() != angleToSet && us.getAngle() != 0)
      assert(true);
    else
      assert(false);
  }
  
  public void testWeightSet() {
    UserSettings us = UserSettings.getInstance();
    assertEquals(us.getWeight(), 0.0);
    
    //test under 180
    double weight = 80.0;
    us.setWeight(weight);
    assertEquals(us.getWeight(), weight);
  }
  
  
}
