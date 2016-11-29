package rc.wedding.ui;

import rc.wedding.model.Place;

import com.vaadin.addon.touchkit.ui.NavigationButton;

@SuppressWarnings("serial")
public class PlaceNavigationButton extends NavigationButton
{
  private Place place;  public void setPlace(Place p) { place = p; } public Place getPlace() { return place; }
  
  public PlaceNavigationButton(Place place)
  {
    this.place = place;
    String name = place.getName();
    setCaption(name);
    
      // single listener for all the navigation buttons; it builds 
      // the next view based on the person represented by the button.
      NavigationButtonClickListener listener 
        = new NavigationButtonClickListener()
              {
                  public void buttonClick(NavigationButtonClickEvent event)
                  {
                    PlaceNavigationButton button = (PlaceNavigationButton)event.getSource();
                    Place place = button.getPlace();
                    PlaceView placeView = new PlaceView(place);
                    getNavigationManager().navigateTo(placeView);
                  }
              };
       addClickListener(listener);
    }
}
