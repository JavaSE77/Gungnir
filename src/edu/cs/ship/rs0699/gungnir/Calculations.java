package edu.cs.ship.rs0699.gungnir;

public class Calculations {

  private static Calculations singleton = null;
  //this needs to be a singleton

  
  
  // Static method
  // Static method to create instance of Singleton class
  public static Calculations getInstance()
  {
      if (singleton == null)
        singleton = new Calculations();

      return singleton;
  }
  
  public double getSpeed(long[] input, double distance) {
    
    if(input.length == 4) {
      
      //for our speed calculation, we are using distance / time delta
      
      long timeDelta = input[3] - input[0];
      
      double speed = distance / timeDelta;
      
      return speed;
    }
    
    return -1;
  }
  
  
}
