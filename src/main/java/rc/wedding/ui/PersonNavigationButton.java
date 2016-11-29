package rc.wedding.ui;

import rc.wedding.model.Person;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationManager;

@SuppressWarnings("serial")
public class PersonNavigationButton extends NavigationButton
{
    private Person person; public Person getPerson() { return person; }
    public PersonNavigationButton(Person p) 
    {
      this(p.getFullName(), p);
    }
    
    public PersonNavigationButton(String caption, Person p) 
    { 
      person = p; setCaption(caption); 
      
//      String key = person.getKey();

//      Resource resource = PersonData.getPersonImageResource(key);
//      setIcon(resource);
//      setTargetView(new PersonView(key));

      // single listener for all the navigation buttons; it builds 
      // the next view based on the person represented by the button.
      NavigationButtonClickListener listener 
        = new NavigationButtonClickListener()
              {
                  public void buttonClick(NavigationButtonClickEvent event)
                  {
                    PersonNavigationButton button = (PersonNavigationButton)event.getSource();
                    String key = button.getPerson().getKey();
                    PersonView personView = new PersonView(key);
//                    NavigationManager navigationManager = MenuView.getStoredNavigationManager();
                    getNavigationManager().navigateTo(personView);
                  }
              };
       addClickListener(listener);
    }
}
