package rc.wedding.model;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

public class ImageUtil extends ResourceUtil
{
  public static Image getImageFromResource(String subdirectoryPath, String filenameStem, String extension)
  {
    FileResource resource = getImageFileFromResource(subdirectoryPath, filenameStem, extension);
    Image image = null;
    if (resource != null) { image = new Image("", resource); }  // no caption
    return image;
  }
  
  public static FileResource getImageFileFromResource(String imageSubDirectory, String filenameStem, String extension)
  {
    String specificPath = "resources/";
    if (imageSubDirectory != null)    { specificPath += imageSubDirectory; }
    
    FileResource fileResource = ResourceUtil.getResource(specificPath, filenameStem, extension);
    
    return fileResource;
  }
  
  public static Image getImageFromFile(File file)
  {
    FileResource fileResource = new FileResource(file);
    Image image = new Image("", fileResource);
    return image;
  }
}
