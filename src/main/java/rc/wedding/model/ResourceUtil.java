package rc.wedding.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.vaadin.server.FileResource;

public class ResourceUtil
{
  private static final String javaSeparator = "/";
  private static final String javaSeparatorRegex = "/";
//  private static final String otherSeparator = "\\";
  private static final String windowsSeparatorRegex = "\\\\";
  
  private static String withSlash(String s)
  {
    s = s.replaceAll(windowsSeparatorRegex, javaSeparatorRegex);
    if (!s.endsWith(javaSeparator)) { s = s + javaSeparator; }
    return s;
  }
  
  private static String getFullFilename(String path, String filename, String extension)
  {
    String basepath = getBaseFilepath();
    String fullFilename = basepath + withSlash(path) + filename + "." + extension;
    return fullFilename;
  }
  
  public static File getReadFile(String path, String filename, String extension)
  {
    String fullFilename = getFullFilename(path, filename, extension);
    File file = getReadFile(fullFilename);
    return file;
  }
  
  public static File getReadFile(String fullFilename)
  {
    File file = null;
    try
    {
      file = new File(fullFilename);
      if (!file.exists()) { file = null; }
    }
    catch (Throwable t)
    {
      file = null;
    }
    return file;
  }
  
  private static String getBaseFilepath()
  {
    String basepath = com.vaadin.server.VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    basepath = basepath.replaceAll(windowsSeparatorRegex, javaSeparatorRegex);
    basepath = withSlash(basepath);
    return basepath;
  }
  
  
  public static FileResource getResource(String specificPath, String filename, String extension)
  {
    FileResource resource = null;
    File file = getReadFile(specificPath, filename, extension);
    if (file != null)
    {
      resource = new FileResource(file);
    }
    return resource;
  }
  
  public static String readResourcesTextFile(String filename)
  {
    String returnLine = readTextFile("resources/text/", filename, "txt");
    return returnLine;
  }

  public static String readPlaceDescriptionFile(String filename)
  {
    String returnLine = readTextFile("resources/places/", filename, "desc");
    return returnLine;
  }

  private static String readTextFile(String path, String filename, String extension)
  {
    String basePath = getBaseFilepath();
    String fullFilename = String.format("%s%s%s.%s", basePath, path, filename, extension);
    BufferedReader textReader = null;
    String returnText = "";
    try
    {
//      File file = ResourceUtil.getReadFile(fullFilename);
      textReader = new BufferedReader(new FileReader(fullFilename));
      StringBuilder text = new StringBuilder();
      String line = textReader.readLine();
      while (line != null)
      {
        if (text.length() > 0) { text.append(" "); }
        text.append(line);
        line = textReader.readLine();
      }
      returnText = text.toString();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try { if (textReader != null) { textReader.close(); } } catch (Exception e) {}
    }
    return returnText;
  }
  
}
