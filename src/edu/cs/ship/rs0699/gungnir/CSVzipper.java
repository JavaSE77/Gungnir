package edu.cs.ship.rs0699.gungnir;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CSVzipper implements HttpHandler {

  CSVhandler CSV;
  
  public CSVzipper(CSVhandler CSV) {
    this.CSV = CSV;
  }
  
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    // TODO Auto-generated method stub

    byte[] data = null;
    int responseCode = 400;
    try {
      CSV.addDirToZipArchive((new File("records/")), null);
      Path path = Paths.get("archive.zip");
      data = Files.readAllBytes(path);
      responseCode = 200;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String response = "Thank you for downloading our records.";
    exchange.sendResponseHeaders(responseCode, data.length);
    OutputStream os = exchange.getResponseBody();
    os.write(data);
    System.out.println(exchange.getRequestURI());
    os.close();
    
  }

}
