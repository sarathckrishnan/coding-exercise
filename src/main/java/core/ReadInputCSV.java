package core;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadInputCSV {

  public static List<double[]> readCSVFile(String filePath) {
    List<double[]> coordinates = new ArrayList<>();
    double startTime = System.nanoTime();
    int totalNumberOfRecords= 0;

    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      String[] nextLine;
      reader.readNext();

      while ((nextLine = reader.readNext()) != null) {
          try {
            double latitude = Double.parseDouble(nextLine[2]);
            double longitude = Double.parseDouble(nextLine[3]);
            coordinates.add(new double[]{latitude, longitude});
            totalNumberOfRecords++;
          } catch (NumberFormatException exception) {
            System.out.println("Exception for postcode "+nextLine[1]);
        }
      }
    } catch (IOException | CsvValidationException e) {
      System.err.println("Error occurred " + e.getMessage());
    }

    double totalTimeTaken = (System.nanoTime()-startTime)/1000000000;
    System.out.printf("%d records read in %.2f seconds%n", totalNumberOfRecords, totalTimeTaken);
    return coordinates;
    
  }
}
