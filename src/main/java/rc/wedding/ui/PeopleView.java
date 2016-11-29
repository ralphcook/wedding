package rc.wedding.ui;

import java.util.List;

import rc.wedding.model.Person;
import rc.wedding.model.PersonData;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.server.Resource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;

@SuppressWarnings("serial")
public class PeopleView extends NavigationView
{
  public static void say(String s) { System.out.println(s); }
  @Override
  public void attach()
  {
    super.attach();
    buildUI();
    say("done PeopleView.attach()");
  }
  
  private void buildUI()
  {
    setCaption("People");
    addStyleName("peopleView");
    setSizeFull();
    
/////////////////////////    
    Table table = new Table("People");
    table.setSizeFull();
    table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    
    table.addContainerProperty("pic", Image.class, null);
    table.setColumnWidth("pic", 52);
    table.addContainerProperty("name", NavigationButton.class, null);
    table.setColumnExpandRatio("name", 90.0f);
    
//    VerticalLayout content = new VerticalLayout();
//    content.setCaption("People2");
/////////////////////////

    List<Person> people = PersonData.getPersonList();
    int rowIndex = 1;
    
    for (Person person : people)
    {
      PersonNavigationButton button = new PersonNavigationButton(person.getFullName(), person) ;
      Resource resource = PersonData.getPersonFullsizeResource(person.getKey());
      
//////////////////////////
      Image image = new Image("", resource);
      image.setWidth("50px");
      table.addItem(new Object[] { image, button}, rowIndex++ );
//      button.setIcon(resource);
//      content.addComponent(button);
//////////////////////////
    }
    setContent(table);
  }
}
