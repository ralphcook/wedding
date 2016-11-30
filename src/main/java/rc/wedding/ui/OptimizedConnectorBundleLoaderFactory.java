package rc.wedding.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.vaadin.addon.touchkit.gwt.client.vcom.OfflineModeConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.VerticalComponentGroupConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationBarConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationButtonConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationManagerConnector;
import com.vaadin.addon.touchkit.gwt.client.vcom.navigation.NavigationViewConnector;
import com.vaadin.client.ui.csslayout.CssLayoutConnector;
import com.vaadin.client.ui.image.ImageConnector;
import com.vaadin.client.ui.label.LabelConnector;
import com.vaadin.client.ui.orderedlayout.VerticalLayoutConnector;
import com.vaadin.client.ui.ui.UIConnector;
import com.vaadin.client.ui.upload.UploadConnector;
import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.Connect.LoadStyle;

public class OptimizedConnectorBundleLoaderFactory extends
    ConnectorBundleLoaderFactory
{
  private List<Class<?>> eagerConnectors = new ArrayList<Class<?>>();
  
  
  {
    eagerConnectors.add(UIConnector.class);
    eagerConnectors.add(NavigationManagerConnector.class);
    eagerConnectors.add(NavigationViewConnector.class);
    eagerConnectors.add(NavigationBarConnector.class);
    eagerConnectors.add(VerticalComponentGroupConnector.class);
    eagerConnectors.add(ImageConnector.class);
    eagerConnectors.add(NavigationButtonConnector.class);
//    eagerConnectors.add("NavigationViewConnector");
//    eagerConnectors.add("NavigationBarConnector");
//    eagerConnectors.add("NavigationButtonConnector");
    eagerConnectors.add(CssLayoutConnector.class);
    eagerConnectors.add(LabelConnector.class);
    eagerConnectors.add(VerticalLayoutConnector.class);
    eagerConnectors.add(UploadConnector.class);
//    eagerConnectors.add("NavigationButtonConnector");
    eagerConnectors.add(OfflineModeConnector.class);
  }

  @Override
  protected LoadStyle getLoadStyle(JClassType connectorType)
  {
    Connect annotation = connectorType.getAnnotation(Connect.class);
    Class<?> componentClass = annotation.value();
    System.out.println("Got an annotation value of " + componentClass.getName());
    if (eagerConnectors.contains(componentClass))
    {
      return LoadStyle.EAGER;
    }
    else
    {
      // Loads all other connectors immediately after the initial view has
      // been rendered
      return LoadStyle.LAZY;
    }
  }
}
