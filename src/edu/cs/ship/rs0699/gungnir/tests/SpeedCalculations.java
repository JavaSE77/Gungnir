package edu.cs.ship.rs0699.gungnir.tests;
import edu.cs.ship.rs0699.gungnir.Calculations;
import junit.framework.Assert;
import junit.framework.TestCase;

public class SpeedCalculations extends TestCase {

  public void testSpeed() {
    
    Calculations calulator = Calculations.getInstance();
    
    long[] input = {10,20,30,40};
    double distance = 1000.0;
    
    double speed = calulator.getSpeed(input, distance);
    System.out.println("Speed: " + speed + " MPH");
    
    assertNotNull(speed);
  }
  
}
