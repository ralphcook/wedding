package rc.wedding.ui;

import java.util.ArrayList;
import java.util.List;

import rc.wedding.model.Place;
import rc.wedding.model.PlaceData;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;

@SuppressWarnings("serial")
public class PlacesView extends NavigationView
{
  private static void say(String s) { System.out.println(s); }
  private List<Place> places = new ArrayList<>();
  
  @Override
  public void attach()
  {
    super.attach();
    if (places.isEmpty())
    {
      places = PlaceData.getPlaces();
      buildUI();
      say("done PlacesView.attach()");
    }
  };

  private void buildUI()
  {
    setStyleName("Places1");
    setCaption("Places");
    setSizeFull();

//    personList = buildPersonContainer();

    VerticalComponentGroup contentLayout = new VerticalComponentGroup();
    contentLayout.setCaption("Places3");
    contentLayout.setSizeFull();
    
    for (Place p : places)
    {
      PlaceNavigationButton button = new PlaceNavigationButton(p);
      contentLayout.addComponent(button);
      
                                                          //      String name = p.getName();
                                                          //      
                                                          //      String websiteUrl = p.getWebsite();
                                                          //      if (websiteUrl != null && websiteUrl.length() > 0)
                                                          //      {
                                                          //        Link link = new Link("Website", new ExternalResource(websiteUrl));
                                                          //        contentLayout.addComponent(link);
                                                          //      }
                                                          //      String mapUrl = p.getMapLink();
                                                          //      if (mapUrl != null && mapUrl.length() > 0)
                                                          //      {
                                                          //        Link link = new Link("Google Map", new ExternalResource(mapUrl));
                                                          //        contentLayout.addComponent(link);
                                                          //      }
    }
    
    setContent(contentLayout);

  }
  
}
