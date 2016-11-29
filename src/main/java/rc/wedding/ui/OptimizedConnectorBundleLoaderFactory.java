package rc.wedding.ui;

import java.util.HashSet;
import java.util.Set;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.vaadin.client.ui.ui.UIConnector;
import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;
import com.vaadin.shared.ui.Connect.LoadStyle;

public class OptimizedConnectorBundleLoaderFactory extends
    ConnectorBundleLoaderFactory
{
  private Set<String> eagerConnectors = new HashSet<String>();
  
  
  {
    eagerConnectors.add("UIConnector");
    eagerConnectors.add("NavigationManagerConnector");
    eagerConnectors.add("NavigationViewConnector");
    eagerConnectors.add("NavigationBarConnector");
    eagerConnectors.add("VerticalComponentGroupConnector");
    eagerConnectors.add("ImageConnector");
    eagerConnectors.add("NavigationButtonConnector");
//    eagerConnectors.add("NavigationViewConnector");
//    eagerConnectors.add("NavigationBarConnector");
//    eagerConnectors.add("NavigationButtonConnector");
    eagerConnectors.add("CssLayoutConnector");
    eagerConnectors.add("LabelConnector");
    eagerConnectors.add("VerticalLayoutConnector");
    eagerConnectors.add("UploadConnector");
//    eagerConnectors.add("NavigationButtonConnector");
    eagerConnectors.add("OfflineModeConnector");
  }

  @Override
  protected LoadStyle getLoadStyle(JClassType connectorType)
  {
    if (eagerConnectors.contains(connectorType.getQualifiedBinaryName()))
    {
      return LoadStyle.EAGER;
    } else
    {
      // Loads all other connectors immediately after the initial view has
      // been rendered
      return LoadStyle.DEFERRED;
    }
  }
}
