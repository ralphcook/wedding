package rc.wedding.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

public class PersonData
{
  private static HashMap<String, Person> map = new HashMap<>();
  private static List<Person> list = new ArrayList<>();
  
  static 
  { 
    readPersonData2();
  }
  
  private static void readPersonData2()
  {
    BufferedReader reader = null;
    try
    {
      Person currentPerson = null;
      String specificPath = "resources/people/";
      File peopleFile = ResourceUtil.getReadFile(specificPath, "allPeople", "txt");
      reader = new BufferedReader(new FileReader(peopleFile));
      String line = reader.readLine();
      while (line != null)
      {
        line = line.trim();
        if (line.length() > 0 && !line.startsWith("//"))
        {
          int commentIndex = line.indexOf("//");
          if (commentIndex > 0) { line = line.substring(0, commentIndex); }
          String currentLine = line.trim();
          if (line.length() > 0)
          {
            // if the line starts with "key:", we start a new person object,
            // add it to the list, set it as current; for all others, we just
            // add the info to the current person.
            if (currentLine.startsWith("key:"))
            {
              currentPerson = new Person();
              list.add(currentPerson);
            }
            currentPerson.setField(currentLine);
          }
        }
        line = reader.readLine();
      }
    }
    catch (Exception e) { e.printStackTrace(); }
    finally { try { reader.close(); } catch (IOException ioe) {} }
    
    // now that the list is built, build the map to go with it.
    for (Person p:list) 
    {
      map.put(p.getKey(), p);
    }
  }

  private static void readPersonData()
  {
    BufferedReader reader = null;
    try
    {
      String specificPath = "resources/people/indices/";
      File peopleFile = ResourceUtil.getReadFile(specificPath, "people", "txt");
      reader = new BufferedReader(new FileReader(peopleFile));
      String line = reader.readLine();
      while (line != null)
      {
        line = line.trim();
        if (line.length() > 0 && !line.startsWith("//"))
        {
          int commentIndex = line.indexOf("//");
          if (commentIndex > 0) { line = line.substring(0, commentIndex); }
          String personKey = line.trim();
          if (line.length() > 0)
          {
            Person person = new Person(personKey);
            addPerson(person);
          }
        }
        line = reader.readLine();
      }
    }
    catch (Exception e) { e.printStackTrace(); }
    finally { try { reader.close(); } catch (IOException ioe) {} }
  }
  
  public static void addPerson(Person p)
  {
    map.put(p.getKey(), p);
    list.add(p);
  }
  
  public static void addPerson(String key, String fName, String lName, Relationship ... relationships)
  {
    Person person = new Person(key, fName, lName, relationships); 
    map.put(key, person);
    list.add(person);
  }
  
  public static Person get(String key) 
  {
    Person person = map.get(key);
    return person; 
  }  
  
  public static List<Person> getPersonList()
  {
    return list;
  }
  
  public static FileResource getPersonThumbnailResource(String key)
  {
    FileResource returnValue = getPersonImageResource("people/images/thumbnails/", key);
    return returnValue;
  }
  
  public static FileResource getPersonFullsizeResource(String key)
  {
    FileResource resource = getPersonImageResource("people/images/fullsize/", key);
    return resource;
  }

  private static FileResource getPersonImageResource(String folder, String key)
  {
    FileResource returnValue = ImageUtil.getImageFileFromResource(folder, key, "jpg");
    return returnValue;
  }
  
  public static Image getPersonFullsizeImage(String key)
  {
    FileResource resource = getPersonFullsizeResource(key);
    Image image = null;
    if (resource != null) { image = new Image("", resource); }  // no caption
    return image;
//    Image image = ImageUtil.getImageFromResource("people/images/", key, "jpg");
//    Resource resource = getPersonResource(key);
//    Image image = null; 
//    if (resource != null) { image = new Image("", resource); }
//    return image;
  }
}
