package rc.wedding.ui;

import java.util.List;

import rc.wedding.model.Person;
import rc.wedding.model.PersonData;
import rc.wedding.model.Relationship;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;

@SuppressWarnings("serial")
public class PersonView extends NavigationView
{
  public static void say(String s) { System.out.println(s); }
  
  public PersonView(String key)
  {
    Person p = PersonData.get(key);
    buildUI(p);
  }
  
  public PersonView(Person p)
  {
    buildUI(p);
  }
  
  private void buildUI(Person p)
  {
    say("PersonView buildUI()");
    CssLayout layout = new CssLayout();
    
    // build content from the person's name and list of relationships
    Image image = PersonData.getPersonFullsizeImage(p.getKey());
    if (image != null)
    {
      image.setWidth("3in");
      layout.addComponent(image);
    }
    
    setCaption(p.getFullName());
    addStyleName("personView");
    
    String description = p.getDescription();
    if (description != null && description.length() > 0)
    {
      Label descriptionLabel = new Label(description, ContentMode.HTML);
      layout.addComponent(descriptionLabel);      
    }
    
    List<Relationship> folks = p.getRelationships();
    if (folks != null && !folks.isEmpty())
    {
      Table table = new Table(p.getFullName());
      table.setSizeUndefined();
      table.setPageLength(0);
//      table.setSizeFull();      // if I set this, the table doesn't show up.
      table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
      table.addContainerProperty("rel", Label.class, null);
//      table.setColumnWidth("rel", 120);
      table.addContainerProperty("btn", NavigationButton.class, null);
//      table.setColumnExpandRatio("btn", 90.0f);
      int rowIndex = 1;
      
      for (Relationship r : folks)
      {
        Person otherPerson = PersonData.get(r.getPersonKey());
        if (otherPerson != null)
        {
          String relationshipText = r.getRelationshipName().toString();
          Label  relationshipLabel = new Label(relationshipText);
          relationshipLabel.addStyleName("relationshipLabel");
//          relationshipLabel.setWidth("90px");
          
          PersonNavigationButton button = new PersonNavigationButton(otherPerson.getFullName(), otherPerson);
          table.addItem(new Object[] { relationshipLabel, button }, rowIndex++);
        }
      }
      layout.addComponent(table);
    }

    setContent(layout);

    NavigationButton peopleNavigationButton = new NavigationButton("People", MenuView.getPeopleView());
    setRightComponent(peopleNavigationButton);
  }
}
