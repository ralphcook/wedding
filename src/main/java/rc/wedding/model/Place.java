package rc.wedding.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Place
{
  private String key      = null;                                                 public String getKey()  { return key; }
  private String name     = null; public void setName(String s) { name = s; }     public String getName() { return name; }
  private String text     = null; public void setText(String s) { text = s; }     public String getText() { return text; }
  private String website  = null; public void setWebsite(String s) { website = s; }  public String getWebsite() { return website; }
  private String map      = null; public void setMap(String s) { map = s; }  public String getMap() { return map; }
  
//  public Place(String name, String url)
//  {
//    this.name = name;
//    this.url = url;
//    this.mapLink = String.format("<a href='%s'>%s</a>", url, name);
//  }
  
  public Place(String key, File placeFile)
  {
    this.key = key;
    BufferedReader reader = null;
    try 
    {
      reader = new BufferedReader(new FileReader(placeFile));
      String line = reader.readLine();
      while (line != null)
      {
        line = line.trim();
        if (!line.startsWith("//")) 
        {
          int firstColon = line.indexOf(":");
          if (firstColon > 0)
          {
              String field = line.substring(0, firstColon).toLowerCase();
              String data     = line.substring(firstColon+1);
              switch (field)
              {
              case "name"   : setName(data); break;
              case "text"   : setText(data); break;
              case "website": setWebsite(data); break;
              case "map"    : setMap(data); break;
              }
          }
          
        }
        line = reader.readLine();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
}
