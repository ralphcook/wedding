package rc.wedding.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import rc.wedding.model.ResourceUtil;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class WelcomeView extends NavigationView
{
  public static void say(String s) { System.out.println(s); }
  @Override
  public void attach()
  {
    super.attach();
    buildUI();
    say("done WelcomeView.attach()");
  }
  
  private void buildUI()
  {
    setCaption("Welcome");
    addStyleName("welcomeView");
//    setSizeFull();
    
    CssLayout content = new CssLayout();
    
    String welcomeText = ResourceUtil.readResourcesTextFile("welcomeHeader");
    
//      String placeHolderText = "Hi, welcome to Jennifer and WuDi's wedding. We hope you have fun, "
//          + "and that we'll have more text to stick in here before you actually see it.";
    
    Label topLabel = new Label(welcomeText, ContentMode.HTML);
//    topLabel.setWidthUndefined();
    content.addComponent(topLabel);
    
    setContent(content);
  }
}
