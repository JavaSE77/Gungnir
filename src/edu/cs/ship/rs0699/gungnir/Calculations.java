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
  
  /**
   * long[] input, double distance
   * readings from the 3 sensors, amount of distance between the fixed sensors
   * @return double speed 
   * */
  public double getSpeed(long[] sensorReadings, double distance) {
    
    if(sensorReadings.length == 3) {
      
      //for our speed calculation, we are using distance / time delta
      
      long timeDelta = sensorReadings[2] - sensorReadings[0];
      //Time to go 4 feet. / 4 to get the time to go 1 foot. Then * .68 to get mph
      double speed = ((((distance * 2)) / (timeDelta))/4 * 0.681818);
      
      if( Main.verbose) System.out.println("Calculated speed: " + speed);
      return speed;
    }
    
    return -1;
  }
  
  /**
   * long[] input, double distance
   * readings from the 3 sensors, amount of distance between the fixed sensors
   * @return the abs of acceleration
   * */
  public double getAcceleration(long[] input, double distance) {
    
    //we should have three values from the sensors
    if(input.length == 3) {
      
      //get the speed from the first two, and compare it to the second two
      
      long timeDeltaA = input[1] - input[0];
      long timeDeltaB = input[2] - input[1];
      
      //change is distance / change in time
      double acceleration = distance / ((timeDeltaB - timeDeltaA));
      
      return Math.abs(acceleration);
    }
    
    return -1;
  }
  
  /**
   * double acceleration, double weight, double gravity
   * (all units must be the same type of units, either SI or imperial)
   * @return double force
   * */
  public double getForce(double acceleration, double weight, double gravity) {
    //f = m * A
    // m = weight * g
    double m = weight * gravity;

    
    return acceleration * m;
  }
  
  
  
}
