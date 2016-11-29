package rc.wedding.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import net.coobird.thumbnailator.Thumbnails;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.server.Page;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class UploadView extends NavigationView implements Receiver, SucceededListener, FailedListener
{
  private static String viewDirectoryName = null;   // directory from which files are viewed
  private static String origDirectoryName = null;   // directory to which files are uploaded
  public static String getViewDirectoryName() { return viewDirectoryName; }
  public static String getOrigDirectoryName() { return origDirectoryName; }
  
  private HashMap<String, String> filenameMap = new HashMap<>();
  private BrowseView browseView = new BrowseView();
  
  static
  {
    String osName = System.getProperty("os.name");    // os for the server...
    say("os.name of " + osName);
    if (osName.toLowerCase().contains("windows"))  { viewDirectoryName = "c:/local/weddingImages/";    origDirectoryName = viewDirectoryName + "orig/"; }
                                              else { viewDirectoryName = "/var/lib/tomcat/webapps/weddingUploadImages/"; origDirectoryName = viewDirectoryName + "orig/"; }
    say("viewDirectory set to " + viewDirectoryName);
  }
  
  public static void say(String s) { System.out.println(s); }
  
//  private Label statusLabel = new Label("");
  
  @Override
  public void attach()
  {
    super.attach();
    buildUI();
    say("done UploadView.attach()");
  }
  
  private void buildUI()
  {
    setCaption("Share");
    addStyleName("shareView");
    setSizeFull();
    
    CssLayout content = new CssLayout();
    
    String text = "You can share photos; just upload from pictures on your"
        + " phone (or take one now) by pressing 'Upload', and look at what"
        + " has been shared already by pressing 'Browse'.";
    Label topLabel = new Label(text);
    
    Upload upload = new Upload("", this);
    upload.setReceiver(this);
    upload.addSucceededListener(this);
    upload.addFailedListener(this); // new 11/16
    upload.setImmediate(true);
    
    NavigationButton browseButton = new NavigationButton("Browse", browseView);

    VerticalLayout buttonGroup = new VerticalLayout();
    buttonGroup.addComponents(upload, browseButton);
    
    content.addComponents(topLabel, buttonGroup);
    setContent(content);
  }
  
  @Override
  public OutputStream receiveUpload(String filename, String mimeType)
  {
    FileOutputStream fileOutputStream = null;
    try
    {
      // convert filename they give us to a 'local' filename; this allows us to ensure
      // uniqueness across filenames, and to order them. We use other filenames to generate
      // the local filename, so doing it requires the directory into which files are uploaded.
      File outputDirectory = new File(origDirectoryName);
      String localFilename = getLocalFilename(outputDirectory, filename);
      filenameMap.put(filename,  localFilename);
      File file = new File(origDirectoryName + localFilename);
      fileOutputStream = new FileOutputStream(file);
    }
    catch (Exception e)
    {
      new Notification("Could not upload file: " + e.getMessage(),
                       Notification.Type.ERROR_MESSAGE)
            .show(Page.getCurrent());
      fileOutputStream = null;
    }
    
    return fileOutputStream;
  }
  
  @Override
  public void uploadSucceeded(SucceededEvent event)
  {
    UiUtil.showNotification("Upload successful", 5);
    
    try
    {
      String localFilename = filenameMap.get(event.getFilename());
      Thumbnails.of(new File(getOrigDirectoryName() + localFilename))
      .size(1000, 1735)
      .toFile(new File(getViewDirectoryName() + localFilename));
      
      // once the rescale happens, we can refresh the file list
      browseView.refreshFileList();
    } 
    catch (IOException e)
    {
      // couldn't scale uploaded picture
      UiUtil.showNotification("Could not scale uploaded picture", -1);
    }
    finally
    {
      // we don't need the name in the map anymore, it was just there while we
      // uploaded and then rescaled the image.
      try { filenameMap.remove(event.getFilename()); } catch (Throwable t) {}
    }
  }
  
  @Override
  public void uploadFailed(FailedEvent event)
  {
    Exception reason = event.getReason();
    String message = "<null reason>"; 
    if (reason != null) { event.getReason().getMessage(); }
    UiUtil.showNotification("Upload failed: " + message, -1);
  }
  
  /**
   * return the filename used locally from the given filename as uploaded
   * @param filename
   * @return
   */
  private String getLocalFilename(File outputDirectory, String filename)
  {
    String nextNumber = getNextNumber(outputDirectory);
    return nextNumber + "-" + filename;
  }
  
  /**
   * get the next number available at the start of files in the originals
   * directory; we use this to ensure uniqueness and to order the pictures.
   * @param origDirectory
   * @return
   */
  private String getNextNumber(File origDirectory)
  {
    File[] fileList = origDirectory.listFiles();

    String highestNumber = "";
    for (File f:fileList)
    {
      String digits = f.getName().substring(0,4);
      if (digits.compareTo(highestNumber) > 0) { highestNumber = digits; }
    }
    String nextNumber;
    if ("".equals(highestNumber)) { nextNumber = "0001"; }
    else 
    {
      int nextNumberInt = Integer.parseInt(highestNumber);
      nextNumberInt++;
      nextNumber = String.format("%04d", nextNumberInt);
    }
    return nextNumber;
  }
}
