package rc.wedding.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceData
{
  private static HashMap<String, Place> placeMap = new HashMap<>();
  private static List<Place>            placeList = new ArrayList<>();  public List<Place> getPlaceList() { return placeList; }
  
  static
  {
    readPlaceData();
  }
  
  public static void readPlaceData()
  {
    BufferedReader reader = null;
    try
    {
      String specificPath = "resources/places/";

      File placesFile = ResourceUtil.getReadFile(specificPath, "places", "txt");           
      reader = new BufferedReader(new FileReader(placesFile));
      String line = reader.readLine();
      while (line != null)
      {
        line = line.trim();
        if (line.length() > 0 && !line.startsWith("//"))
        {
          int commentIndex = line.indexOf("//");
          if (commentIndex > 0) { line = line.substring(0,commentIndex); }
          String placeKey = line;
          File placeFile = ResourceUtil.getReadFile(specificPath, placeKey, "txt");
          Place place = new Place(placeKey, placeFile);
          if (place != null)
          {
            placeMap.put(placeKey, place);
            placeList.add(place);
          }
        }
        line = reader.readLine();
      }
    } 
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally
    {
      if (reader != null) { try { reader.close(); } catch (IOException ioe) {ioe.printStackTrace();} }
    }
  }
  
  public static List<Place> getPlaces()
  {
    if (placeMap.isEmpty()) 
    { readPlaceData(); 
    }
    ArrayList<Place> places = new ArrayList<>();
    for (String s: placeMap.keySet())
    {
      places.add(placeMap.get(s));
    }
    return places;
  }
}
