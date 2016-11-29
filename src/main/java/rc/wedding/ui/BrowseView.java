package rc.wedding.ui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import rc.wedding.model.ImageUtil;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class BrowseView extends NavigationView
{
  private List<File> fileList = new ArrayList<>();
  private int               currentIndex        = 0;
  private Image             previousImage       = null;
  private Image             currentImage        = null;
  private Image             nextImage           = null;
  private HorizontalLayout  picture             = null;
  
  private Button            previousButton     = new Button("<prev");
  private Button            nextButton         = new Button("next>");
  
  public static void say(String s) { System.out.println(s); }
  
  @Override
  public void attach()
  {
    super.attach();
    buildUI();
    say("done BrowseView attach()");
  }
  
  public void refreshFileList()
  {
    String uploadedImagesDirName = UploadView.getViewDirectoryName();
    File[] tempFileList = new File(uploadedImagesDirName).listFiles
        ( new FilenameFilter()
        {
          public boolean accept(File dir, String name)
          {
            name = name.toLowerCase();
            return (name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png"));
          }
        }
        );
    for (File f:tempFileList)
    {
      if (!fileList.contains(f))
      {
        fileList.add(f);
      }
      Comparator<File> descendingComparator 
      = new Comparator<File>()
      {
        @Override
        public int compare(File o1, File o2)
        {
          String name1 = o1.getName();
          String name2 = o2.getName();
          return name2.compareTo(name1);
        }
      };
      fileList.sort(descendingComparator);

      // BUG - we don't handle refresh after new upload well; need to rewrite
      // without assuming that this is the initial load of the UI.
      currentIndex = 0;
      previousImage = null;
      if (fileList.size() == 0)
      {
        nextButton.setEnabled(false);
        previousButton.setEnabled(false);
      }
      else
      {
        currentImage = getImage(currentIndex); // ImageUtil.getImageFromFile(fileList.get(0));
        nextImage    = getImage(currentIndex+1); // ImageUtil.getImageFromFile(fileList.get(1));
        nextButton.setEnabled(fileList.size() > 1);
        previousButton.setEnabled((fileList.size() > 0) && (currentIndex > 0));
      }
    }
  }
  
  private void buildUI()
  {
    setCaption("Browse");
    addStyleName("browseView");
    refreshFileList();
    
    
    // we've initialized all the data; now actually build the UI
    picture = new HorizontalLayout();
    picture.setSpacing(true);
    picture.removeAllComponents();
    picture.addComponent(currentImage);
    
    previousButton.addClickListener
    (
        new ClickListener()
        {
          public void buttonClick(ClickEvent clickEvent)
          {
            if (currentIndex > 0)
            {
              currentIndex--;
              previousButton.setEnabled(currentIndex > 0);
              nextButton.setEnabled(true);
              nextImage = currentImage;
              currentImage = previousImage;
              previousImage = getImage(currentIndex - 1);
              
              setPictureImage(currentImage);
            }
          }
        }
    );
    nextButton.addClickListener
    (
      new ClickListener()
      {
        public void buttonClick(ClickEvent clickEvent)
        {
          if (currentIndex < (fileList.size()-1))
          {
            currentIndex++;
            previousButton.setEnabled(true);
            nextButton.setEnabled(currentIndex < (fileList.size()-1));
            previousImage = currentImage;
            currentImage = nextImage;
            nextImage = getImage(currentIndex + 1);
            
            setPictureImage(currentImage);
          }
        }
      }
    );
    
    /*** ***/
    /*
     * add a refresh button
     * on refresh, first get the length of the file list, then get a new file list; if different,
     * adjust the current index so that the picture either stays the same or moves one towards the
     * more recent picture.
     * Disable next/prev buttons when they won't do anything.
     */

    Button refreshButton = new Button("Refresh list");
    refreshButton.addClickListener
    (
        new ClickListener()
        {
          @Override public void buttonClick(ClickEvent event)
          {
            refreshFileList();
            setPictureImage(currentImage);
          }
          
        }
    );

    HorizontalLayout previousNextLayout = new HorizontalLayout();
    previousNextLayout.addComponents(previousButton, refreshButton, nextButton);
    
    
    VerticalLayout content = new VerticalLayout();
    content.addComponents(previousNextLayout, picture);
    
    setContent(content);
  }
  
  
  private Image getImage(int index) 
  {
    Image image = null;
    if (index >= 0 && index < fileList.size())
    {
      image = ImageUtil.getImageFromFile(fileList.get(index));
      image.setWidth("3in");
    }
    return image;
  }
  
  private void setPictureImage(Image image)
  {
    picture.removeAllComponents();
    picture.addComponent(image);
  }

//  private SwipeView createView(int pos)
//  {
//    SwipeView view = new SwipeView();
//    view.setSizeFull();
//
//    // Use an inner layout to center the image
//    CssLayout layout = new CssLayout();
//    layout.setSizeFull();
//    
//    NavigationButton backButton = new NavigationButton("Upload");
////    backButton.setStyleName("back");
////    backButton.addClickListener
////    (
////        new NavigationButton.NavigationButtonClickListener()
////        {
////          public void buttonClick(NavigationButton.NavigationButtonClickEvent event)
////          {
////            navigateBack();
////          }
////        }
////    );
//
//    Image image = ImageUtil.getImageFromFile(fileList.get(currentIndex));
//    image.setWidth("3in");
//    layout.addComponents(backButton, image);
////    layout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
//    
//    getNavigationManager().addNavigationListener(this);
//
//    view.setContent(layout);
//    return view;
//  }
//
//  @Override
//  public void navigate(NavigationEvent event)
//  {
//    switch (event.getDirection())
//    {
//      case FORWARD:
//        if (++currentIndex < fileList.size() - 1)
//          getNavigationManager().setNextComponent(createView(currentIndex + 1));
//        break;
//      case BACK:
//        if (--currentIndex >= 0)
//          getNavigationManager().setPreviousComponent(createView(currentIndex - 1));
//    }
//  }
}
