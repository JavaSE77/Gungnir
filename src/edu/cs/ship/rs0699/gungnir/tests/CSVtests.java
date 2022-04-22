package edu.cs.ship.rs0699.gungnir.tests;



import org.junit.Test;

import edu.cs.ship.rs0699.gungnir.CSVhandler;
import junit.framework.TestCase;

public class CSVtests extends TestCase{

  @Test
  public void testCSVLoading() {
    
    CSVhandler handler = new CSVhandler("testData.csv");
    handler.readFile();
    
    assert(true);
    
  }

  @Test
  public void testCSVIO() {
    
    CSVhandler handler = new CSVhandler("testData.csv");
    String line = handler.getLine(0);
    handler.appendLineNoErrorChecking(line);
    
    assert(true);
    
  }
  
  @Test
  public void testAddRecord() {
    System.out.println("CSV OUTPUT");
    CSVhandler handler = new CSVhandler("testData.csv");
    System.out.println(handler.pickCSVFile());
    System.out.println(handler.getFileName());
  }
  
}
