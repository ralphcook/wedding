package rc.wedding.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Person
{
  private String firstName; public String getFirstName() { return firstName; } public void setFirstName(String s) { firstName = s; }
  private String lastName;  public String getLastName()  { return lastName;  } public void setLastName(String s) { lastName = s; }
  private String description; public String getDescription() { return description; } public void setDescription(String s) { description = s; }
  private String key;       public String getKey()       { return key;       } public void setKey(String s) { key = s; }
  private List<Relationship> 
                 relationships = new ArrayList<>();
                                public List<Relationship> getRelationships() { return relationships; }
                                public void setRelationships(List<Relationship> l) { relationships = l; }
  private static String fullNameFormat = "%s %s";
  
  public Person() { } 
  public Person(String key)  {    this.key = key;  }
  
  /**
   * stopped calling this for now -- it read an individual file about one person, 
   * and we've converted to one file that contains all information.
   * @param key
   * @return
   */
  public static Person readPersonFile(String key)
  {
    Person person = new Person(key);
    BufferedReader reader = null;
    try
    {
      String specificPath = "resources/people/indices/";
      File peopleFile = ResourceUtil.getReadFile(specificPath, key, "txt");
      if (peopleFile == null)
      {
        System.err.println("No person file for key [" + key + "]");
      }
      else
      {
        reader = new BufferedReader(new FileReader(peopleFile));
        String line = reader.readLine();
        while (line != null)
        {
          person.setField(line);
//          line = line.trim();
//          if (line.length() > 0 && !line.startsWith("//"))
//          {
//            int commentIndex = line.indexOf("//");
//            if (commentIndex > 0)
//            {
//              line = line.substring(0, commentIndex);
//            }
//            String[] lineParts = line.split(":");
//            for (String s : lineParts)
//            {
//              s = s.trim();
//            }
//
//            switch (lineParts[0].toLowerCase())
//            {
//              case "key": break;    // no action here
//              case "first":       person.setFirstName(checkLength(key, lineParts, 1)); break;
//              case "last":        person.setLastName(checkLength(key, lineParts, 1)); break;
//              case "description": person.setDescription(checkLength(key, lineParts, 1)); break;
//              case "relationship":
//                String relationshipNameString = checkLength(key, lineParts, 1);
//                String relationshipKey = checkLength(key, lineParts, 2);
//                  try
//                  {
//                    Relationship.Name relationshipName = Relationship.Name.valueOf(relationshipNameString.toUpperCase());
//                    Relationship r = new Relationship(relationshipKey, relationshipName);
//                    person.getRelationships().add(r);
//                  } 
//                  catch (Exception e)
//                  {
//                    System.err.println("Bad relationship name: <" + lineParts[1] + "> for " + key + ".txt");
//                  }
//                break;
//              default:
//                System.err.println("Bad type value in person file: <" + lineParts[0] + "> for " + key + ".txt");
//                break;
//            }
          }
          line = reader.readLine();
        }
      }
//    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try { if (reader != null) { reader.close(); } } catch (IOException ioe) {}  // legitimately nothing to do here.
    }
    return person;
  }
  
  /**
   * set whichever field value is indicated in the given line,
   * which is something like "first:Jennifer" or "relationship:wife:bhall".
   * @param line
   */
  public void setField(String line)
  {
    line = line.trim();
    if (line.length() > 0 && !line.startsWith("//"))
    {
      int commentIndex = line.indexOf("//");
      if (commentIndex > 0)
      {
        line = line.substring(0, commentIndex);
      }
      String[] lineParts = line.split(":");
      for (String s : lineParts)
      {
        s = s.trim();
      }

      switch (lineParts[0].toLowerCase())
      {
        case "key":         setKey(checkLength(lineParts[1], lineParts, 1)); break;
        case "first":       setFirstName(checkLength(key, lineParts, 1)); break;
        case "last":        setLastName(checkLength(key, lineParts, 1)); break;
        case "description": setDescription(checkLength(key, lineParts, 1)); break;
        case "relationship":
          String relationshipNameString = checkLength(key, lineParts, 1);
          String relationshipKey = checkLength(key, lineParts, 2);
            try
            {
              Relationship.Name relationshipName = Relationship.Name.valueOf(relationshipNameString.toUpperCase());
              Relationship r = new Relationship(relationshipKey, relationshipName);
              relationships.add(r);
            } 
            catch (Exception e)
            {
              System.err.println("Bad relationship name: <" + lineParts[1] + "> for " + key + ".txt");
            }
          break;
        default:
          System.err.println("Bad type value in person file: <" + lineParts[0] + "> for " + key + ".txt");
          break;
      }
    }
  }
  
  /**
   * if the array is long enough, set the given field to have the value of the last array element 
   * @param key
   * @param array
   * @param pos
   * @param field
   */
  private static String checkLength(String key, String[] array, int pos)
  {
    String result = "<?>";
    if (array.length > pos) { result = array[pos];    }
                      else  { System.err.printf("insufficient elements for field %s in person %s%n", array[0], key);    }
    return result;
  }
  
  private static boolean okLength(String key, String[] array, int length)
  {
    boolean result = true;
    if (array.length < length) 
      { 
        result = false;
      }
    return result;
  }
  
  public Person(String key, String firstName, String lastName, Relationship ... list)
  {
    this.key = key;
    this.firstName = firstName;
    this.lastName = lastName;
    if (list != null) { for (Relationship r: list) { relationships.add(r); }  }
  }
  
  public String getFullName()
  {
    return String.format(fullNameFormat, firstName, lastName);
  }
}
