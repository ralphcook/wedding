package rc.wedding.ui;

import rc.wedding.model.ImageUtil;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Image;

@SuppressWarnings("serial")
public class MenuView extends NavigationView 
{
  private static void say(String s) { System.out.println(); }
  
  private static NavigationManager navigationManager = null; public static NavigationManager getStoredNavigationManager() { return navigationManager; }
  private static PeopleView peopleView = null; public static PeopleView getPeopleView() { return peopleView; }

    public MenuView() {
        setCaption("J&W Wedding");
        addStyleName("menuView");
//        Label emptyLabel = new Label("");
//        emptyLabel.addStyleName("empty");
//        setLeftComponent(emptyLabel);
        
        VerticalComponentGroup content = new VerticalComponentGroup();

        Image heading = ImageUtil.getImageFromResource("images/", "weddingInviteTitle", "jpg");
//        heading.setWidth("2.5in");
        content.addComponent(heading);
        
        NavigationButton welcomeButton = new NavigationButton("Welcome", new WelcomeView());
        peopleView = new PeopleView();
        NavigationButton peopleButton = new NavigationButton("People", peopleView);
        NavigationButton placesButton = new NavigationButton("Places", new PlacesView());
        NavigationButton shareButton  = new NavigationButton("Share",   new UploadView());
        
        content.addComponents(welcomeButton, peopleButton, placesButton, shareButton);
        
        setContent(content);
        navigationManager = getNavigationManager();
        say("MenuView constructor");
    };
}
