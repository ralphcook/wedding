package rc.wedding.ui;

import rc.wedding.model.Place;
import rc.wedding.model.ResourceUtil;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

@SuppressWarnings("serial")
public class PlaceView extends NavigationView
{
  public static void say(String s) { System.out.println(s); }
  
  public PlaceView(Place place)
  {
    setCaption(place.getName());
    addStyleName("placeView");
    CssLayout layout = new CssLayout();
    String description = ResourceUtil.readPlaceDescriptionFile(place.getKey());
    Label topLabel = new Label(description, ContentMode.HTML);
    layout.addComponent(topLabel);
    
    addNonnullLink(layout, "Website", place.getWebsite());
    addNonnullLink(layout, "Map", place.getMap());
    setContent(layout);
    say("done PlaceView constructor");
  }
  
  private void addNonnullLink(Layout layout, String name, String url)
  {
    if (url != null && url.length() > 0)
    {
      Label label = newTabLinkLabel(name, url);
      layout.addComponent(label);
    }
  }
  
  private Label newTabLinkLabel(String caption, String url)
  {
    url = url.replaceAll("'", "&#39");
    String newTabFormat = "<a href='%s' target='_blank'>%s<//a>";
    String link = String.format(newTabFormat, url, caption);
    Label result = new Label(link, ContentMode.HTML);
    return result;
  }
}
