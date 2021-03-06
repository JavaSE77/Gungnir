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
      //coef of friction is .4. Multiply by 1.7 to account for friction
      
      long timeDelta = sensorReadings[2] - sensorReadings[0];
      //Time to go 4 feet. / 4 to get the time to go 1 foot. Then * .682 to get mph
      //We have found the Erika constant to be about 3.2. Multiplying this value by 3.2 gives a normal value
      double speed = (((distance * 2)/(timeDelta / 1000.0)) * Math.PI) * .682;
      
      if( Main.verbose) System.out.println("Calculated speed: " + speed);
      return speed;
    }
    
    return -1;
  }
  
  /**
   * long[] input, double distance
   * readings from the 3 sensors, amount of distance between the fixed sensors
   * @return the abs of acceleration
   * @deprecated
   * */
  public double getAcceleration(long[] input, double distance) {
    
    //we should have three values from the sensors
    if(input.length == 3) {
      
      //get the speed from the first two, and compare it to the second two
      
      long timeDeltaA = input[1] - input[0];
      if ( Main.verbose) System.out.println("Time Delta: " + timeDeltaA + "," + timeDeltaA + " distance: " + distance);//.392
      long timeDeltaB = input[2] - input[1];
      long timeDeltaTotal = input[2] - input[0];
      double totalTimeInSeconds = timeDeltaA / 1000.0;
      
      //change is distance / change in time
      double acceleration = ((((distance)/(timeDeltaA / 1000.0))*1.7) - (((distance)/(timeDeltaB / 1000.0))*1.7)) / (totalTimeInSeconds);
      if( Main.verbose) System.out.println("Calculated acceleration: " + acceleration + " Calculated from: " + 
      ((distance)/(timeDeltaA / 1000.0)) + " - " + ((distance)/(timeDeltaB / 1000.0)) + " total time: " + (totalTimeInSeconds));

      
      return Math.abs(acceleration + 32);
    }
    
    return -1;
  }
  
  /**
   * double acceleration, double weight, double gravity
   * (all units must be the same type of units, either SI or imperial)
   * @return double force
   * @deprecated
   * */
  public double getForce(double acceleration, double weight, double gravity) {
    //f = m * A
    // m = weight * g
    double m = weight / gravity;

    
    return acceleration * m;
  }
  
  /**
   * double acceleration, double weight, double gravity
   * (all units must be the same type of units, either SI or imperial)
   * @return double expected distance
   * R = V?? * sin(2??) / g
   * https://www.omnicalculator.com/physics/projectile-motion#:~:text=The%20equation%20for%20the%20distance,is%20when%202%CE%B8%20%3D%2090%20degrees.
   * */
  public double getExpectedDistance(double acceleration, double gravity, double angle) {
    
    //acceleration * 1.46 to go from MPH to fps
    double R = (Math.pow((acceleration * 1.46),2) * Math.sin(Math.toRadians(2* angle))) / gravity;
    
    return R;
  }
  
  
}
