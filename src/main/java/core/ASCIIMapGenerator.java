package core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ASCIIMapGenerator {
  private static final int MAP_WIDTH = 120;
  private static final int MAP_HEIGHT = 80;

  private static final double UK_MIN_LATITUDE = 49.0, UK_MAX_LATITUDE = 62.00;
  private static final double UK_MIN_LONGITUDE = -9, UK_MAX_LONGITUDE = 3.00;

  public static void main(String[] args) {
    PropertyLoader config = new PropertyLoader("src/main/resources/configuration.properties");
    String inputFile = config.getProperty("inputFile");
    String outputFile = config.getProperty("outputFile");
    char charToPlot = config.getProperty("charToPlot").charAt(0);
    int numberOfPlots=0;

    List<double[]> allRecords = ReadInputCSV.readCSVFile(inputFile);

    char[][] mapArea = new char[MAP_HEIGHT][MAP_WIDTH];
    for (char[] row : mapArea) {
      Arrays.fill(row, ' ');
    }

    for (double[] coordinates : allRecords) {
      double latitude = coordinates[0];
      double longitude = coordinates[1];

      int x = (int) ((longitude - UK_MIN_LONGITUDE) / (UK_MAX_LONGITUDE - UK_MIN_LONGITUDE) * (MAP_WIDTH - 1));
      int y = (int) ((UK_MAX_LATITUDE - latitude) / (UK_MAX_LATITUDE - UK_MIN_LATITUDE) * (MAP_HEIGHT - 1));
      
      if (x >= 0 && x < MAP_WIDTH && y >= 0 && y < MAP_HEIGHT) {
        mapArea[y][x] = charToPlot;
        numberOfPlots++;
      }
    }

    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      for (char[] row : mapArea) {
        fileWriter.write(new String(row) + "\n");
      }
 //     System.out.println("Number of postcodes plotted " + numberOfPlots);
      System.out.println("ASCII map created : " + outputFile);

    } catch (IOException e) {
      System.err.println("Error creating output file " + e.getMessage());
    }
  }
}
